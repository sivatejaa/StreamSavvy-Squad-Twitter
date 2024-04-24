package com.streamSavvySquad.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TweetGenerator {

    private static final Random random = new Random();

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String[] movieNames = {"Salaar", "Dunki"};

    private static final String[] hashtags = {"#hit", "#blockbuster", "#flop", "#disaster"};

    private static final String[] positiveSentiments = {"amazing", "fantastic", "great", "awesome"};

    private static final String[] negativeSentiments = {"terrible", "awful", "disappointing", "bad"};

    public static List<Tweet> generateTweets(int count) {
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            Tweet tweet = new Tweet();
            tweet.setId(i);
            tweet.setTextContent(generateTextContent());
            tweet.setAuthor(generateRandomName());
            tweet.setHashtag(generateRandomHashtag());
            tweet.setSubject(generateRandomMovieName());
            tweets.add(tweet);
        }
        return tweets;
    }

    private static String generateTextContent() {
        String[] sentiments = random.nextBoolean() ? positiveSentiments : negativeSentiments;
        String sentiment = sentiments[random.nextInt(sentiments.length)];
        return "The movie is " + sentiment + "!";
    }

    private static String generateRandomName() {
        String[] names = {"Shiva", "Anu", "Meghana", "Pavani", "Raju", "Teja"};
        return names[random.nextInt(names.length)];
    }

    private static String generateRandomHashtag() {
        return hashtags[random.nextInt(hashtags.length)];
    }

    private static String generateRandomMovieName() {
        return movieNames[random.nextInt(movieNames.length)];
    }





    public static String convertToJsonString(Tweet tweet) throws JsonProcessingException {
        return objectMapper.writeValueAsString(tweet);
    }
}
