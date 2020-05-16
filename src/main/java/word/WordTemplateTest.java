package word;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.util.List;

/**
 * @Author: wb_xugz
 * @CreateTime: 2020-05-16 14:00
 */
public class WordTemplateTest {

    public static void main(String[] args)throws Exception {
        String inputUrl = "template.docx";
        XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(inputUrl));
        //获取整个文本对象
        List<XWPFParagraph> allParagraph = document.getParagraphs();
        System.out.println(allParagraph);
        allParagraph.stream().forEach(t -> {
            System.out.println("pa===="+t.getText());
            List<XWPFRun> runs = t.getRuns();
            runs.stream().forEach(run -> {
                System.out.println(run.text());

            });
        });
    }
}
