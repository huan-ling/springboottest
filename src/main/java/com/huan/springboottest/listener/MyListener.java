package com.huan.springboottest.listener;

import com.huan.springboottest.event.MyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * @Author: Huan
 * @CreateTime: 2019-07-12 09:13
 */
public class MyListener implements ApplicationListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyListener.class);

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if(event instanceof MyEvent){
            LOGGER.info("自定义事件={}",event);
        }
    }
}
