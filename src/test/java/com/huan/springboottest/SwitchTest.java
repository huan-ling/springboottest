package com.huan.springboottest;

/**
 * @Author: wb_xugz
 * @CreateTime: 2020-07-15 14:45
 */
public class SwitchTest {

    public static void main(String[] args) {
        String str = null;
        switch (str) {
            case "1":
                System.out.println(1);
                break;
            default:
                System.out.println("default");
                break;
        }
    }
}
