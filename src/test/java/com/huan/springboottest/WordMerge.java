package com.huan.springboottest;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.poifs.filesystem.DirectoryNode;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.junit.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.springframework.web.util.HtmlUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 单元格的合并
 * @Author: wb_xugz
 * @CreateTime: 2020-05-08 08:46
 */
public class WordMerge {

    @Test
    public void test4(){
        System.out.println(Units.pixelToEMU(10));
        System.out.println(Units.toEMU(0.8));
        System.out.println(Units.pointsToPixel(0.8));
        System.out.println(Units.pointsToPixel(0.7));
    }

    @Test
    public void test3() throws Exception {
        String html = "";
        System.out.println(HtmlUtils.htmlUnescape("<html></html>"));

    }

    @Test
    public void test2() throws Exception {
        byte[] data = "<html><body><div>1</div></body></html>".getBytes("utf-8");
        ByteArrayInputStream bais = new ByteArrayInputStream(data);

        POIFSFileSystem poifsFileSystem = new POIFSFileSystem();
        DirectoryNode root = poifsFileSystem.getRoot();
        FileInputStream fis = new FileInputStream("test.html");
        root.createDocument("test", bais);
        FileOutputStream fos = new FileOutputStream("test.doc");
        poifsFileSystem.writeFilesystem(fos);
        poifsFileSystem.close();
        fos.close();
        bais.close();
        fis.close();
    }

    @Test
    public void test1() throws Exception {
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph paragraph = doc.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("test");
        run.setFontSize(22);
        run.setBold(true);
        run.setFontFamily("宋体");
        paragraph.setAlignment(ParagraphAlignment.CENTER);


        XWPFTable table = doc.createTable(3, 4);
        List<XWPFTableRow> rows = table.getRows();
        rows.forEach(r -> {
            List<XWPFTableCell> tableCells = r.getTableCells();
            int[] widths = {1, 2, 3, 1};
            AtomicInteger index = new AtomicInteger(0);
            tableCells.forEach(c -> {
                setWidth(c, widths[index.getAndIncrement()]);
                c.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            });
        });


//        CTTblPr tblPr = table.getCTTbl().getTblPr();
//        tblPr.getTblW().setType(STTblWidth.DXA);
//        tblPr.getTblW().setW(new BigInteger("8000"));

        XWPFTableCell cell = table.getRow(1).getCell(1);
        setColMerge(cell, STMerge.RESTART);
        XWPFParagraph paragraphArray = cell.getParagraphArray(0);
        paragraphArray.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run1 = paragraphArray.createRun();
        run1.setText("32");
        run1.setFontSize(23);

        XmlCursor xmlCursor = paragraphArray.getCTP().newCursor();
        XWPFTable table1 = cell.insertNewTbl(xmlCursor);
        XWPFTableRow row = table1.createRow();
        XWPFTableCell cell2 = row.createCell();
        XWPFParagraph paragraph1 = cell2.addParagraph();
        XWPFRun run2 = paragraph1.createRun();
        run2.setText("23");

        XWPFPicture picture = run1.addPicture(new FileInputStream("图片1.png"), Document.PICTURE_TYPE_PNG, "图片1", Units.pixelToEMU(50),Units.pixelToEMU(50));
        System.out.println(Units.pixelToEMU(50));

        XWPFTableCell cell1 = table.getRow(1).getCell(2);
        setColMerge(cell1, STMerge.CONTINUE);

        FileOutputStream fos = new FileOutputStream("test.docx");
        doc.write(fos);
        fos.close();
        doc.close();
    }


    private void setRowMerge(XWPFTableCell tableCell, STMerge.Enum mergeVlaue) {
        CTTc ctTc = tableCell.getCTTc();
        CTTcPr cpr = ctTc.isSetTcPr() ? ctTc.getTcPr() : ctTc.addNewTcPr();
        CTVMerge merge = cpr.isSetVMerge() ? cpr.getVMerge() : cpr.addNewVMerge();
        merge.setVal(mergeVlaue);
    }

    private void setColMerge(XWPFTableCell tableCell, STMerge.Enum mergeVlaue) {
        CTTc ctTc = tableCell.getCTTc();
        CTTcPr cpr = ctTc.isSetTcPr() ? ctTc.getTcPr() : ctTc.addNewTcPr();
        CTHMerge merge = cpr.isSetHMerge() ? cpr.getHMerge() : cpr.addNewHMerge();
        merge.setVal(mergeVlaue);
    }

    private static void setWidth(XWPFTableCell cell, int width) {
        CTTblWidth ctTblWidth = cell.getCTTc().addNewTcPr().addNewTcW();
        // 此处乘以20是我以最接近A4上创建表格的宽度手动设置的
        // 目前没有找到将px转换为word里单位的方式
        ctTblWidth.setW(BigInteger.valueOf(width).multiply(BigInteger.valueOf(2000)));
        ctTblWidth.setType(STTblWidth.DXA);
    }

}
