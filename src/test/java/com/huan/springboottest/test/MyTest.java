package com.huan.springboottest.test;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;
import com.google.common.primitives.Ints;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: Huan
 * @CreateTime: 2019-07-15 11:16
 */
public class MyTest {

    /**
     * 连接字符串
     * @param args
     */
    public static void main(String[] args) {
        String join = Joiner.on(",").skipNulls().join("1", 2, "8", null);
        System.out.println(join);//1,2,8
    }

    /**
     * 分隔器字符串
     */
    @Test
    public void test1() {
        List<String> split = Splitter.on(",").omitEmptyStrings().trimResults().splitToList("2,,3 ,4");
        System.out.println(split);// [2, 3, 4]
    }

    /**
     * CharMatcher的使用
     */
    @Test
    public void test2() {
        // 去除字符串中间的空格
        String s = CharMatcher.whitespace().removeFrom("32 45");
        System.out.println("s = " + s);//s = 3245

        // 保留字符串当中的数字
        String s2 = CharMatcher.inRange('0', '9').retainFrom("s7fuerouw09");
        System.out.println("s2 = " + s2);//s2 = 79

        String s3 = CharMatcher.inRange('0', '9').replaceFrom("se45ert3", "D");
        System.out.println("s3 = " + s3);//s3 = seDDertD
    }

    /**
     * 字符串操作
     */
    @Test
    public void test3() {
        // 不足长度用0在开始补足
        String s1 = Strings.padStart("1", 3, '0');
        System.out.println("s1 = " + s1);// 001

        //
        String s2 = Strings.commonPrefix("1323", "12434");
        System.out.println("s2 = " + s2);// 1

        String s3 = Strings.commonSuffix("21324", "32424");
        System.out.println("s3 = " + s3);// 24

        String s4 = Strings.repeat("love", 3);
        System.out.println("s4 = " + s4);// lovelovelove
    }

    /**
     * 基本类型数组
     */
    @Test
    public void test4() {
        // 拼接素组
        int[] ints = Ints.concat(new int[]{3, 5, 7}, new int[]{4, 6});
        int max = Ints.max(ints);
        System.out.println("max = " + max);//max = 7

        Ints.reverse(ints);
        System.out.println("array = " + Arrays.toString(ints));//array = [6, 4, 7, 5, 3]

        boolean contains = Ints.contains(ints, 1);
        System.out.println("contains = " + contains);//contains = false

        List<Integer> list = Ints.asList(ints);
    }

    /**
     * 保护性拷贝list,map
     */
    @Test
    public void test5() {
        List<String> list = Lists.newArrayList("1", "2");
        ImmutableList<String> list1 = ImmutableList.copyOf(list);
        list.add("3");
        //list.size() = 3;list1.size() = 2
        System.out.println("list.size() = " + list.size() + ";list1.size() = " + list1.size());
    }

    /**
     * 一对多的Multimap  Map<String,List<String,String>)
     */
    @Test
    public void test6() {
        Multimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("1", "name1");
        multimap.put("1", "age1");
        multimap.put("2", "name2");
        //multimap = {1=[name1, age1], 2=[name2]}
        System.out.println("multimap = " + multimap);
    }

    /**
     * 双向唯一map
     */
    @Test
    public void test7() {
        BiMap<String, String> biMap = HashBiMap.create();
        biMap.put("1", "1");
        // 强制覆盖
        biMap.forcePut("2", "1");
        // biMap = {2=1}
        System.out.println("biMap = " + biMap);
    }

    /**
     * RowKey-ColKey 共同确定Value 类似两个字段做联合主键
     */
    @Test
    public void test8() {
        Table<Integer, Integer, String> table = HashBasedTable.create();
        table.put(1, 1, "1-1");
        String s = table.get(1, 1);
        // s = 1-1
        System.out.println("s = " + s);
    }
}
