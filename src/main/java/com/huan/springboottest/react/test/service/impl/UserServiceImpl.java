package com.huan.springboottest.react.test.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.huan.springboottest.react.test.mapper.UserMapper;
import com.huan.springboottest.react.test.pojo.User;
import com.huan.springboottest.react.test.service.UserService;
import com.huan.springboottest.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Huan
 * @CreateTime: 2019-07-15 10:23
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageUtil<User> getAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<User> userList = userMapper.getAll();
        return new PageUtil<>(userList);
    }

    @Override
    public PageUtil<User> getByDId(int pageNum, int pageSize,int deptId){
        PageHelper.startPage(pageNum,pageSize);
        List<User> deptList = userMapper.getByDId(deptId);
        return new PageUtil<>(deptList);
    }
}
