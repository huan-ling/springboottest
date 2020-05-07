package com.huan.springboottest;

import com.google.common.collect.Lists;
import com.huan.springboottest.react.test.pojo.User;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Author: wb_xugz
 * @CreateTime: 2020-03-31 08:09
 */
public class ListTest {

    public static void main(String[] args) {
        List<User> list = Lists.newArrayList();
        list.add(User.builder().id(1).uName("1").build());
        list.add(User.builder().id(2).uName("2").build());
        list.add(User.builder().id(3).uName("3").build());

        Map<Integer, List<User>> map = list.stream().collect(Collectors.groupingBy(User::getId));
        System.out.println(map);

        Runtime.getRuntime();
    }

    @Test
    public void test1() {
        String str = "&lt;p>&lt;img&nbsp;src=&quot;/weaver/weaver.file.FileDownload?fileid=5652&quot;&nbsp;/>&lt;/p>&lt;br>";
        System.out.println(str.replaceAll("&lt;", "<"));

        Pattern p = Pattern.compile("(&lt;)(&nbsp;)");
        Matcher m = p.matcher(str);

        System.out.println(m.find());
    }

    @Test
    public void test2() {
        System.out.println(addOne("1---09--ew"));

    }

    public String addOne(String testStr) {
        String[] strs = testStr.split("[^0-9]");//根据不是数字的字符拆分字符串
        System.out.println(Arrays.toString(strs));
        String numStr = strs[strs.length - 1];//取出最后一组数字
        if (numStr != null && numStr.length() > 0) {//如果最后一组没有数字(也就是不以数字结尾)，抛NumberFormatException异常
            int n = numStr.length();//取出字符串的长度
            int num = Integer.parseInt(numStr) + 1;//将该数字加一
            String added = String.valueOf(num);
            n = Math.min(n, added.length());
            //拼接字符串
            return testStr.subSequence(0, testStr.length() - n) + added;
        } else {
            throw new NumberFormatException();
        }
    }
}
