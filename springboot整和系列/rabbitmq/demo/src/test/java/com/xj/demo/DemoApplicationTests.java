package com.xj.demo;

import com.xj.demo.config.RabbitMQConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    // 注入 rabbitMQtemplate模板类 ，不注入 就是空指针异常等你
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSend(){
        // 发送消息
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,"boot.hello","这是整合：testSend方法发送的消息");
    }
}
