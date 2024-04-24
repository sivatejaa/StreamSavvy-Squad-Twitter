package com.streamSavvySquad.controller;

import com.streamSavvySquad.dto.Tweet;
import com.streamSavvySquad.dto.TweetGenerator;
import com.streamSavvySquad.service.KafkaMessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableScheduling
@RequestMapping("/producer-app")
public class EventController {

    @Autowired
    private KafkaMessagePublisher publisher;

    @Scheduled(fixedDelay = 60000)
    public void publishMessage() {
        try {
            List<Tweet> tweets = TweetGenerator.generateTweets(100);
            for (int i = 0; i < tweets.size(); i++) {
                publisher.sendMessageToTopic(tweets.get(i));
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }




}
