package com.huan.springboottest.common.util;


import com.huan.springboottest.common.exception.BaseException;

/**
 * @Description: ${Description}
 * @Author: Huan
 * @CreateTime: 2019-02-21 16:49
 */
public class Assert {

    public static void checkUId(Integer uid){
        if(uid == null){
            throw new BaseException(101,"无法获取Cookie中的用户id");
        }
    }

    public static void checkNull(Object ... arr){
        for(int i=0;i<arr.length;i++){
            if(arr[i] == null){
                throw new BaseException(100,"传入的参数有空");
            }
        }
    }

}
