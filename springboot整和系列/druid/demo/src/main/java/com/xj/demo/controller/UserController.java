package com.xj.demo.controller;

import com.xj.demo.pojo.User;
import com.xj.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 努力的派大星
 * @version 1.0
 */
@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    @ResponseBody
    public List<User> getUser() {
        return userService.list();
    }
}
