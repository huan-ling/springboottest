package com.huan.springboottest.test;

import com.google.common.collect.Lists;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

/**
 * @Author: wb_xugz
 * @CreateTime: 2020-07-28 09:01
 */
public class MyTest {

    public static void main(String[] args) {
        SoftReference<A> softReference = new SoftReference<>(new A());
        WeakReference<A> weakReference = new WeakReference<>(new A());
        System.out.println(weakReference.get());
        System.gc();
        A a = softReference.get();
        System.out.println(a);
        System.out.println(weakReference.get());
        ThreadLocalRandom.current();
        ThreadLocal<Object> objectThreadLocal = ThreadLocal.withInitial(null);
        objectThreadLocal.get();
        long b = 0xdf;

        List<String> list = Lists.newArrayList();
        list.add("1");
        Optional<String> first = list.parallelStream().findFirst();
        System.out.println(first.orElse("不存在"));
    }

}

class A {

}

