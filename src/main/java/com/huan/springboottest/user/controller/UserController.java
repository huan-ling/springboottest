package com.huan.springboottest.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: Huan
 * @CreateTime: 2019-06-20 11:54
 */
@RestController
@RequestMapping("user")
public class UserController {

    @GetMapping("get")
    public String get(){
        return "OK!";
    }

    @GetMapping("test")
    public String test(){
        return "11";
    }
}
