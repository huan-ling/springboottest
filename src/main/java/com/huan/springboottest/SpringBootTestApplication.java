package com.huan.springboottest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.util.ClassUtils;

@SpringBootApplication
@MapperScan("com.huan.springboottest.react.test.mapper")
@EnableSpringDataWebSupport
public class SpringBootTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootTestApplication.class, args);
    }

}
