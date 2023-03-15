package com.xj.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {
    private static Logger logger = LogManager.getLogger(DemoApplicationTests.class);

    @Test
    void contextLoads() {
        test1();
    }

    public static void test1() {
        logger.info("123");
        logger.info("你好呀");
        logger.error("发生错误了");
    }

}
