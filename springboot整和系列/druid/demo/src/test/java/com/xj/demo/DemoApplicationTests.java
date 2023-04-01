package com.xj.demo;

import com.xj.demo.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
        User user = new User();
        user.setUserId(3);
        user.selectAll().forEach(System.out::println);
    }

}
