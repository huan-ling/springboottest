package com.huan.springboottest.test;

import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.List;

/**
 * @Author: wb_xugz
 * @CreateTime: 2019-10-09 10:12
 */
public class MyTest2 {

    @Test
    public void test() {
        List<Integer> list = Lists.newArrayList(1, 2, 3);
        List<Integer> list2 = list.subList(0, 2);
        list.add(4);
        System.out.println(list2.size());//java.util.ConcurrentModificationException
        // 可以new ArrayList<>(list2);的方式创建一个新的集合解决
    }
}

