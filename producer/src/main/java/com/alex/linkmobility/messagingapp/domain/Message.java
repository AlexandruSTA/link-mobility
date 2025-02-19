package com.alex.linkmobility.messagingapp.domain;

import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.UUID;

@Data
@Component
public class Message implements Serializable {

    private UUID id = UUID.randomUUID();

    private String sender = RandomStringUtils.randomAlphanumeric(10);

    private String recipient = RandomStringUtils.random(1, "ABC") + RandomStringUtils.randomAlphanumeric(5);

    private String message = RandomStringUtils.randomAlphanumeric(20);

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
