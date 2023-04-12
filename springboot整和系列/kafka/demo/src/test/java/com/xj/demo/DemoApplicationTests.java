package com.xj.demo;

import com.xj.demo.common.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    private static final String TOPIC = "my-topic";

    @Autowired
    KafkaProducer kafkaProducer;

    @Test
    void test() {
        for (int i = 0; i < 20; i++) {
            kafkaProducer.sendMessage(TOPIC, "message-" + i);
        }

    }

}
