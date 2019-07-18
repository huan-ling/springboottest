package com.huan.springboottest.react.test.controller;

import com.huan.springboottest.common.dto.ResponseDto;
import com.huan.springboottest.react.test.dto.DeptTreeDto;
import com.huan.springboottest.react.test.pojo.Dept;
import com.huan.springboottest.react.test.service.DeptService;
import com.huan.springboottest.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Huan
 * @CreateTime: 2019-07-15 14:13
 */
@RestController
@RequestMapping("dept")
public class DeptController {
    @Autowired
    private DeptService deptService;

    @GetMapping("getAll")
    public ResponseDto<PageUtil<Dept>> getAll(int pageNum,int pageSize){
        return new ResponseDto<>(deptService.getAll(pageNum,pageSize));
    }

    @GetMapping("getTree")
    public ResponseDto<List<DeptTreeDto>> getTree(){
        return new ResponseDto<>(deptService.getTree());
    }
}
