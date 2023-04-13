## 一、简介

1.RabbitMQ 是一个消息中间件：它接受并转发消息。

#### 1.下载

windows

Windows下载地址：https://www.rabbitmq.com/install-windows.html

进去之后可以直接找到**Direct Downloads**，下载相关EXE程序进行安装就可以了。

由于RabbitMQ是由erlang语言编写的，所以安装之前我们还需要安装erlang环境，你下载RabbitMQ之后直接点击安装，如果没有相关环境，安装程序会提示你，然后会让你的浏览器打开erlang的下载页面，在这个页面上根据自己的系统类型点击下载安装即可，安装完毕后再去安装RabbitMQ。

这两者的安装都只需要一直NEXT下一步就可以了。

#### 2.启动

RabbitMQ Server目录下

start服务：RabbitMQ Service - start

stop服务:RabbitMQ Service - stop

ui界面：

sbin目录下cmd：输入rabbitmq-plugins enable rabbitmq_management回车

ui页面地址：http://127.0.0.1:15672

用户名密码为：guest

#### 3.demo

##### 1.依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

##### 2.yml

```yml
spring:
  rabbitmq:
    host: 127.0.0.1
    port: 5672
    username: guest
    password: guest
```

##### 3.RabbitMQConfig 

```java
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
```

##### 4.Producer

```java
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
```

##### 5.Consumer

```java
@Slf4j
@Component
public class Consumer {

    @RabbitListener(queues = "my_queue")
    public void getMessage(Message message) {
        log.info("message:===> {}", message); // 这里 不只是输出 单个 发送的信息，而是 全部输出 消息里面的内容数据
    }
}
```