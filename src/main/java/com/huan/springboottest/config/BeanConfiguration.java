package com.huan.springboottest.config;

import com.huan.springboottest.event.MyEvent;
import com.huan.springboottest.listener.MyListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Huan
 * @CreateTime: 2019-07-12 09:22
 */
@Configuration
public class BeanConfiguration {

    @Bean
    public MyListener setListener(){
        return new MyListener();
    }

    @Bean
    public MyEvent setEvent(){
        return new MyEvent("自定义事件");
    }

}
