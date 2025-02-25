package com.alex.linkmobility.messagingapp.service;

import com.alex.linkmobility.messagingapp.concurrent.ConsumeMessageTask;
import com.alex.linkmobility.messagingapp.domain.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.alex.linkmobility.messagingapp.commons.constant.RabbitMQConstants.*;


@Getter
@Service
public class ConsumerService implements Consumable {

    private static final Logger LOGGER = LogManager.getLogger(ConsumerService.class);

    private static final int THREADS_NUMBER = 10;

    private final ExecutorService executorServiceA = Executors.newFixedThreadPool(THREADS_NUMBER);
    private final ExecutorService executorServiceB = Executors.newFixedThreadPool(THREADS_NUMBER);
    private final ExecutorService executorServiceC = Executors.newFixedThreadPool(THREADS_NUMBER);

    @Value("${rabbitmq.host}")
    private String host;

    @Value("${rabbitmq.username}")
    private String username;

    @Value("${rabbitmq.password}")
    private String password;

    @Override
    public void consumeMessages() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);

        final Connection connection = connectionFactory.newConnection();
        final Channel channel = connection.createChannel();

        final ObjectMapper objectMapper = new ObjectMapper();

        LOGGER.info("Waiting for messages...");

        handleConsumer(channel, executorServiceA, QUEUE_RECIPIENTS_A_NAME.value(), objectMapper);
        handleConsumer(channel, executorServiceB, QUEUE_RECIPIENTS_B_NAME.value(), objectMapper);
        handleConsumer(channel, executorServiceC, QUEUE_RECIPIENTS_C_NAME.value(), objectMapper);
    }

    private void handleConsumer(Channel channel, ExecutorService executorService, String queueName, ObjectMapper objectMapper) throws IOException {
        registerConsumer(channel, queueName, executorService, objectMapper);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                this.shutdown(executorService);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    private void shutdown(ExecutorService executorService) throws IOException {
        LOGGER.info("Invoking shutdown hook...");
        LOGGER.info("Shutting down thread pool...");
        executorService.shutdown();
        try {
            while (!executorService.awaitTermination(10, TimeUnit.SECONDS)) ;
        } catch (InterruptedException e) {
            LOGGER.error("Interrupted while waiting for termination");
        }
        LOGGER.info("Thread pool shut down.");
        LOGGER.info("Done with shutdown hook.");
    }

    private static void registerConsumer(final Channel channel,
                                         final String queue,
                                         final ExecutorService executorService,
                                         final ObjectMapper objectMapper)
            throws IOException {
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       final byte[] body) throws IOException {
                try {
                    LOGGER.info("Received MESSAGE on CHANNEL {}.", channel.getChannelNumber());
                    Message parsedMessage = objectMapper.readValue(body, Message.class);
                    executorService.submit(new ConsumeMessageTask(parsedMessage));
                } catch (Exception e) {
                    LOGGER.error("", e);
                }
            }
        };
        channel.basicConsume(queue, true /* auto-ack */, consumer);
    }

}
