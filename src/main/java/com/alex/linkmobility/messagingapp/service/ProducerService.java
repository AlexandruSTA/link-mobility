package com.alex.linkmobility.messagingapp.service;

import com.alex.linkmobility.messagingapp.concurrent.SendMessageTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ProducerService implements Produceable {

    private static final Logger LOGGER = LogManager.getLogger(ProducerService.class);

    private static final int THREAD_NUMBER = 10;

    private final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUMBER);

    private final RabbitTemplate rabbitTemplate;

    public ProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void produceMessages() {
        LOGGER.info("Sending the messages...");
        for (int i = 0; i < THREAD_NUMBER; i++) {
            executorService.execute(new SendMessageTask(rabbitTemplate));
        }
        LOGGER.info("Messages sent!");
    }

}
