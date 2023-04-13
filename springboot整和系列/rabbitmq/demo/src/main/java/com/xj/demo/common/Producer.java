package com.xj.demo.common;

import com.xj.demo.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 努力的派大星
 * @version 1.0
 */
@Slf4j
@RestController
public class Producer {

    private RabbitTemplate rabbitTemplate;

    public Producer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/hello/{message}")
    public String postMessage(@PathVariable("message") final String message){
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,"my.hello",message);
        log.info("send: ===> {}",message);
        return "success";
    }
}
