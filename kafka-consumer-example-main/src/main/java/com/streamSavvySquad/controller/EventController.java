package com.streamSavvySquad.controller;

import com.streamSavvySquad.partitions.Producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/producer-app")
public class EventController {

    @Autowired
    private Producer publisher;

    @GetMapping("/sendToPartitions")
    public ResponseEntity<?> publishMessage() {
        try {

                publisher.sendMessageToTopic();

            return ResponseEntity.ok("message published successfully ..");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }



}
