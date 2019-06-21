package com.huan.springboottest.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public Msg get(String msg){
        return new Msg(msg);
    }

    @PostMapping("test")
    public String test(String msg){
        return "11"+msg;
    }

    class Msg{
        private String data;

        public Msg(String data){
            this.data = data;
        }
        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}
