## mybatis（quickstart）

#### 1.pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.5.1</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.xj</groupId>
    <artifactId>demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>demo</name>
    <description>demo</description>
    <properties>
        <java.version>1.8</java.version>
        <mysql.version>5.1.47</mysql.version>
        <commons-lang3.version>3.6</commons-lang3.version>
        <hutool-all.version>4.6.2</hutool-all.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <!-- mybatis begin =================================== -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.2.2</version>
        </dependency>
        <!-- mybatis-plus end -->

        <!-- ========================= 数据库相关 ========================== -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql.version}</version>
        </dependency>
        <!-- 阿里数据库连接池 -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.0.18</version>
        </dependency>

        <!-- ========================= 常用库依赖 ========================== -->
        <!-- lombok插件 -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>com.spring4all</groupId>
            <artifactId>spring-boot-starter-swagger</artifactId>
            <version>1.5.1.RELEASE</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

#### 2.application.yml

```yml
# \u914D\u7F6E\u7AEF\u53E3
server:
  port: 8080
  servlet:
    #    context-path: /api
    application-display-name: demo

spring:
  application:
    name: demo
  profiles:
    active: dev
  # \u914D\u7F6E\u6570\u636E\u6E90
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/study_xj?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF8&zeroDateTimeBehavior=convertToNull&useSSL=false # MySQL\u5728\u9AD8\u7248\u672C\u9700\u8981\u6307\u660E\u662F\u5426\u8FDB\u884CSSL\u8FDE\u63A5 \u89E3\u51B3\u5219\u52A0\u4E0A &useSSL=false
    name: demo
    username: root
    password: root
    # \u4F7F\u7528druid\u6570\u636E\u6E90
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

management:
  security:
    enabled: false


#mybatis
mybatis:
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: com.xj.demo.pojo
  configuration:
    # \u5F00\u542F\u9A7C\u5CF0uName\u81EA\u52A8\u6620\u5C04\u5230u_name
    map-underscore-to-camel-case: true
```

#### 3.userMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.xj.demo.mapper.UserMapper">
    <select id="getList" resultType="com.xj.demo.pojo.User">
        select user_id,user_name from user;
    </select>
    <select id="getUserById" resultType="com.xj.demo.pojo.User">
        select user_id,user_name from user where user_id = #{userId}
    </select>
    <insert id="add" parameterType="com.xj.demo.pojo.User">
        insert into user(user_id,user_name) values (#{userId},#{userName})
    </insert>
    <update id="update" parameterType="com.xj.demo.pojo.User">
        update user set user_name = #{userName} where user_id = #{userId}
    </update>
    <delete id="delete" parameterType="integer">
        delete from user where user_id = #{userId}
    </delete>
</mapper>
```