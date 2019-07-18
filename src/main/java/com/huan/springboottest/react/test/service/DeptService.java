package com.huan.springboottest.react.test.service;

import com.huan.springboottest.react.test.dto.DeptTreeDto;
import com.huan.springboottest.react.test.pojo.Dept;
import com.huan.springboottest.util.PageUtil;

import java.util.List;

/**
 * @Author: Huan
 * @CreateTime: 2019-07-15 14:14
 */
public interface DeptService {

    PageUtil<Dept> getAll(int pageNum,int pageSize);

    List<DeptTreeDto> getTree();
}
