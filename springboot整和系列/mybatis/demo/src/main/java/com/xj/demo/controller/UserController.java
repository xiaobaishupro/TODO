package com.xj.demo.controller;

import com.xj.demo.pojo.User;
import com.xj.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 努力的派大星
 * @version 1.0
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getList(){
        List<User> list = userService.getList();
        list.forEach(System.out::println);
        return list;
    }

    @GetMapping("/getUserById/{userId}")
    public User getUserById(@PathVariable Integer id){
        return userService.getUserById(id);
    }

    @PostMapping("/add")
    public Integer add(@RequestBody User user){
        return userService.add(user);
    }

    @PostMapping("/update")
    public void update(@RequestBody User user){
        userService.update(user);
    }

    @PostMapping("/delete")
    public void delete(@RequestParam Integer userId){
        userService.delete(userId);
    }
}
