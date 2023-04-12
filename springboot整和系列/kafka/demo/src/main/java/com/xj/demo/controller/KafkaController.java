package com.xj.demo.controller;

import com.xj.demo.common.KafkaProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 努力的派大星
 * @version 1.0
 */
@RestController
public class KafkaController {
    private static final String TOPIC = "my-topic";

    private KafkaProducer kafkaProducer;

    public KafkaController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @GetMapping("/hello/{message}")
    public String postKafka(@PathVariable("message") final String message){
        kafkaProducer.sendMessage(TOPIC, message);
        return "success";
    }
}
