package com.huan.springboottest.test;


import com.google.common.collect.Lists;
import com.huan.springboottest.common.util.UUIDUtil;
import com.huan.springboottest.react.test.pojo.User;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Test;
import sun.plugin.util.UIUtil;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map;

/**
 * of生成of的静态方法 生成该对象
 * slf4j 生成一个log的静态日志变量
 * @Author: wb_xugz
 * @CreateTime: 2019-11-25 14:10
 */
//@Data(staticConstructor = "of")
//@Slf4j
public class MyTest3 {

    public static void main(String[] args) {
        System.out.println(UUIDUtil.getUUID());
    }

    @Test
    public void test1() throws Exception {
        Integer i = null;
        // NPE
        System.out.println(1 == i);
    }

    @Test
    public void test2() {
        // 匿名内部类继承了HashMap,所以具有HashMap父类当中的所有方法，{}内部为类中的代码块
        Map<String, String> map = new HashMap<String, String>() {
            private static final long serialVersionUID = 8584306419732943192L;

            {
                put("name", "001");
                put("age", "23");
            }
        };
        map.put("sex", "man");
        System.out.println(map);
    }

    @Test
    public void test3(){
        List<String> list = Lists.newArrayList("1");
        list.stream().skip(0).forEach(System.out::println);
    }

    @Test
    public void test4(){
        char s = 0;
        System.out.println(s == 0);
        new String();
    }
}

