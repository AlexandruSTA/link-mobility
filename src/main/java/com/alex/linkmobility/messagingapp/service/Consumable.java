package com.alex.linkmobility.messagingapp.service;

import com.alex.linkmobility.messagingapp.domain.Message;

public interface Consumable {
    void consumeMessage(Message message);
}
