package com.huan.springboottest.react.test.mapper;

import com.huan.springboottest.react.test.pojo.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @Author: wb_xugz
 * @CreateTime: 2019-11-13 16:32
 */
public interface UserTestMapper extends CrudRepository<User, Integer> {

    List<User> findByUName(String uName);

}
