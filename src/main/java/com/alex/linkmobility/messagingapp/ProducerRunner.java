package com.alex.linkmobility.messagingapp;

import com.alex.linkmobility.messagingapp.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ProducerRunner implements ApplicationRunner {

    @Autowired
    private ProducerService producerService;

    @Override
    public void run(ApplicationArguments args) {
        producerService.produceMessages();
    }

}
