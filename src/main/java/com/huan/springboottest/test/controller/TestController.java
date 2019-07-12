package com.huan.springboottest.test.controller;

import com.huan.springboottest.event.MyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Huan
 * @CreateTime: 2019-07-12 09:25
 */
@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping("get")
    public String getEvent(){
        MyEvent event = new MyEvent("自定义事件");
        applicationContext.publishEvent(event);
        return "OK";
    }
}
