package com.huan.springboottest;

import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STOnOff;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Iterator;

/**
 * html转word文档
 * @Author: wb_xugz
 * @CreateTime: 2020-05-08 10:41
 */
public class WordTest2 {
    public static void main(String[] args) throws Exception {
        String tableContent = "<table cellspacing=\"0\" cellpadding=\"0\" width=\"0\"><tbody><tr><td align=\"CENTER\"" +
                " style=\"border: 1px solid gray\" width=\"30\">班级</td><td align=\"CENTER\" " +
                "style=\"border: 1px solid gray\" width=\"95\">班级人数</td><td align=\"CENTER\"" +
                " style=\"border: 1px solid gray\" width=\"95\">近视人数</td><td align=\"CENTER\" " +
                "style=\"border: 1px solid gray\" width=\"312\">近视人数占全班人数的百分数(除不尽的百分号前保留一位小数)" +
                "</td></tr><tr><td align=\"CENTER\" style=\"border: 1px solid gray\">六一班</td><td align=\"CENTER\" " +
                "style=\"border: 1px solid gray\">.</td><td align=\"CENTER\" style=\"border: 1px solid gray\">8</td>" +
                "<td align=\"CENTER\" style=\"border: 1px solid gray\">.</td></tr><tr><td align=\"CENTER\" " +
                "style=\"border: 1px solid gray\">六二班</td><td align=\"CENTER\" " +
                "style=\"border: 1px solid gray\">32</td><td align=\"CENTER\" style=\"border: 1px solid gray\">." +
                "</td><td align=\"CENTER\" style=\"border: 1px solid gray\">.</td></tr><tr><td align=\"CENTER\" " +
                "style=\"border: 1px solid gray\">六三班</td><td align=\"CENTER\" style=\"border: 1px solid gray\">" +
                ".</td><td align=\"CENTER\" style=\"border: 1px solid gray\">.</td><td align=\"CENTER\" " +
                "style=\"border: 1px solid gray\">22.2%</td></tr><tr><td align=\"CENTER\" style=\"border: 1px " +
                "solid gray\">合计</td><td align=\"CENTER\" style=\"border: 1px solid gray\">117</td><td align=\"" +
                "CENTER\" style=\"border: 1px solid gray\">26</td><td align=\"CENTER\" style=\"border: 1px solid " +
                "gray\">22.2%</td></tr></tbody></table>";
        Element node = supplementTable(tableContent);

        XWPFDocument set = setTable(node);
        OutputStream stream = new FileOutputStream("test-test.docx");
        set.write(stream);
        set.close();
    }

    /**
     * 向word中写表格
     *
     * @author Christmas_G
     * @param element
     * @return
     * @throws Exception
     */
    private static XWPFDocument setTable(Element element) throws Exception {
        Document tableDoc = element.ownerDocument();

        Elements trList = tableDoc.getElementsByTag("tr");
        Elements tdList = trList.first().getElementsByTag("td");

        XWPFDocument document = new XWPFDocument();
        XWPFTable table = document.createTable(trList.size(), tdList.size());

        boolean[][] colspanFlag = new boolean[trList.size()][tdList.size()];
        boolean[][] rowspanFlag = new boolean[trList.size()][tdList.size()];
        for (int row = 0; row < trList.size(); row++) {
            Element tr = trList.get(row);
            Elements tds = tr.getElementsByTag("td");

            for (int col = 0; col < tds.size(); col++) {
                Element td = tds.get(col);

                String colspan = td.attr("colspan");
                String rowspan = td.attr("rowspan");

                String align = td.attr("align");
                String widthStyle = td.attr("width");
                String style = td.attr("style");

                // 记录需要合并的列
                if (!StringUtils.isEmpty(colspan)) {
                    int colCount = Integer.parseInt(colspan);
                    for (int i = 0; i < colCount - 1; i++) {
                        colspanFlag[row][col + i + 1] = true;
                    }
                }
                // 记录需要合并的行
                if (!StringUtils.isEmpty(rowspan)) {
                    int rowCount = Integer.parseInt(rowspan);
                    for (int i = 0; i < rowCount - 1; i++) {
                        rowspanFlag[row + i + 1][col] = true;
                    }
                }
                // 处理合并
                XWPFTableCell cell = table.getRow(row).getCell(col);
                if (colspanFlag[row][col]) {
                    setColMerge(cell, STMerge.CONTINUE);
                    continue;
                } else {
                    setColMerge(cell, STMerge.RESTART);
                }
                if (rowspanFlag[row][col]) {
                    setRowMerge(cell, STMerge.CONTINUE);
                    continue;
                } else {
                    setRowMerge(cell, STMerge.RESTART);
                }
                // 设置列宽
                if (!Strings.isNullOrEmpty(widthStyle) && !"0".equals(widthStyle)) {
                    int width = Integer.parseInt(widthStyle);

                    setWidth(cell, width);
                }

                XWPFParagraph paragraph = null;
                int size = cell.getParagraphs().size();
                if (size == 0) {
                    paragraph = cell.addParagraph();
                } else {
                    paragraph = cell.getParagraphs().get(size - 1);
                }
                // 设置水平样式
                if ("CENTER".equalsIgnoreCase(align)) {
                    paragraph.setAlignment(ParagraphAlignment.CENTER);
                } else if ("LEFT".equalsIgnoreCase(align)) {
                    paragraph.setAlignment(ParagraphAlignment.LEFT);
                }
                // 设置垂直居中
                cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);

                // 设置没有边框
                if (!style.contains("border:")) {
                    setNotBorder(cell);
                }

                XWPFRun run = paragraph.createRun();
                run.setText(td.text());
            }
        }
        return document;
    }

    /**
     * 设置行合并属性
     *
     * @author Christmas_G
     * @date 2019-05-31 14:08:02
     * @param tableCell
     * @param mergeVlaue
     */
    private static void setRowMerge(XWPFTableCell tableCell, STMerge.Enum mergeVlaue) {
        CTTc ctTc = tableCell.getCTTc();
        CTTcPr cpr = ctTc.isSetTcPr() ? ctTc.getTcPr() : ctTc.addNewTcPr();
        CTVMerge merge = cpr.isSetVMerge() ? cpr.getVMerge() : cpr.addNewVMerge();
        merge.setVal(mergeVlaue);
    }

    /**
     * 设置列合并属性
     *
     * @author Christmas_G
     * @date 2019-05-31 14:07:50
     * @param tableCell
     * @param mergeVlaue
     */
    private static void setColMerge(XWPFTableCell tableCell, STMerge.Enum mergeVlaue) {
        CTTc ctTc = tableCell.getCTTc();
        CTTcPr cpr = ctTc.isSetTcPr() ? ctTc.getTcPr() : ctTc.addNewTcPr();
        CTHMerge merge = cpr.isSetHMerge() ? cpr.getHMerge() : cpr.addNewHMerge();
        merge.setVal(mergeVlaue);
    }

    /**
     * 补全表格
     *
     * @author Christmas_G
     * @date 2019-05-31 13:32:52
     * @param tableHtml
     * @return
     */
    private static Element supplementTable(String tableHtml) {
        Document tableDoc = Jsoup.parse(tableHtml);
        Elements trels = tableDoc.getElementsByTag("tr");
        // 补全合并的列
        supplementMergedColumns(trels);
        // 补全合并的行
        supplementMergedRows(trels);
        return tableDoc.getElementsByTag("table").first();
    }

    /**
     * 补全合并的列
     *
     * @author Christmas_G
     * @date 2019-05-31 11:57:36
     * @param trels
     */
    private static void supplementMergedColumns(Elements trels) {
        // 所有tr
        Iterator<Element> trelIter = trels.iterator();
        while (trelIter.hasNext()) {
            Element trel = trelIter.next();
            // 获取所有td
            Elements tdels = trel.getElementsByTag("td");
            Iterator<Element> tdelIter = tdels.iterator();
            while (tdelIter.hasNext()) {
                Element tdel = tdelIter.next();
                // 删除样式
                tdel.removeAttr("class");
                // 取到合并的列数量
                String colspanIndex = tdel.attr("colspan");
                if (StringUtils.isEmpty(colspanIndex)) {
                    continue;
                }
                Integer colspanVal = Integer.parseInt(colspanIndex);
                for (int i = 1; i < colspanVal; i++) {
                    trel.appendElement("td");
                }
            }
        }
    }

    /**
     * 补全合并的行（调用此方法前 需要调用 “补全合并的列”方法）
     *
     * @author Christmas_G
     * @date 2019-05-31 11:57:47
     * @param trels
     */
    private static void supplementMergedRows(Elements trels) {
        // 获取最大的列
        int tdSize = 0;
        Iterator<Element> iterator = trels.iterator();
        while (iterator.hasNext()) {
            Element element = iterator.next();
            int size = element.getElementsByTag("td").size();
            if (size > tdSize) {
                tdSize = size;
            }
        }

        for (int i = 0; i < trels.size(); i++) {
            Element currTr = trels.get(i);
            int currTrTds = currTr.getElementsByTag("td").size();
            if (currTrTds == tdSize) {
                continue;
            }

            int count = tdSize - currTrTds;
            for (int j = 0; j < count; j++) {
                currTr.appendElement("td");
            }
        }
    }

    /**
     * 设置列宽
     *
     * @author Christmas_G
     * @date 2019-06-28 11:30:22
     * @param cell
     * @param width
     */
    private static void setWidth(XWPFTableCell cell, int width) {
        CTTblWidth ctTblWidth = cell.getCTTc().addNewTcPr().addNewTcW();
        // 此处乘以20是我以最接近A4上创建表格的宽度手动设置的
        // 目前没有找到将px转换为word里单位的方式
        ctTblWidth.setW(BigInteger.valueOf(width).multiply(BigInteger.valueOf(20)));
        ctTblWidth.setType(STTblWidth.DXA);
    }

    /**
     * 设置表格为没有边框线
     *
     * @author Christmas_G
     * @date 2019-06-28 11:33:48
     * @param cell
     */
    private static void setNotBorder(XWPFTableCell cell) {
        CTTcBorders cTTcBorders = cell.getCTTc().getTcPr().addNewTcBorders();
        CTBorder clBorder = cTTcBorders.addNewLeft();
        clBorder.setVal(STBorder.Enum.forString("none"));
        clBorder.setShadow(STOnOff.ON);
        CTBorder crBorder = cTTcBorders.addNewRight();
        crBorder.setVal(STBorder.Enum.forString("none"));
        crBorder.setShadow(STOnOff.ON);
        CTBorder cbBorder = cTTcBorders.addNewBottom();
        cbBorder.setVal(STBorder.Enum.forString("none"));
        cbBorder.setShadow(STOnOff.ON);
        CTBorder ctBorder = cTTcBorders.addNewTop();
        ctBorder.setVal(STBorder.Enum.forString("none"));
        ctBorder.setShadow(STOnOff.ON);
    }


}
