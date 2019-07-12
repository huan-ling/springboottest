package com.huan.springboottest.jdk8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Huan
 * @CreateTime: 2019-06-25 14:37
 */
public class RefUtil {

    @Test
    public void test1() {
        List<String> names = new ArrayList<>();
        names.add("Google");
        names.add("Runoob");
        names.add("Taobao");
        names.add("Baidu");
        names.add("Sina");
        names.forEach(System.out::println);
    }
}
