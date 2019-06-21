package com.huan.springboottest.common.util;

/**
 * @Description: ${Description}
 * @Author: Huan
 * @CreateTime: 2019-02-18 16:12
 */
public class TestUtil {
    public static void main(String[] args) {
        //http://kscx.hbee.edu.cn:9012/zk/zkcj201704ap
        System.err.println(HttpUtil.doGet("http://kscx.hbee.edu.cn:9012/zk/zkcj201704ap",null));
    }
}
