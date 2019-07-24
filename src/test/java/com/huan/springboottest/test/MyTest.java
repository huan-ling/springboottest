package com.huan.springboottest.test;

import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Random;

/**
 * @Author: Huan
 * @CreateTime: 2019-07-15 11:16
 */
public class MyTest {

    @Test
    public void test1(){
        String baseSql = "update user set dept_id=";
        String sql = "";

        for(int i=1;i<=53;i++){
            sql += baseSql + new Random().nextInt(10)+" where id="+i+"; ";
        }
        System.out.println(sql);
        switch (baseSql) {
            case "1":
                break;
        }
    }

    private int getPId(int i,String name){
        return i == 0 ? 0 : i < 10 ? 1: new Random().nextInt(10);
    }


    private int getAge(){
        return new Random().nextInt(100);
    }


    @Test
    public void test2() throws NoSuchMethodException {
        Class<MyTest> clz = MyTest.class;
        Method method = clz.getDeclaredMethod("getPId", int.class,String.class);
        Parameter[] parameters = method.getParameters();
        for (Parameter p : parameters) {
            System.out.println(p.getName());
        }
    }

}
