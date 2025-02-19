package com.alex.linkmobility.messagingapp.api;

import com.alex.linkmobility.messagingapp.domain.Message;
import com.alex.linkmobility.messagingapp.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/messages")
public class ProducerController {

    @Autowired
    private ProducerService service;

    @PostMapping("/publish")
    public ResponseEntity<?> publish(@RequestBody Message message) {
        service.produceMessage(message);
        return ResponseEntity
                .ok("Message with ID " + message.getId().toString() + " was successfully published!");
    }

}
