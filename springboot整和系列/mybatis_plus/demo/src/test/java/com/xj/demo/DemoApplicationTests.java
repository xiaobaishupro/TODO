package com.xj.demo;

import com.baomidou.mybatisplus.plugins.Page;
import com.xj.demo.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DemoApplicationTests {


    /**
     * 查
     */
    @Test
    void select() {
        User user = new User();
        user.setUserId(3);
        User user1 = user.selectById();
        System.out.println(user1);
    }

    /**
     * 查所有
     */
    @Test
    void selectAll() {
        User user = new User();
        List<User> users = user.selectAll();
        users.forEach(System.out::println);
    }

    /**
     * 添加
     */
    @Test
    void add() {
        User user = User.builder()
                .userId(9)
                .userName("哆啦a梦")
                .build();
        user.insert();
    }

    /**
     * 修改
     */
    @Test
    void update(){
        User user = User.builder()
                .userId(5)
                .userName("哆啦a梦yy")
                .build();
        user.updateById();
    }

    /**
     * 删除
     */
    @Test
    void delete(){
        User user = new User();
        user.setUserId(3);
        user.deleteById();
    }

    /**
     * 分页查询
     */
    @Test
    void testSelectAllPage() throws Exception{
        User user = new User();
        Page<User> page = user.selectPage(new Page<User>(1, 10), null);
        System.out.println(page);
    }

}
