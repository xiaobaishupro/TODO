package com.example.securitystudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 努力的派大星
 * @version 1.0
 */
@Controller
public class LoginController {

    @RequestMapping("/login")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/home")
    public String home(){
        return "home";
    }

    @RequestMapping("/myError")
    public String myError(){
        return "myError";
    }

    @RequestMapping("/admin")
    public String admin(){
        return "admin";
    }

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }

    @RequestMapping("/order")
    public String order(){
        return "order";
    }
}
