package com.streamSavvySquad.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.streamSavvySquad.utils.Tweet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KafkaMessageListener {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    Logger log = LoggerFactory.getLogger(KafkaMessageListener.class);
    public static List<Tweet> salaarMessages = new ArrayList<>();
    public static List<Tweet> dunkiMessages = new ArrayList<>();
    @KafkaListener(topics = "salaar-topic",groupId = "jt-group-new")
    public void consume2(String message) throws JsonProcessingException {
        Tweet tweet = convertToTweet(message);
        salaarMessages.add(tweet);
       // log.info("consumer2 consume the message {} ", message);
    }

    @KafkaListener(topics = "salaar-topic",groupId = "jt-group-new")
    public void consume3(String message) throws JsonProcessingException {
        Tweet tweet = convertToTweet(message);
        salaarMessages.add(tweet);
        //log.info("consumer3 consume the message {} ", message);
    }

    @KafkaListener(topics = "salaar-topic",groupId = "jt-group-new")
    public void consume4(String message) throws JsonProcessingException {
        Tweet tweet = convertToTweet(message);
        salaarMessages.add(tweet);
      //  log.info("consumer4 consume the message {} ", message);
    }


    public static List<Tweet> getSalaarMessages() {
        return salaarMessages;
    }


    @KafkaListener(topics = "dunki-topic",groupId = "jt-group-new2")
    public void consume2Dunki(String message) throws JsonProcessingException {
        Tweet tweet =convertToTweet(message);
        dunkiMessages.add(tweet);
        //log.info("consumer2 consume the message {} ", message);
    }

    @KafkaListener(topics = "dunki-topic",groupId = "jt-group-new2")
    public void consume3Dunki(String message) throws JsonProcessingException {
        Tweet tweet =convertToTweet(message);
        dunkiMessages.add(tweet);
        //log.info("consumer3 consume the message {} ", message);
    }

    @KafkaListener(topics = "dunki-topic",groupId = "jt-group-new2")
    public void consume4Dunki(String message) throws JsonProcessingException {
        Tweet tweet =convertToTweet(message);
        dunkiMessages.add(tweet);
       // log.info("consumer4 consume the message {} ", message);
    }

    public static List<Tweet> getDunkiMessages() {
        return dunkiMessages;
    }


    public static Tweet convertToTweet(String jsonString) throws JsonProcessingException {
        return objectMapper.readValue(jsonString, Tweet.class);
    }
}
