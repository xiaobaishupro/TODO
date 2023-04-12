package com.xj.demo.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author 努力的派大星
 * @version 1.0
 */
@Slf4j
@Component
public class KafkaProducer{

    private KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        log.info("on message:{}", message);
        kafkaTemplate.send(topic, message);
    }
}

