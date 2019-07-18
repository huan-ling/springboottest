package com.huan.springboottest.react.test.mapper;

import com.huan.springboottest.react.test.dto.DeptTreeDto;
import com.huan.springboottest.react.test.pojo.Dept;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Huan
 * @CreateTime: 2019-07-15 14:18
 */
@Repository
public interface DeptMapper {

    List<Dept> getAll();

    Dept getById(@Param("id") int id);

    List<DeptTreeDto> getChildById(@Param("id") int id);
}
