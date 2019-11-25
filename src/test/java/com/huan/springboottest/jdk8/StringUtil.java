package com.huan.springboottest.jdk8;

import java.util.StringJoiner;

/**
 * StringJoiner拼接字符串
 * @Author: wb_xugz
 * @CreateTime: 2019-11-14 13:03
 */
public class StringUtil {

    public static void main(String[] args) {
        StringJoiner sj = new StringJoiner(",","[","]");

        sj.add("1").add("2").add("3");

        System.out.println("sj = " + sj);
    }
}
