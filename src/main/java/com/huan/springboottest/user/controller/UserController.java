package com.huan.springboottest.user.controller;

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

    @RequestMapping("get")
    public String get(){
        return "OK!";
    }
}
