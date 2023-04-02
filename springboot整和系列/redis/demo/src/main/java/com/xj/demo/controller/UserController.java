package com.xj.demo.controller;

import com.xj.demo.pojo.User;
import com.xj.demo.service.UserService;
import com.xj.demo.utils.RedisUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author 努力的派大星
 * @version 1.0
 */
@Controller
public class UserController {

    @Resource
    private RedisUtil redisUtil;

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getUserById/{userId}")
    @ResponseBody
    public User getUser(@PathVariable Integer userId) {
        if (redisUtil.hasKey("user" + userId)){
            return (User) redisUtil.get("user" + userId);
        }
        User user = userService.selectById(userId);
        redisUtil.set("user" + userId,user);  //缓存
        return user;
    }

    @GetMapping("/deleteUserById/{userId}")
    @ResponseBody
    public String deleteUser(@PathVariable Integer userId) {
        if (redisUtil.hasKey("user" + userId)){
            redisUtil.del("user" + userId);
            userService.deleteById(userId);
            return "success";
        }
        userService.deleteById(userId);
        return "success";
    }

}
