package com.alex.linkmobility.messagingapp.constant;

import java.io.Serializable;

public enum RabbitMQConstants implements Serializable {
    TOPIC_EXCHANGE_NAME("message-exchange"),

    QUEUE_NAME("message-queue"),

    MESSAGE_KEY("message-key");


    private final String value;

    RabbitMQConstants(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

}
