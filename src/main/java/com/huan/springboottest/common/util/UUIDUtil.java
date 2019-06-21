package com.huan.springboottest.common.util;

import java.util.UUID;

/**
 * @Description: ${Description}
 * @Author: Huan
 * @CreateTime: 2019-02-21 14:27
 */
public class UUIDUtil {

    /**
     *
     * @return 去除中间的横线
     */
    public static String getTaskName(){
        return getUUID().replaceAll("-","");
    }

    public static String getUUID(){
        return UUID.randomUUID().toString();
    }

}
