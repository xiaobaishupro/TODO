### 一、前言

使用Spring Boot整合Elasticsearch十分简单。你可以在Spring Boot的上下文中定义Elasticsearch的客户端并使用Elasticsearch的REST API来执行各种操作，如索引文档、搜索等。

`ES和` Kibana`官网下载地址： https://www.elastic.co/downloads/past-releases

bin目录下的bat启动

ik分词器插件下载：https://github.com/medcl/elasticsearch-analysis-ik/releases

下载后解压至es文件夹plugins下的ik中

demo版本：springboot2.5.1、Elasticsearch8.1.2

springboot2.5.1:spring-data-elasticsearch版本为4.2.1

spring-data-elasticsearch:https://docs.spring.io/spring-data/elasticsearch/docs

### 二、整合

#### 1.依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
</dependency>
```

#### 2.yml配置

```yml
server:
  port: 8080

spring:
  data:
    elasticsearch:
      client:
        reactive:
          username: elasticsearch
      repositories:
        enabled: true

  datasource:
    url: jdbc:mysql://127.0.0.1:3306/mybatis_plus?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF8&zeroDateTimeBehavior=convertToNull&useSSL=false # MySQL在高版本需要指明是否进行SSL连接 解决则加上 &useSSL=false
    name: demo
    username: root
    password: root
    # 使用druid数据源
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.jdbc.Driver
    filters: stat
    maxActive: 20
    initialSize: 1
    maxWait: 60000
    minIdle: 1
    timeBetweenEvictionRunsMillis: 60000
    minEvictableIdleTimeMillis: 300000
    validationQuery: select 'x'
    testWhileIdle: true
    testOnBorrow: false
    testOnReturn: false
    poolPreparedStatements: true
    maxOpenPreparedStatements: 20
    # mybatis-plus相关配置
mybatis-plus:
  # xml扫描，多个目录用逗号或者分号分隔（告诉 Mapper 所对应的 XML 文件位置）
  mapper-locations: classpath:**/*Mapper.xml
  configuration:
    #        日志
    log-impl: org.apache.ibatis.logging.slf4j.Slf4jImpl
  # 以下配置均有默认值,可以不设置
  global-config:
    #主键类型  0:"数据库ID自增", 1:"用户输入ID",2:"全局唯一ID (数字类型唯一ID)", 3:"全局唯一ID UUID";
    id-type: 0
    #字段策略 0:"忽略判断",1:"非 NULL 判断"),2:"非空判断"
    field-strategy: 2
    #驼峰下划线转换
    db-column-underline: true
    #刷新mapper 调试神器
    refresh-mapper: false

```

#### 3.实体类Essay

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "essay")
@TableName("essay")
public class Essay {
    @Id
    private long id;

    @Field(type = FieldType.Integer)
    private int userId;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String title;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String content;

    @Field(type = FieldType.Integer)
    private int type;

    @Field(type = FieldType.Integer)
    private int status;

    @Field(type = FieldType.Date)
    private Date createTime;

    @Field(type = FieldType.Integer)
    private int commentCount;

    @Field(type = FieldType.Double)
    private double score;
}
```

#### 4.集群配置

使用配置文件或者配置类注入ES集群的客户端

```java
@Configuration
public class RestClientConfig extends AbstractElasticsearchConfiguration {

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {

        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("localhost:9200")//
                .build();

        return RestClients.create(clientConfiguration).rest();
    }
}
```

#### 5.使用ElasticsearchRepository封装的方法

> 不需要任何实现类我们就能实现对ES基本的增删改查。（当然复杂的操作需要自定义实现）



```java
@Component
public interface EssayRepository extends ElasticsearchRepository<Essay, Long> {
    /**
     * 使用@Query注解自定义查询语句，其中的?是占位符，0表示第一个参数
     * @param content
     * @return
     */
    @Query("{\n" +
            "    \"bool\": {\n" +
            "      \"must\": [\n" +
            "        {\n" +
            "          \"match\": {\n" +
            "            \"content\": \"?0\"\n" +
            "          }\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  }")
    List<Essay> selectByContent(String content);
}
```

##### 测试

```java
@SpringBootTest
class DemoApplicationTests {
    @Autowired
    private EssayMapper essayMapper;
    @Autowired
    private EssayRepository essayRepository;

    /**
     * 增加、修改
     */
    @Test
    void save() {
//        essayRepository.save(essayMapper.selectById(1));
        List<Essay> essays = essayMapper.selectList(null);
        essayRepository.saveAll(essays);
    }

    /**
     * 删除
     */
    @Test
    void delete() {
        essayRepository.deleteById(2l);
    }

    /**
     * 查所有
     */
    @Test
    void selectAll() {
        Iterable<Essay> all = essayRepository.findAll();
        Iterator<Essay> iterator = all.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    /**
     * 通过id查
     */
    @Test
    void selectById() {
        Optional<Essay> essay = essayRepository.findById(24l);
        System.out.println(essay);
    }

    /**
     * 根据内容模糊查询
     */
    @Test
    void selectByContent() {
        List<Essay> essays = essayRepository.selectByContent("海外");
        System.out.println(essays);
    }

    /**
     * 排序查
     */
    @Test
    void selectBySort() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Iterable<Essay> essays = essayRepository.findAll(sort);
        Iterator<Essay> iterator = essays.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    /**
     * 多个条件排序
     */
    @Test
    void selectBySorts() {
        ArrayList<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.ASC, "userId"));
        orders.add(new Sort.Order(Sort.Direction.DESC, "id"));
        Sort sort = Sort.by(orders);
        Iterator<Essay> iterator = essayRepository.findAll(sort).iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

    /**
     * 限制数据量
     */
    @Test
    void selectByPage() {
        Pageable pageable = Pageable.ofSize(10);
        Page<Essay> page = essayRepository.findAll(pageable);
        page.forEach(System.out::println);
    }

    /**
     * 分页查
     */
    @Test
    void selectByPage1() {
        Pageable pageable = PageRequest.of(1, 10);
        Page<Essay> page = essayRepository.findAll(pageable);
        page.forEach(System.out::println);
    }

    /**
     * 分页排序
     */
    @Test
    void selectByPageAndSort() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(1, 10, sort);
        Page<Essay> page = essayRepository.findAll(pageable);
        page.forEach(System.out::println);
    }
}
```

#### 6.使用ElasticsearchOperations

- ElasticsearchOperations是spring data es操作ES的一个接口，在4.x的版本它的默认实现是`ElasticsearchRestTemplate`。

##### 测试

```java
@SpringBootTest
public class EsTemplateTest {
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Test
    void save(){
        Essay essay = new Essay();
        essay.setId(999);
        essay.setTitle("swwxwwxwdwdwdwdwdddw");
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(essay.getId()+"")
                .withObject(essay)
                .build();
        IndexCoordinates indexCoordinates = elasticsearchOperations.getIndexCoordinatesFor(essay.getClass());
        elasticsearchOperations.index(indexQuery, indexCoordinates);
    }
    @Test
    public void testQuery() {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(new MatchAllQueryBuilder())
                .build();

        SearchHits<Essay> searchHits = elasticsearchOperations.search(searchQuery, Essay.class);
        long count = searchHits.getTotalHits();
        System.out.println(count);
        List<SearchHit<Essay>> list = searchHits.getSearchHits();
        for (SearchHit hit:list) {
            System.out.println(hit.getContent());
        }
    }
}
```

