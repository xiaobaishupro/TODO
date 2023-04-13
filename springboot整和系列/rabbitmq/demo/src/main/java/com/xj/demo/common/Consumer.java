package com.xj.demo.common;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author 努力的派大星
 * @version 1.0
 */
@Slf4j
@Component
public class Consumer {

    @RabbitListener(queues = "my_queue")
    public void getMessage(Message message) {
        log.info("message:===> {}", message); // 这里 不只是输出 单个 发送的信息，而是 全部输出 消息里面的内容数据
    }
}
