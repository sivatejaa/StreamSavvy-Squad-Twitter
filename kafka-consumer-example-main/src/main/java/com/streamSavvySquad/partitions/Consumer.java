package com.streamSavvySquad.partitions;

import com.streamSavvySquad.consumer.KafkaMessageListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class Consumer {

    @Autowired
    private KafkaTemplate<String,Object> template;
    Logger log = LoggerFactory.getLogger(KafkaMessageListener.class);
    public void consume2(String message) {
        log.info("consumer2 consume the message {} ", message);
    }
  /*  @KafkaListener(topics = "partions-specific",groupId = "jt-group-new",
            topicPartitions = {@TopicPartition(topic="partions-specific",partitions = {"3"})})*/
        public void listen(ConsumerRecord<String, String> record) {
            try {
                String message = record.value();
                processMessage(message);
            } catch (Exception e) {
                handleRetry(record, e);
            }
        }
        private void handleRetry(ConsumerRecord<String, String> record, Exception exception) {
            for (int i = 0; i < 3; i++) {
                try {
                    processMessage(record.value());
                    return;
                } catch (Exception e) {
                    exception = e;
                }
            }

            sendMessage("deadLetterTopic", record.value());

            System.out.println("Message sent to dead letter topic due to exception: " + exception.getMessage());
        }

    private void processMessage(String message) throws RuntimeException{
        if (message.contains("error")) {
            throw new RuntimeException("Error processing message: " + message);
        }

        template.send("retry-topic",null, message);
        System.out.println("Processing message: " + message);
    }

        private void sendMessage(String topic, String message) {

            CompletableFuture<SendResult<String, Object>> future = template.send("dtl-topic",null, message);
        }
}
