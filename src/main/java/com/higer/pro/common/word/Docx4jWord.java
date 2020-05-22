package com.higer.pro.common.word;

import lombok.extern.slf4j.Slf4j;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.docx4j.wml.RPr;
import org.docx4j.wml.Text;

import java.io.File;

/**
 * @Author: wb_xugz
 * @CreateTime: 2020-05-22 14:32
 */
@Slf4j
public class Docx4jWord {

    public static void main(String[] args) {
        createDocx();
        System.out.println("END");
    }

    private static void createDocx() {
        // Create the package
        WordprocessingMLPackage wordMLPackage;
        try {
            wordMLPackage = WordprocessingMLPackage.createPackage();
            wordMLPackage.getMainDocumentPart().addParagraphOfText("3ew中");

            ObjectFactory factory= Context.getWmlObjectFactory();
            P paragraph = factory.createP();
            Text text = factory.createText();
            text.setValue("32323232");

            wordMLPackage.getMainDocumentPart().addObject(text);
            // 另存为新的文件
            wordMLPackage.save(new File("docx4j-word.docx"));
        } catch (InvalidFormatException e) {
            log.error("createDocx error:InvalidFormatException", e);
        } catch (Docx4JException e) {
            log.error("createDocx error: Docx4JException", e);
        }
    }
}
