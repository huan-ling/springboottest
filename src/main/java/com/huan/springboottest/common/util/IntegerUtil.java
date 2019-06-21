package com.huan.springboottest.common.util;

/**
 * @Description: ${Description}
 * @Author: Huan
 * @CreateTime: 2019-02-18 14:13
 */
public class IntegerUtil {

    public static int getInt(String str){
        if(str == null || "".equals(str.trim())){
            return 0;
        }
        return Integer.valueOf(str);
    }

}
