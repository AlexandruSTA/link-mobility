package com.alex.linkmobility.messagingapp.service;

import com.alex.linkmobility.messagingapp.concurrent.SendMessageTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class ProducerService implements Produceable {

    private static final Logger LOGGER = LogManager.getLogger(ProducerService.class);

    private static final int THREAD_NUMBER = 10;

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(THREAD_NUMBER);

    private final RabbitTemplate rabbitTemplate;

    public ProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void produceMessages() {
        for (int i = 0; i < 5; i++) {
            executorService.scheduleWithFixedDelay(
                    new SendMessageTask(rabbitTemplate),
                    0,
                    3,
                    TimeUnit.SECONDS);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
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
        }));
    }

}
