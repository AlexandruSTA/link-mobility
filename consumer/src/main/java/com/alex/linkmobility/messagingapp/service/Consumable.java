package com.alex.linkmobility.messagingapp.service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface Consumable {
    void consumeMessages() throws IOException, TimeoutException;
}
