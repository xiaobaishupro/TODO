package com.example.ruijistudy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class RuijiStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(RuijiStudyApplication.class, args);
        log.info("nimeide===========================");
    }

}
