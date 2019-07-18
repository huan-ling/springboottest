package com.huan.springboottest.react.test.mapper;

import com.huan.springboottest.react.test.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Huan
 * @CreateTime: 2019-07-15 10:16
 */
@Repository
public interface UserMapper {

    List<User> getAll();

    List<User> getByDId(@Param("deptId") int deptId);
}
