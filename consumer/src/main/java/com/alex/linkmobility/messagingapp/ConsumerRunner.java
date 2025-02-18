package com.alex.linkmobility.messagingapp;

import com.alex.linkmobility.messagingapp.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsumerRunner implements ApplicationRunner {

    @Autowired
    private ConsumerService service;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        service.consumeMessages();
    }

}
