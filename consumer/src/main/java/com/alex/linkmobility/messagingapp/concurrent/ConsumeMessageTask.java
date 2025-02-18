package com.alex.linkmobility.messagingapp.concurrent;

import com.alex.linkmobility.messagingapp.domain.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class ConsumeMessageTask implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(ConsumeMessageTask.class);

    private final Message message;

    public ConsumeMessageTask(Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        LOGGER.info("Consuming the message...");
        LOGGER.info("\n*********CONTENT*********\n{}\n*********CONTENT*********\n", message.toString());
        LOGGER.info("CURRENT THREAD: {}", Thread.currentThread());
        LOGGER.info("Message consumed!");
    }

}
