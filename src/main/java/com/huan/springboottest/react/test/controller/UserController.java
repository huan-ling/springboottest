package com.huan.springboottest.react.test.controller;

import com.huan.springboottest.common.dto.ResponseDto;
import com.huan.springboottest.react.test.pojo.User;
import com.huan.springboottest.react.test.service.UserService;
import com.huan.springboottest.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Huan
 * @CreateTime: 2019-07-15 10:03
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("getAll")
    public ResponseDto<PageUtil<User>> getAll(int pageNum, int pageSize){
        return new ResponseDto<>(userService.getAll(pageNum,pageSize));
    }


    @GetMapping("getByDId")
    public ResponseDto<PageUtil<User>> getByDId(int pageNum, int pageSize,int deptId){
        return new ResponseDto<>(userService.getByDId(pageNum,pageSize,deptId));
    }
}
