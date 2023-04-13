package com.xj.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 努力的派大星
 * @version 1.0
 */
@Configuration // 声明为 配置类
public class RabbitMQConfig {
    /**
     * 设置三部分
     * 交换机 Exchange
     * 队列 Queue
     * 绑定关系 Binding
     */

    // 声明 交换机名称
    public static final String EXCHANGE_NAME = "my_topic_exchange";
    // 声明 队列名称
    public static final String QUEUE_NAME = "my_queue";

    //1 配置交换机
    @Bean("myExchange") //设置BeanName 为 bootExchanghe
    public Exchange myExchange(){
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();
    }

    //2 Queue 队列
    @Bean("myQueue") //设置BeanName 为 bootQueue
    public Queue myQueue(){
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    //3 队列 与交换机的绑定
    /*
     1、要知道那个队列
     2、要知道那个交换机
     3、知道那个路由Key
     @Qualifier  自动装配
     */
    @Bean("bootBind") //设置BeanName 为 bootBind
    public Binding bootBindQueueExchange(@Qualifier("myQueue") Queue queue,@Qualifier("myExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("my.#").noargs();
    }
}
