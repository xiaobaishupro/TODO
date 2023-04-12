## 一、简介

Kafka是一个分布式流处理平台，它可以处理实时数据流。

#### 1.下载

kafka下载(kafka自带zookeeper)：https://kafka.apache.org/downloads

zookeeper下载：http://archive.apache.org/dist/zookeeper/

#### 2.配置

kafka-:config目录下server.properties

zookeeper:config目录下zookeeper.properties

#### 3.启动

windows启动：

zookeeper-server-start.bat +配置文件路径

kafka-server-start.bat +配置文件路径

#### 4.demo

demo使用单机环境，使用kafka自带的zookeeper

整合参考：https://docs.spring.io/spring-kafka/docs/current/reference/html/

## 二、整合springboot

### 1.yml配置

```yml
kafka:
  bootstrap-servers: localhost:9092
  #生产者
  producer:
    acks: 1
    retries: 3
    key-serializer: org.apache.kafka.common.serialization.StringSerializer
    value-serializer: org.apache.kafka.common.serialization.StringSerializer
  #消费者
  consumer:
    #默认消费组名
    group-id: my-group
    #关闭自动提交
    enable-auto-commit: true
    #提交间隔的毫秒 spring.kafka.consumer.auto-commit-interval.ms=60000
    # kafka消费指定每次最大消费消息数量
    max-poll-records: 50
    #当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，从头开始消费
    #auto-offset-reset: earliest
    auto-offset-reset: latest
    key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
    value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
  listener:
    #手动提交
    ack-mode: manual_immediate
    # 设置批量消费
    type: batch
    # 并发数设置
    concurrency: 3
```

### 2.config

#### 1.KafkaConsumerConfig

```java
@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        //设置为批量消费，每个批次数量在Kafka配置参数中设置ConsumerConfig.MAX_POLL_RECORDS_CONFIG
        factory.setBatchListener(true);
        // 设置消息转换器
        factory.setMessageConverter(new StringJsonMessageConverter());
        return factory;
    }

}
```

#### 2.KafkaProducerConfig

```java
@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
```

### 3.生成者和消费者

#### 1.KafkaConsumer

```java
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
```

#### 2.KafkaProducer

```java
@Slf4j
@Component
public class KafkaProducer {

    private KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        log.info("on message:{}", message);
        kafkaTemplate.send(topic, message);
    }
}
```

### 4.controller

测试

#### KafkaController

```java
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
```
