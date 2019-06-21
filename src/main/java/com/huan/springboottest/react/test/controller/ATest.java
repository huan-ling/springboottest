package com.huan.springboottest.react.test.controller;

import com.huan.springboottest.common.dto.ResponseDto;
import com.huan.springboottest.react.test.pojo.StepField;
import com.huan.springboottest.react.test.service.ATestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Huan
 * @CreateTime: 2019-06-21 10:00
 */
@RestController
@RequestMapping("a")
public class ATest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ATest.class);

    @Autowired
    private ATestService aTestService;

    @GetMapping("get")
    public ResponseDto<List<StepField>> get(){
        LOGGER.info("收到请求");
        return new ResponseDto<>(aTestService.get());
    }
}
