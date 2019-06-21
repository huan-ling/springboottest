package com.huan.springboottest.common.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Description: 获取spring容器的对象，通过spring容器获取spring容器注入的Bean
 * @Author: Huan
 * @CreateTime: 2019-03-01 13:15
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    public static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringUtil.applicationContext == null){
            SpringUtil.applicationContext = applicationContext;
        }
    }

    /**
     * 通过类型获取spring容器中的bean
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clz){
        return applicationContext.getBean(clz);
    }

    public static <T> T getBean(String name,Class<T> clz){
        return applicationContext.getBean(name, clz);
    }

}
