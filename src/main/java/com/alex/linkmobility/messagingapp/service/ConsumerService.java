package com.alex.linkmobility.messagingapp.service;

import com.alex.linkmobility.messagingapp.concurrent.ConsumeMessageTask;
import com.alex.linkmobility.messagingapp.domain.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RabbitListener(queues = "message-queue", concurrency = "1")
public class ConsumerService implements Consumable {

    private static final int THREADS_NUMBER = 10;

    private final ExecutorService executorService = Executors.newFixedThreadPool(THREADS_NUMBER);

    @Override
    @RabbitHandler
    public void consumeMessage(Message message) {
        for (int i = 0; i < THREADS_NUMBER; i++) {
            executorService.execute(new ConsumeMessageTask(message));
        }
    }

}
