package com.huan.springboottest.test;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.HashMap;

/**
 * @Author: wb_xugz
 * @CreateTime: 2019-11-25 14:10
 */
@Data(staticConstructor = "of")
@Slf4j
public class MyTest3 {

    public static void main(String[] args) {
        val a = new HashMap<>();
        val b = 1;
        MyTest3 myTest3 = MyTest3.of();

        f(null);
        log.info("{}{}", "a", "b");
    }

    public static void f(Integer a) {
        System.out.println("a = " + a);
    }


}

