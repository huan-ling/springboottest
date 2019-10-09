package com.huan.springboottest;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

/**
 * 文件合并test
 */
public class SpringboottestApplicationTests {

    @Test
    public void test1() throws IOException {
        FileInputStream fis = new FileInputStream("F:\\BaiduNetdiskDownload/sql_server_2014.iso");
        byte[] data = new byte[1024 * 1024 * 1024];
        int i = -1;
        FileOutputStream fos = null;
        int count = 1;
        File file = new File("D://a-core/split1/");
        file.mkdirs();
        while ((i = fis.read(data)) != -1) {
            fos = new FileOutputStream("D://a-core/split1/part" + count++ + ".part");
            fos.write(data, 0, i);
            fos.flush();
            fos.close();
        }
        fis.close();
        System.out.println("End");
    }

    @Test
    public void test2() throws IOException {
        File file = new File("D://a-core/split1");
        File[] files = file.listFiles();
        List<FileInputStream> list = new ArrayList<>();
        for (File f : files) {
            list.add(new FileInputStream(f.getPath()));
        }
        final Iterator<FileInputStream> it = list.iterator();//ArrayList本身没有枚举方法，通过迭代器来实现
        Enumeration<FileInputStream> en = new Enumeration<FileInputStream>(){//匿名内部类，复写枚举接口下的两个方法{
            public boolean hasMoreElements() {
                return it.hasNext();
            }

            public FileInputStream nextElement() {
                return it.next();
            }

        };
        SequenceInputStream sis = new SequenceInputStream(en);
        FileOutputStream fos = new FileOutputStream("D://a-core/1.zip");
        byte[] buf = new byte[1024 * 1024];
        int count = -1;
        while ((count = sis.read(buf)) != -1) {
            fos.write(buf, 0, count);
        }
        sis.close();
        fos.close();
        System.out.println("END");
    }

}
