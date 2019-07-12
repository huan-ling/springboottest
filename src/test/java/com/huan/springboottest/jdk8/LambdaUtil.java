package com.huan.springboottest.jdk8;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Huan
 * @CreateTime: 2019-06-25 10:39
 */
public class LambdaUtil {

    @Test
    public void test1(){
        List<String> list = new ArrayList<>();
        list.forEach(s -> {
            try {
                FileInputStream fis = new FileInputStream(s);
            } catch (FileNotFoundException e) {
                throw new RuntimeException();
            }
        });
    }

}
