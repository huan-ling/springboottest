package com.huan.springboottest.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Description: ${Description}
 * @Author: Huan
 * @CreateTime: 2019-02-23 20:19
 */
public class RandomUtil {

    public static final Random random = new Random();

    public static int getInt(int end){
        return random.nextInt(end);
    }

    /**
     * 获取指定个数的随机数
     *
     * @param end
     * @param select 个数
     * @return 不足返回所有
     */
    public static List<Integer> getIntArr(int end,int select){
        List<Integer> list = new ArrayList<>();
        if(end <= select){
            for(int i=0;i<end;i++){
                list.add(i);
            }
        }
        while (list.size() < select){
            int num = getInt(end);
            if(!list.contains(num)){
                list.add(num);
            }
        }
        return list;
    }

}
