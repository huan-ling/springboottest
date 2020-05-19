package com.higer.pro.common.word;

import com.google.common.collect.Lists;
import com.huan.springboottest.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTString;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: wb_xugz
 * @CreateTime: 2020-05-16 14:40
 */
@Slf4j
public class WordUtil {


    private static final String TEMPLATE_PATH = "template.docx";

    private static final String COMPANY_NAME = "金龙联合汽车工业（苏州）有限公司";

    private static final String LOGO_PATH = "logo.jpg";

    public static void main(String[] args) {
        String title = "文件名称";
        String toPath = "template01.docx";
        Map<String, String> map = new HashMap<>();
        map.put("title", "文件名称");
        map.put("target", "文件的目的 文件木第");
        map.put("range", "文件的范围");
        try {
            generateDoc(title, toPath, map);
        } catch (Exception e) {
            log.error("生成word失败", e);
            throw BaseException.of("生成word失败");
        }
    }

    private static String[] getArray() {
        return new String[]{"1", "2", "文件的目的 文件木第的目的 文件木第的目的 文件木第的目的 文件木第的目的 文件木第的目的 文件木第", "4"};
    }

    public static void generateDoc(String title, String toPath, Map<String, String> map) throws Exception {
        XWPFDocument doc = null;
        FileOutputStream fos = null;
        String tempPath = copy(toPath);
        try {
            doc = getDoc(tempPath);
            List<Integer> removeParagraph = Lists.newArrayList();
            setInfo(doc, map, removeParagraph);
            List<String[]> contentList = Lists.newArrayList(getArray(), getArray(), getArray());
            handleTable(doc, contentList, 0);
            handleTable(doc, null, 1);

            List<String[]> detailList = Lists.newArrayList(
                    new String[]{"1", "2", null, "", "", ""},
                    new String[]{"1", "2", null, "", "", ""},
                    new String[]{"1", "2", null, "", "", ""},
                    new String[]{"1", "2", null, "", "", ""},
                    new String[]{"1", "2", null, "", "", ""},
                    new String[]{"1", "2", null, "", "", ""},
                    new String[]{"1", "2", null, "", "", ""},
                    new String[]{"1", "2", null, "", "", ""},
                    new String[]{"1", "2", null, "", "", ""},
                    new String[]{"1", "2", null, "", "", ""},
                    new String[]{"1", "2", null, "", "", ""},
                    new String[]{"1", "2", null, "", "", ""},
                    new String[]{"1", "2", null, "", "", ""},
                    new String[]{"1", "2", null, "", "", ""},
                    new String[]{"1", "2", null, "", "", ""},
                    new String[]{"1", "2", null, "", "", ""},
                    new String[]{"1", "2", null, "", "", ""},
                    new String[]{"1", "2", null, "", "", ""},
                    new String[]{"1", "2", null, "", "", ""},
                    new String[]{"1", "2", null, "", "", ""},
                    new String[]{"1", "2", "3", "", "", ""}
            );
            handleTable(doc, detailList, 2);

            // 页眉
            createHeader(doc, "C00-000-00-01");

            fos = new FileOutputStream(toPath);
            doc.write(fos);
        } catch (Exception e) {
            throw e;
        } finally {
            if (doc != null) {
                doc.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }

    private static void createHeader(XWPFDocument doc, String proNo) throws Exception {
        List<XWPFHeader> headerList = doc.getHeaderList();
        List<XWPFRun> runs = headerList.get(0).getParagraphs().get(0).getRuns();
        XWPFRun run = runs.get(0);
        run.setText("", 0);
        FileInputStream fis = new FileInputStream(LOGO_PATH);
        run.addPicture(fis, XWPFDocument.PICTURE_TYPE_JPEG, LOGO_PATH, Units.toEMU(50), Units.toEMU(18));
        fis.close();
        runs.get(2).setText(proNo, 0);
        runs.get(4).setText(COMPANY_NAME, 0);
        headerList.get(0).setXWPFDocument(doc);
    }

    private static void handleTable(XWPFDocument doc, List<String[]> contentList, int tableIndex) {
        List<XWPFTable> tables = doc.getTables();
        XWPFTable table = tables.get(tableIndex);
        CTTblPr tblPr = table.getCTTbl().getTblPr();

        CTString styleStr = tblPr.addNewTblStyle();

        styleStr.setVal("StyledTable");
        //table.getCTTbl().getTblPr().getTblW().setType(STTblWidth.DXA);
        if (contentList == null || contentList.isEmpty()) {
            XWPFTableRow row = table.getRow(0);
            CTTblBorders tblBorders = table.getCTTbl().getTblPr().addNewTblBorders();
            tblBorders.addNewTop().setVal(STBorder.Enum.forInt(STBorder.INT_NONE));
            System.out.println(tblBorders.getTop());
            row.getCell(0).setText("无");
            return;
        }

        contentList.forEach(c -> {
            XWPFTableRow row = table.createRow();
            List<XWPFTableCell> cellList = row.getTableCells();
            boolean bold = isBold(c);
            for (int i = 0; i < cellList.size(); i++) {
                XWPFParagraph paragraph = cellList.get(i).getParagraphs().get(0);
                cellList.get(i).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                XWPFRun run = paragraph.createRun();
                run.setBold(bold);
                if (c[i] == null) {
                    setColMerge(cellList.get(i), STMerge.CONTINUE);
                } else if (i + 1 < c.length - 1 && c[i + 1] == null) {// 合并开始的条件
                    setColMerge(cellList.get(i), STMerge.RESTART);
                }
                if (c[i] != null) {
                    run.setText(c[i], 0);
//                    run.addBreak(BreakClear.LEFT);
//
////                    //run.setText("ds",1);
////                    paragraph.createRun().setText("一行");
//                    run.setText("ds");
                }
            }
        });
    }

    /**
     * 如果有合并行 则加粗
     * @param data
     * @return
     */
    private static boolean isBold(String[] data) {
        return Arrays.stream(data).anyMatch(Objects::isNull);
    }

    /**
     * 合并
     * @param tableCell
     * @param mergeValue
     */
    private static void setColMerge(XWPFTableCell tableCell, STMerge.Enum mergeValue) {
        CTTc ctTc = tableCell.getCTTc();
        CTTcPr cpr = ctTc.isSetTcPr() ? ctTc.getTcPr() : ctTc.addNewTcPr();
        CTHMerge merge = cpr.isSetHMerge() ? cpr.getHMerge() : cpr.addNewHMerge();
        merge.setVal(mergeValue);
    }


    private static String copy(String toPath) throws Exception {
        String tempPath = toPath + "tmp";
        try (
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(TEMPLATE_PATH));
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tempPath))
        ) {
            int i = -1;
            while ((i = bis.read()) != -1) {
                bos.write(i);
            }
            return tempPath;
        } catch (Exception e) {
            throw e;
        }
    }

    private static void setTitle(XWPFDocument doc, String title) {
        XWPFParagraph paragraph = doc.getParagraphs().get(0);
        XWPFRun xwpfRun = paragraph.getRuns().get(0);
        // 原位置覆盖
        xwpfRun.setText(title, 0);
    }

    private static void setInfo(XWPFDocument doc, Map<String, String> map, List<Integer> removeParagraph) {
        List<XWPFParagraph> paragraphs = doc.getParagraphs();

        AtomicInteger index = new AtomicInteger(-1);
        paragraphs.forEach(p -> {
            index.getAndIncrement();
            System.out.println(p.getText() + "  " + index.get());
            if (needReplace(p.getText())) {
                String value = getReplaceText(p.getText(), map);
                if (StringUtils.isEmpty(value)) {
                    removeParagraph.add(index.get());
                } else {
                    p.getRuns().get(0).setText(value, 0);
                }
            }
        });
    }

    private static String getReplaceText(String text, Map<String, String> map) {
        String key = text.substring(text.indexOf("{") + 1, text.lastIndexOf("}"));
        String value = map.get(key);
        return value == null ? "" : value;
    }

    private static boolean needReplace(String text) {
        return text.contains("$");
    }


    private static XWPFDocument getDoc(String path) throws IOException {
        XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(path));
        return document;
    }
}
