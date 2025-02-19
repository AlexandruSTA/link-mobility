package com.alex.linkmobility.messagingapp.commons.constant;

import java.io.Serializable;

public enum RabbitMQConstants implements Serializable {

    TOPIC_EXCHANGE_NAME("message-exchange"),

    QUEUE_RECIPIENTS_A_NAME("message-queue-recipients-a"),
    QUEUE_RECIPIENTS_B_NAME("message-queue-recipients-b"),
    QUEUE_RECIPIENTS_C_NAME("message-queue-recipients-c"),

    MESSAGE_ROUTING_KEY_RECIPIENTS_A("message-routing-key-a"),
    MESSAGE_ROUTING_KEY_RECIPIENTS_B("message-routing-key-b"),
    MESSAGE_ROUTING_KEY_RECIPIENTS_C("message-routing-key-c");

    private final String value;

    RabbitMQConstants(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

}
