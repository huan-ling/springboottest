package com.higer.pro.common.word;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Aspose 为商业收费版
 * @Author: wb_xugz
 * @CreateTime: 2020-06-02 15:16
 */
public class Word2PdfByAspose {
    public static void main(String[] args) throws Exception {
        Document doc = new Document(new FileInputStream("A-01-00-08-HJ 一个文件手册 (1).docx"));
        doc.save(new FileOutputStream("1.pdf"), SaveFormat.PDF);
    }
}
