package com.huan.springboottest.test.controller;

import com.google.common.collect.Maps;
import com.huan.springboottest.event.MyEvent;
import com.huan.springboottest.react.test.mapper.MybatisPlusUserMapper;
import com.huan.springboottest.react.test.mapper.UserTestMapper;
import com.huan.springboottest.react.test.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Huan
 * @CreateTime: 2019-07-12 09:25
 */
@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private UserTestMapper userTestMapper;

    @Autowired
    private MybatisPlusUserMapper mybatisPlusUserMapper;

    @GetMapping("get")
    public List<User> getEvent(String uName) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("u_name", uName);

        return mybatisPlusUserMapper.selectByMap(map);
    }
}
