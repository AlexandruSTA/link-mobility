package com.alex.linkmobility.messagingapp.service;

import com.alex.linkmobility.messagingapp.domain.Message;

public interface Produceable {
    void produceMessages();

    void produceMessage(Message message);
}
