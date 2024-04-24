package com.streamSavvySquad.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.streamSavvySquad.dto.Tweet;
import com.streamSavvySquad.dto.TweetGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher {

    @Autowired
    private KafkaTemplate<String, String> template;

    public void sendMessageToTopic(Tweet tweet) throws JsonProcessingException {
        String message = TweetGenerator.convertToJsonString(tweet);
        CompletableFuture<SendResult<String, String>> future = template.send("movie-tweets-topic", String.valueOf(tweet.getId()), message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" + message + "] due to : " + ex.getMessage());
            }
        });

    }

}
