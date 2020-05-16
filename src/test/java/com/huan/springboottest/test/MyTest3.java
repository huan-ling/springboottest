package com.huan.springboottest.test;


import com.huan.springboottest.common.util.UUIDUtil;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Test;
import sun.plugin.util.UIUtil;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String docPath = "E:\\2020-dev\\管理文件\\05\\软件功能设计书（管理文件翻新OA）.docx";
        String pdfPath = "E:\\2020-dev\\管理文件\\05\\test.pdf";

        XWPFDocument document;
        InputStream doc = new FileInputStream(docPath);
        document = new XWPFDocument(doc);
        PdfOptions options = PdfOptions.create();
        OutputStream out = new FileOutputStream(pdfPath);
        PdfConverter.getInstance().convert(document, out, options);

        doc.close();
        out.close();
    }
}

