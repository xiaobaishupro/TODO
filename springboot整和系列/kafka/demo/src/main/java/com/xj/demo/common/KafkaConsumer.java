package com.xj.demo.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 努力的派大星
 * @version 1.0
 */
@Slf4j
@Component
public class KafkaConsumer{


//    @KafkaListener(topics = "my-topic", groupId = "my-group", containerFactory = "kafkaListenerContainerFactory")
//    public void onMessage(ConsumerRecord<String, String> record) {
//        log.info("listen Received message >>> {}", record);
//    }

    @KafkaListener(topics = "my-topic", groupId = "my-group", containerFactory = "kafkaListenerContainerFactory")
    public void handleMessage(List<ConsumerRecord<String, String>> records) {
        for (ConsumerRecord<String, String> record : records) {
            log.info("listen... ====>> {}", record.value());
        }
    }
}
