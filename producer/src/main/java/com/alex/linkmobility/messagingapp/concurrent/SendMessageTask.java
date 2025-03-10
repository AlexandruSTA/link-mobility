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
public class SendMessageTask implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(SendMessageTask.class);

    private final RabbitTemplate rabbitTemplate;

    public SendMessageTask(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void run() {
        try {
            Message toSend = new Message();
            AtomicReference<String> routingKey = new AtomicReference<>("message-key");
            LOGGER.info("Sending the message...");
            LOGGER.info("\n*********CONTENT*********\n{}\n*********CONTENT*********\n", toSend.toString());
            Stream.of(
                            MESSAGE_ROUTING_KEY_RECIPIENTS_A.value(),
                            MESSAGE_ROUTING_KEY_RECIPIENTS_B.value(),
                            MESSAGE_ROUTING_KEY_RECIPIENTS_C.value())
                    .filter(t -> Character.toLowerCase(t.charAt(t.length() - 1)) == Character.toLowerCase(toSend.getRecipient().charAt(0)))
                    .findFirst().ifPresent(routingKey::set);

            rabbitTemplate.convertAndSend(
                    TOPIC_EXCHANGE_NAME.value(),
                    routingKey.get(),
                    toSend);
            LOGGER.info("Message sent!");
        } catch (AmqpException exception) {
            LOGGER.error("Received AMQP Exception: {}", String.valueOf(exception));
        }
    }

}
