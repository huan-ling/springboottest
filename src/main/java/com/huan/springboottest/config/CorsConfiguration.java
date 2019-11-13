package com.huan.springboottest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: Huan
 * @CreateTime: 2019-06-20 15:58
 */
@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        registry.
//                addMapping("/**").
//                allowedHeaders("*").
//                allowedMethods("*").
//                allowedOrigins("*").
//                allowCredentials(true);
    }
}
