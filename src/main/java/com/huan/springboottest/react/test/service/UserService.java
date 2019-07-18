package com.huan.springboottest.react.test.service;

import com.huan.springboottest.react.test.pojo.User;
import com.huan.springboottest.util.PageUtil;

import java.util.List;

/**
 * @Author: Huan
 * @CreateTime: 2019-07-15 10:22
 */
public interface UserService {

    PageUtil<User> getAll(int pageNum, int pageSize);

    PageUtil<User> getByDId(int pageNum, int pageSize,int deptId);
}
