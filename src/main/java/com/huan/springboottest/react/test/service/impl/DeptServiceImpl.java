package com.huan.springboottest.react.test.service.impl;

import com.github.pagehelper.PageHelper;
import com.huan.springboottest.react.test.dto.DeptTreeDto;
import com.huan.springboottest.react.test.mapper.DeptMapper;
import com.huan.springboottest.react.test.pojo.Dept;
import com.huan.springboottest.react.test.service.DeptService;
import com.huan.springboottest.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Huan
 * @CreateTime: 2019-07-15 14:17
 */
@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;

    @Override
    public PageUtil<Dept> getAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Dept> deptList = deptMapper.getAll();
        return new PageUtil<>(deptList);
    }

    @Override
    public List<DeptTreeDto> getTree(){
        DeptTreeDto deptTreeDto = new DeptTreeDto();
        deptTreeDto.setId(0);
        return getTreeDeep(deptTreeDto);
    }


    private List<DeptTreeDto> getTreeDeep(DeptTreeDto deptTreeDto){
        List<DeptTreeDto> deptList = deptMapper.getChildById(deptTreeDto.getId());
        if(deptList != null && deptList.size()>0){
            deptList.forEach(dept-> {
                List<DeptTreeDto> treeDeepList = getTreeDeep(dept);
                dept.setDeptList(treeDeepList);
            });
        }
        return deptList;
    }
}
