package com.alex.linkmobility.messagingapp.domain;

import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.Serializable;
import java.util.UUID;

@Data
public class Message implements Serializable {

    private UUID id;

    private String sender;

    private String recipient;

    private String message;

    public static Message generateRandomMessage() {
        Message toBuild = new Message();
        toBuild.id = UUID.randomUUID();
        toBuild.sender = RandomStringUtils.randomAlphanumeric(10);
        toBuild.recipient = RandomStringUtils.random(1, "ABC");
        toBuild.message = RandomStringUtils.randomAlphanumeric(20);
        return toBuild;
    }

    @Override
    public String toString() {
        return "\n------Message------" +
                "\nID: " + id +
                "\nSender: " + sender +
                "\nRecipient: " + recipient +
                "\nMessage: " + message +
                "\n------Message------\n";
    }

}
