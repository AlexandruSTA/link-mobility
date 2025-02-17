package com.alex.linkmobility.messagingapp.concurrent;

import com.alex.linkmobility.messagingapp.domain.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static com.alex.linkmobility.messagingapp.constant.RabbitMQConstants.MESSAGE_KEY;
import static com.alex.linkmobility.messagingapp.constant.RabbitMQConstants.TOPIC_EXCHANGE_NAME;

@Component
public class SendMessageTask implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(SendMessageTask.class);

    private final RabbitTemplate rabbitTemplate;

    public SendMessageTask(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void run() {
        LOGGER.info("Sending the message...");
        rabbitTemplate.convertAndSend(
                TOPIC_EXCHANGE_NAME.value(),
                MESSAGE_KEY.value(),
                Message.generateRandomMessage());
        LOGGER.info("Message sent!");
    }

}
