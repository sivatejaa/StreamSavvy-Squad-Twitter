package com.streamSavvySquad.domainprocessor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.streamSavvySquad.utils.Tweet;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MovieTweetKafkaProcessor {
  private static final ObjectMapper objectMapper = new ObjectMapper();
  @Bean
  public Function<KStream<String, String>, KStream<String, String>[]> domainProcessor() {
    return kstream -> kstream.branch(
            (key, value) -> {
              try {
                Tweet tweet = convertToTweet(value);
                String movieName = tweet.getSubject();
                return movieName.contains("Salaar");
              } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
              }
            },
            (key, value) -> true
    );
  }

  public static Tweet convertToTweet(String jsonString) throws JsonProcessingException {
    return objectMapper.readValue(jsonString, Tweet.class);
  }
}
