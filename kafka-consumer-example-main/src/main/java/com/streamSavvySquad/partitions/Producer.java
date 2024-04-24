package com.streamSavvySquad.partitions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.streamSavvySquad.utils.ExcelGenerator;
import com.streamSavvySquad.utils.Tweet;
import com.streamSavvySquad.utils.TweetGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.streamSavvySquad.consumer.KafkaMessageListener.getDunkiMessages;
import static com.streamSavvySquad.consumer.KafkaMessageListener.getSalaarMessages;

@Service
@EnableScheduling
public class Producer {

    @Autowired
    private KafkaTemplate<String,Object> template;

    static int[] categoryCounts = new int[4];
    static int[] categoryCounts2 = new int[4];
    Logger log = LoggerFactory.getLogger(Producer.class);

    @Scheduled(fixedDelay = 60000)
    public void sendMessageToTopic() throws JsonProcessingException {
        List<Tweet> messages = getSalaarMessages();
        for (Tweet tweet : messages) {
            int partition = 0;
            switch (tweet.getHashtag()) {
                case "#hit" -> {
                    partition = 0;
                    categoryCounts[0]++;
                }
                case "#blockbuster" -> {
                    partition = 1;
                    categoryCounts[1]++;
                }
                case "#flop" -> {
                    partition = 2;
                    categoryCounts[2]++;
                }
                case "#disaster" -> {
                    partition = 3;
                    categoryCounts[3]++;
                }
            }
            String json = TweetGenerator.convertToJsonString(tweet);
            CompletableFuture<SendResult<String, Object>> future = template.send("salaar-partitionss", partition, String.valueOf(tweet.getId()), json);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                   // System.out.println("Sent message=[" + json + "] with offset=[" + result.getRecordMetadata().offset() + "]");
                } else {
                   // System.out.println("Unable to send message=[" + json + "] due to : " + ex.getMessage());
                }
            });
        }

        String[] categories = {"#hit", "#blockbuster", "#flop", "#disaster"};
        ExcelGenerator.generateExcelWithCounts(categories, categoryCounts, "salaar_tweets_analysis.xlsx");

        log.info("--------------> salaar hit- "+categoryCounts[0]+" \n #blockbuster "+categoryCounts[1]+" \n #flop "+categoryCounts[2]+"\n #disaster "+categoryCounts[3]+" <------------------");



        List<Tweet> messages2 = getDunkiMessages();
        for (Tweet tweet : messages2) {
            int partition = 0;
            switch (tweet.getHashtag()) {
                case "#hit" -> {
                    partition = 0;
                    categoryCounts2[0]++;
                }
                case "#blockbuster" -> {
                    partition = 1;
                    categoryCounts2[1]++;
                }
                case "#flop" -> {
                    partition = 2;
                    categoryCounts2[2]++;
                }
                case "#disaster" -> {
                    partition = 3;
                    categoryCounts2[3]++;
                }
            }
            String json = TweetGenerator.convertToJsonString(tweet);
            CompletableFuture<SendResult<String, Object>> future = template.send("dunki-partitionss", partition, String.valueOf(tweet.getId()), json);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    //System.out.println("Sent message=[" + json + "] with offset=[" + result.getRecordMetadata().offset() + "]");
                } else {
                   // System.out.println("Unable to send message=[" + json + "] due to : " + ex.getMessage());
                }
            });
        }
        // Generate Excel file with category counts
        log.info("-------------->dunki hit- "+categoryCounts2[0]+" \n #blockbuster "+categoryCounts2[1]+"  \n #flop "+categoryCounts2[2]+" \n #disaster "+categoryCounts2[3]);

        String[] categories2 = {"#hit", "#blockbuster", "#flop", "#disaster"};
        ExcelGenerator.generateExcelWithCounts(categories2, categoryCounts2, "dunki_tweets_analysis.xlsx");



    }

}
