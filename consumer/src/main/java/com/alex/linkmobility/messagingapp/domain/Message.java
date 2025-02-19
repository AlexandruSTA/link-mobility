package com.alex.linkmobility.messagingapp.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.UUID;

@Data
@Component
public class Message implements Serializable {

    private UUID id;

    private String sender;

    private String recipient;

    private String message;

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
