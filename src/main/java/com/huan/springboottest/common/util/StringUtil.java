package com.huan.springboottest.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: ${Description}
 * @Author: Huan
 * @CreateTime: 2019-02-18 12:39
 */
public final class StringUtil {

    private StringUtil(){}

    public static boolean isNull(String str){
        return str == null || str.isEmpty();
    }

    public  static boolean isNotNull(String str){
        return !isNull(str);
    }

    /**
     * 将字符串中的数字转换成Integer型
     *
     * @param str
     * @return str为null、空字符串、无数字转换成Integer时，返回null
     */
    public static Integer getIntegerForString(String str){
        if(isNull(str)){
            return null;
        }
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(str);
        if(matcher.find()){
            return IntegerUtil.getInt(matcher.group());
        }
        return null;
    }

    /**
     * 字符串是否仅仅为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str){
        if(isNull(str)){
            return false;
        }
        return str.matches("\\d+");
    }

    /**
     * 拼接objects的toString结果，返回字符串
     *
     * @param objects
     * @return
     */
    public static String jointObjectStr(Object...objects){
        if(objects == null || objects.length == 0){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<objects.length;i++){
            sb.append(objects[i]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.err.println(isNumber("1w"));
    }
}
