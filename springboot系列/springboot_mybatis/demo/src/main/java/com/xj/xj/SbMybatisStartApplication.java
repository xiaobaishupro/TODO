package com.xj.xj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xj.xj.domain.mapper")
public class SbMybatisStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(SbMybatisStartApplication.class, args);
    }

}
