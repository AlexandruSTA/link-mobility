package com.alex.linkmobility.messagingapp.concurrent;

import com.alex.linkmobility.messagingapp.domain.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import static com.alex.linkmobility.messagingapp.commons.constant.RabbitMQConstants.*;

@Component
public class SendSpecificMessageTask implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(SendSpecificMessageTask.class);

    private final Message message;

    private final RabbitTemplate rabbitTemplate;

    public SendSpecificMessageTask(RabbitTemplate rabbitTemplate, Message message) {
        this.rabbitTemplate = rabbitTemplate;
        this.message = message;
    }

    public void run() {
        try {
            LOGGER.info("Sending the message...");
            LOGGER.info("\n*********CONTENT*********\n{}\n*********CONTENT*********\n", message.toString());
            AtomicReference<String> routingKey = new AtomicReference<>("message-key");
            Stream.of(
                            MESSAGE_ROUTING_KEY_RECIPIENTS_A.value(),
                            MESSAGE_ROUTING_KEY_RECIPIENTS_B.value(),
                            MESSAGE_ROUTING_KEY_RECIPIENTS_C.value())
                    .filter(t -> Character.toLowerCase(t.charAt(t.length() - 1)) == Character.toLowerCase(message.getRecipient().charAt(0)))
                    .findFirst().ifPresent(routingKey::set);

            rabbitTemplate.convertAndSend(
                    TOPIC_EXCHANGE_NAME.value(),
                    routingKey.get(),
                    message);
            LOGGER.info("Message sent!");
        } catch (AmqpException exception) {
            LOGGER.error("Received AMQP Exception: {}", String.valueOf(exception));
        }
    }

}
