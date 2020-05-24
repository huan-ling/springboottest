package com.higer.pro.common.word;

import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import com.huan.springboottest.common.util.JsonUtil;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.springframework.web.util.HtmlUtils;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Author: Huan
 * @CreateTime: 2020-05-19 22:05
 */
public class Html2Word {

    private static final String BREAK = "<br>";
    private static final String LOGO_PATH = "logo.jpg";

    /**
     * block 块级标签
     */
    private static List<String> BLOCK_TAG;

    static {
        BLOCK_TAG = Lists.newArrayList("br", "p", "div");
    }

    enum MergeType {
        /**
         * 水平合并
         */
        H,
        /**
         * 垂直
         */
        V;
    }

    enum WordType {
        TEXT, TABLE, IMG, BLOCK;
    }

    @Data
    @Builder
    static class Point {
        private int x;
        private int y;
        private MergeType mergeType;
    }


    @Data
    static class Content {
        /**
         * 标签名称
         */
        private String tagName;
    }

    @Data
    @Builder
    static class ContentText extends Content {
        private String text;
        private boolean bold;

    }

    @Data
    @Builder
    static class ContentTable extends Content {
        private List<ContentTr> contentTrList;
        private List<Point> mergerList;
    }

    @Data
    @Builder
    static class ContentTr extends Content {
        private List<ContentTd> contentTdList;
    }

    @Builder
    @Data
    static class ContentTd extends Content {
        private XWPFTableCell.XWPFVertAlign vertAlign;
        private List<Content> contentList;
    }

    @Data
    @Builder
    static class ContentImg extends Content {
        private String src;
    }

    @Data
    @Builder
    static class ContentBlock extends Content {

    }

    @Data
    @Builder
    static class TextType {
        private String text;
        private boolean bold;
    }


    public static void main(String[] args) throws Exception {
        String html = "<p>是<b>这时一个加粗文本</b>结果</p><p>&nbsp;</p><p>&nbsp;</p><p>正常</p><p>&nbsp;</p><table" +
                " border=\"1\"cellpadding=\"1\" cellspacing=\"1\" style=\"width:500px;\"><tbody><tr><td colspan=" +
                "\"2\">表格</td></tr><tr><td>s</td><td colspan=\"1\" rowspan=\"2\">z洪文</td></tr><tr><td>&nb" +
                "sp;</td></tr></tbody></table><p>&nbsp;</p><p>&nbsp;</p><p><img src=\"/weaver/weaver.file.FileDownlo" +
                "ad?fileid=7306\"/></p>";

        String html2 = "<p>&nbsp;<strong><span style=\"FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 14px\">评分规则：合理化建议评分 = 合理化建议提交评分*50%+合理化建议采纳评分*50%</span></strong></p><table cellspacing=\"0\" cellpadding=\"0\" width=\"585\"><tbody><tr style=\"HEIGHT: 17px\" class=\"firstRow\"><td style=\"BORDER-BOTTOM: black 1px solid; BORDER-LEFT: black 1px solid; PADDING-BOTTOM: 0px; BACKGROUND-COLOR: transparent; PADDING-LEFT: 7px; PADDING-RIGHT: 7px; BORDER-TOP: black 1px solid; BORDER-RIGHT: black 1px solid; PADDING-TOP: 0px\" height=\"17\" valign=\"top\" width=\"92\"><p style=\"TEXT-ALIGN: center; LINE-HEIGHT: 17px\"><span style=\"FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 14px\">评分项目</span></p></td><td style=\"BORDER-BOTTOM: black 1px solid; BORDER-LEFT: #f0f0f0; PADDING-BOTTOM: 0px; BACKGROUND-COLOR: transparent; PADDING-LEFT: 7px; PADDING-RIGHT: 7px; BORDER-TOP: black 1px solid; BORDER-RIGHT: black 1px solid; PADDING-TOP: 0px\" height=\"17\" valign=\"top\" width=\"126\"><p style=\"TEXT-ALIGN: center; LINE-HEIGHT: 17px\"><span style=\"FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 14px\">60分</span></p></td><td style=\"BORDER-BOTTOM: black 1px solid; BORDER-LEFT: #f0f0f0; PADDING-BOTTOM: 0px; BACKGROUND-COLOR: transparent; PADDING-LEFT: 7px; PADDING-RIGHT: 7px; BORDER-TOP: black 1px solid; BORDER-RIGHT: black 1px solid; PADDING-TOP: 0px\" height=\"17\" valign=\"top\" width=\"126\"><p style=\"TEXT-ALIGN: center; LINE-HEIGHT: 17px\"><span style=\"FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 14px\">80分</span></p></td><td style=\"BORDER-BOTTOM: black 1px solid; BORDER-LEFT: #f0f0f0; PADDING-BOTTOM: 0px; BACKGROUND-COLOR: transparent; PADDING-LEFT: 7px; PADDING-RIGHT: 7px; BORDER-TOP: black 1px solid; BORDER-RIGHT: black 1px solid; PADDING-TOP: 0px\" height=\"17\" valign=\"top\" width=\"126\"><p style=\"TEXT-ALIGN: center; LINE-HEIGHT: 17px\"><span style=\"FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 14px\">100分</span></p></td><td style=\"BORDER-BOTTOM: black 1px solid; BORDER-LEFT: #f0f0f0; PADDING-BOTTOM: 0px; BACKGROUND-COLOR: transparent; PADDING-LEFT: 7px; PADDING-RIGHT: 7px; BORDER-TOP: black 1px solid; BORDER-RIGHT: black 1px solid; PADDING-TOP: 0px\" height=\"17\" valign=\"top\" width=\"117\"><p style=\"TEXT-ALIGN: center; LINE-HEIGHT: 17px\"><span style=\"FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 14px\">120分</span></p></td></tr><tr style=\"HEIGHT: 52px\"><td style=\"BORDER-BOTTOM: black 1px solid; BORDER-LEFT: black 1px solid; PADDING-BOTTOM: 0px; BACKGROUND-COLOR: transparent; PADDING-LEFT: 7px; PADDING-RIGHT: 7px; BORDER-TOP: #f0f0f0; BORDER-RIGHT: black 1px solid; PADDING-TOP: 0px\" height=\"52\" valign=\"top\" width=\"92\"><p style=\"TEXT-ALIGN: center; LINE-HEIGHT: 17px\"><span style=\"FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 14px\">合理化建议</span></p><p style=\"TEXT-ALIGN: center; LINE-HEIGHT: 17px\"><span style=\"FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 14px\">提交评分</span></p><p style=\"TEXT-ALIGN: center; LINE-HEIGHT: 17px\"><span style=\"FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 14px\">（50%）</span></p></td><td style=\"BORDER-BOTTOM: black 1px solid; BORDER-LEFT: #f0f0f0; PADDING-BOTTOM: 0px; BACKGROUND-COLOR: transparent; PADDING-LEFT: 7px; PADDING-RIGHT: 7px; BORDER-TOP: #f0f0f0; BORDER-RIGHT: black 1px solid; PADDING-TOP: 0px\" height=\"52\" valign=\"top\" width=\"126\"><p style=\"TEXT-ALIGN: center; LINE-HEIGHT: 17px\"><span style=\"FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 14px\">建议条数</span></p><p style=\"TEXT-ALIGN: center; LINE-HEIGHT: 17px\"><span style=\"FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 14px\">＜</span></p><p style=\"TEXT-ALIGN: center; LINE-HEIGHT: 17px\"><span style=\"FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 14px\">基本要求</span></p></td><td style=\"BORDER-BOTTOM: black 1px solid; BORDER-LEFT: #f0f0f0; PADDING-BOTTOM: 0px; BACKGROUND-COLOR: transparent; PADDING-LEFT: 7px; PADDING-RIGHT: 7px; BORDER-TOP: #f0f0f0; BORDER-RIGHT: black 1px solid; PADDING-TOP: 0px\" height=\"52\" valign=\"top\" width=\"126\"><p style=\"TEXT-ALIGN: center; LINE-HEIGHT: 17px\"><span style=\"FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 14px\">建议条数</span></p><p style=\"TEXT-ALIGN: center; LINE-HEIGHT: 17px\"><span style=\"FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 14px\">=</span></p><p style=\"TEXT-ALIGN: center; LINE-HEIGHT: 17px\"><span style=\"FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 14px\">基本要求</span></p></td><td style=\"BORDER-BOTTOM: black 1px solid; BORDER-LEFT: #f0f0f0; PADDING-BOTTOM: 0px; BACKGROUND-COLOR: transparent; PADDING-LEFT: 7px; PADDING-RIGHT: 7px; BORDER-TOP: #f0f0f0; BORDER-RIGHT: black 1px solid; PADDING-TOP: 0px\" height=\"52\" valign=\"top\" width=\"126\"><p style=\"TEXT-ALIGN: center; LINE-HEIGHT: 17px\"><span style=\"FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 14px\">建议条数</span></p><p style=\"TEXT-ALIGN: center; LINE-HEIGHT: 17px\"><span style=\"FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 14px\">＞</span></p><p style=\"TEXT-ALIGN: center; LINE-HEIGHT: 17px\"><span style=\"FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 14px\">基本要求</span></p></td><td style=\"BORDER-BOTTOM: black 1px solid; BORDER-LEFT: #f0f0f0; PADDING-BOTTOM: 0px; BACKGROUND-COLOR: transparent; PADDING-LEFT: 7px; PADDING-RIGHT: 7px; BORDER-TOP: #f0f0f0; BORDER-RIGHT: black 1px solid; PADDING-TOP: 0px\" height=\"52\" valign=\"top\" width=\"117\"><p style=\"TEXT-ALIGN: center; LINE-HEIGHT: 17px\"><span style=\"FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 14px\">&nbsp;</span></p><p style=\"TEXT-ALIGN: center; LINE-HEIGHT: 17px\"><span style=\"FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 14px\">/</span></p></td></tr><tr style=\"HEIGHT: 54px\"><td style=\"BORDER-BOTTOM: black 1px solid; BORDER-LEFT: black 1px solid; PADDING-BOTTOM: 0px; BACKGROUND-COLOR: transparent; PADDING-LEFT: 7px; PADDING-RIGHT: 7px; BORDER-TOP: #f0f0f0; BORDER-RIGHT: black 1px solid; PADDING-TOP: 0px\" height=\"54\" valign=\"top\" width=\"92\"><p style=\"TEXT-ALIGN: center; LINE-HEIGHT: 17px\"><span style=\"FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 14px\">合理化建议</span></p><p style=\"TEXT-ALIGN: center; LINE-HEIGHT: 17px\"><span style=\"FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 14px\">采纳评分</span></p><p style=\"TEXT-ALIGN: center; LINE-HEIGHT: 17px\"><span style=\"FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 14px\">（50%）</span></p></td><td style=\"BORDER-BOTTOM: black 1px solid; BORDER-LEFT: #f0f0f0; PADDING-BOTTOM: 0px; BACKGROUND-COLOR: transparent; PADDING-LEFT: 7px; PADDING-RIGHT: 7px; BORDER-TOP: #f0f0f0; BORDER-RIGHT: black 1px solid; PADDING-TOP: 0px\" height=\"54\" valign=\"top\" width=\"126\"><p style=\"TEXT-ALIGN: center; LINE-HEIGHT: 17px\"><span style=\"FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 14px\">采纳条数</span></p><p style=\"TEXT-ALIGN: center; LINE-HEIGHT: 17px\"><span style=\"FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 14px\">＜</span></p><p style=\"TEXT-ALIGN: center; LINE-HEIGHT: 17px\"><span style=\"FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 14px\">基本要求</span></p></td><td style=\"BORDER-BOTTOM: black 1px solid; BORDER-LEFT: #f0f0f0; PADDING-BOTTOM: 0px; BACKGROUND-COLOR: transparent; PADDING-LEFT: 7px; PADDING-RIGHT: 7px; BORDER-TOP: #f0f0f0; BORDER-RIGHT: black 1px solid; PADDING-TOP: 0px\" height=\"54\" valign=\"top\" width=\"126\"><p style=\"TEXT-ALIGN: center; LINE-HEIGHT: 17px\"><span style=\"FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 14px\">&nbsp;</span></p><p style=\"TEXT-ALIGN: center; LINE-HEIGHT: 17px\"><span style=\"FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 14px\">/</span></p></td><td style=\"BORDER-BOTTOM: black 1px solid; BORDER-LEFT: #f0f0f0; PADDING-BOTTOM: 0px; BACKGROUND-COLOR: transparent; PADDING-LEFT: 7px; PADDING-RIGHT: 7px; BORDER-TOP: #f0f0f0; BORDER-RIGHT: black 1px solid; PADDING-TOP: 0px\" height=\"54\" valign=\"top\" width=\"126\"><p style=\"TEXT-ALIGN: center; LINE-HEIGHT: 17px\"><span style=\"FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 14px\">采纳条数</span></p><p style=\"TEXT-ALIGN: center; LINE-HEIGHT: 17px\"><span style=\"FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 14px\">=</span></p><p style=\"TEXT-ALIGN: center; LINE-HEIGHT: 17px\"><span style=\"FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 14px\">基本要求</span></p></td><td style=\"BORDER-BOTTOM: black 1px solid; BORDER-LEFT: #f0f0f0; PADDING-BOTTOM: 0px; BACKGROUND-COLOR: transparent; PADDING-LEFT: 7px; PADDING-RIGHT: 7px; BORDER-TOP: #f0f0f0; BORDER-RIGHT: black 1px solid; PADDING-TOP: 0px\" height=\"54\" valign=\"top\" width=\"117\"><p style=\"TEXT-ALIGN: center; LINE-HEIGHT: 17px\"><span style=\"FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 14px\">采纳条数</span></p><p style=\"TEXT-ALIGN: center; LINE-HEIGHT: 17px\"><span style=\"FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 14px\">＞</span></p><p style=\"TEXT-ALIGN: center; LINE-HEIGHT: 17px\"><span style=\"FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 14px\">基本要求</span></p></td></tr></tbody></table><p>&nbsp;</p>\t";
        Document htmlDoc = Jsoup.parse(html);

        Elements body = htmlDoc.getElementsByTag("body");
        Element element = body.get(0);
        List<Content> contentList = new ArrayList<>();
        Stack<Content> tagStack = new Stack<>();
        Stack<ContentTable> tableStack = new Stack<>();
        // rowspan 垂直合并 元素为当前所在的列
        Stack<Integer> tdVerStack = new Stack<>();
        analysis(element, contentList, tableStack, tagStack, tdVerStack);
        System.out.println(JsonUtil.obj2Json(contentList));

        XWPFDocument wordDoc = new XWPFDocument(POIXMLDocument.openPackage("test.docx"));
        XWPFTableCell htmlCell = getHtmlCell(wordDoc);
        transform(htmlCell, contentList);
        wordDoc.write(new FileOutputStream("test01.docx"));
    }

    private static void transform(XWPFTableCell cell, List<Content> contentList) throws Exception {
        //cell.insertTable(0,);
        for (Content c : contentList) {
            List<XWPFParagraph> paragraphList = cell.getParagraphs();
            XWPFParagraph xwpfParagraph = paragraphList.get(paragraphList.size() - 1);
            if (c instanceof ContentText) {
                handleText(xwpfParagraph, (ContentText) c);
            } else if (c instanceof ContentBlock) {
                cell.addParagraph();
                //handleBlock(xwpfParagraph);
            } else if (c instanceof ContentImg) {
                handleImg(xwpfParagraph);
            } else if (c instanceof ContentTable) {
                handleTable(cell, (ContentTable) c);
            }
        }
    }

    private static void handleTable(XWPFTableCell cell, ContentTable table) throws Exception {
        XWPFParagraph xwpfParagraph = cell.getParagraphs().get(cell.getParagraphs().size() - 1);
        XmlCursor xmlCursor = xwpfParagraph.getCTP().newCursor();
        String uri = CTTbl.type.getName().getNamespaceURI();
        String localPart = "tbl";
        xmlCursor.beginElement(localPart, uri);
        xmlCursor.toParent();
        XmlObject object = xmlCursor.getObject();
        //XWPFTable insertTable = cell.insertNewTbl(xmlCursor);
        XWPFTable insertTable = new XWPFTable((CTTbl) object, cell, 2, 2);

        List<ContentTr> contentTrList = table.getContentTrList();
        for (int y = 0; y < contentTrList.size(); y++) {
            XWPFTableRow row = insertTable.getRow(y);
            if (row == null) {
                row = insertTable.createRow();
            }
            ContentTr contentTr = contentTrList.get(y);
            List<ContentTd> contentTdList = contentTr.getContentTdList();
            for (int x = 0; x < contentTdList.size(); x++) {
                XWPFTableCell insertCell = row.getCell(x);
                if (insertCell == null) {
                    insertCell = row.createCell();
                }
                test(x, y, table.getMergerList(), cell, row);
                ContentTd contentTd = contentTdList.get(x);
                transform(insertCell, contentTd.getContentList());
            }
        }
    }

    private static void test(int x, int y, List<Point> mergeList, XWPFTableCell cell, XWPFTableRow row) {
        AtomicInteger offset = new AtomicInteger(1);
        List<Point> pointList = mergeList.stream()
                .filter(p ->
                        // 当前行有合并,并且下个行都是合并行
                        p.getY() == y && p.getX() == offset.getAndIncrement() + x
                ).collect(Collectors.toList());

        pointList.forEach(p -> {
            if (p.getMergeType().equals(MergeType.H)) {
                XWPFTableCell insertCell = row.getCell(p.getX());
                if (insertCell == null) {
                    insertCell = row.createCell();
                }
                setColMerge(cell, STMerge.RESTART);
                setColMerge(insertCell, STMerge.CONTINUE);
            }
        });
        if(CollectionUtils.isNotEmpty(pointList)){
            //setRowMerge(cell, STMerge.RESTART);
            setColMerge(cell, STMerge.RESTART);
        }
    }


    private static void setRowMerge(XWPFTableCell tableCell, STMerge.Enum mergeValue) {
        CTTc ctTc = tableCell.getCTTc();
        CTTcPr cpr = ctTc.isSetTcPr() ? ctTc.getTcPr() : ctTc.addNewTcPr();
        CTVMerge merge = cpr.isSetVMerge() ? cpr.getVMerge() : cpr.addNewVMerge();
        merge.setVal(mergeValue);
    }

    private static void setColMerge(XWPFTableCell tableCell, STMerge.Enum mergeValue) {
        CTTc ctTc = tableCell.getCTTc();
        CTTcPr cpr = ctTc.isSetTcPr() ? ctTc.getTcPr() : ctTc.addNewTcPr();
        CTHMerge merge = cpr.isSetHMerge() ? cpr.getHMerge() : cpr.addNewHMerge();
        merge.setVal(mergeValue);
    }


    private static void handleImg(XWPFParagraph xwpfParagraph) throws Exception {
        FileInputStream fis = new FileInputStream(LOGO_PATH);
        xwpfParagraph.createRun().addPicture(fis, XWPFDocument.PICTURE_TYPE_JPEG, LOGO_PATH, Units.toEMU(50), Units.toEMU(18));
    }

    private static void handleBlock(XWPFParagraph xwpfParagraph) {
        xwpfParagraph.createRun().addBreak();
    }


    private static void handleText(XWPFParagraph xwpfParagraph, ContentText contentText) {
        XWPFRun xwpfRun = xwpfParagraph.createRun();
        xwpfRun.setText(contentText.getText());
        xwpfRun.setBold(contentText.isBold());
    }

    private static void analysis(Node node, List<Content> contentList, Stack<ContentTable> tableStack, Stack<Content> tagStack, Stack<Integer> tdVerStack) {

        List<Node> nodes = node.childNodes();
        if (nodes.isEmpty()) {
            return;
        }
        for (Node n : nodes) {
            if (n instanceof Element) {
                Element eleNode = (Element) n;
                //Document nodeDoc = (Document)n;
                //System.out.println(nodeDoc.tagName() + ":::" + nodeDoc.text());
                String tagName = eleNode.tagName();

                Content content = null;


                switch (tagName) {
                    case "table":
                        ContentTable table = ContentTable.builder().contentTrList(new ArrayList<>(20)).build();
                        content = table;
                        tableStack.push(table);
                        contentList.add(content);
                        break;
                    case "tr":
                        ContentTable trTable = tableStack.peek();
                        // 增加一行
                        trTable.getContentTrList().add(ContentTr.builder().contentTdList(new ArrayList<>()).build());
                        break;
                    case "td":
                        List<ContentTr> contentTrList = tableStack.peek().getContentTrList();
                        // 获取当前行
                        ContentTr currentTr = contentTrList.get(contentTrList.size() - 1);
                        // 增加一列
                        List<Content> tdContentList = new ArrayList<>();
                        ContentTd contentTd = ContentTd.builder().contentList(tdContentList).build();
                        // 需要进行垂直合并
                        if (!tdVerStack.isEmpty() && (currentTr.getContentTdList().size() + 1) == tdVerStack.pop()) {

                        } else {
                            currentTr.getContentTdList().add(contentTd);

                            // 当前列的内容
                            content = ContentText.builder().build();
                            tdContentList.add(content);

                            String colspan = eleNode.attr("colspan");
                            if (StringUtils.isNotEmpty(colspan)) {
                                for (int i = 1; i < Integer.parseInt(colspan); i++) {
                                    //tdContentList.add(null);
                                    List<Point> mergerList = tableStack.peek().getMergerList();
                                    if (mergerList == null) {
                                        mergerList = Lists.newArrayList();
                                        tableStack.peek().setMergerList(mergerList);
                                    }
                                    mergerList.add(new Point(currentTr.getContentTdList().size() - 1 + i, contentTrList.size() - 1, MergeType.H));
                                }
                            }
                            String rowspan = eleNode.attr("rowspan");
                            if (StringUtils.isNotEmpty(rowspan)) {
                                for (int i = 1; i < Integer.parseInt(rowspan); i++) {
                                    // 当前所在的列数
                                    // tdVerStack.push(currentTr.getContentTdList().size());
                                    List<Point> mergerList = tableStack.peek().getMergerList();
                                    if (mergerList == null) {
                                        mergerList = Lists.newArrayList();
                                        tableStack.peek().setMergerList(mergerList);
                                    }
                                    mergerList.add(new Point(currentTr.getContentTdList().size() - 1, contentTrList.size() - 1 + i, MergeType.V));
                                }
                            }
                        }
                        break;
                    case "img":
                        content = ContentImg.builder().src(eleNode.attr("src")).build();
                        break;
                    case "b":
                    case "strong":
                        content = ContentText.builder()
                                .bold(true)
                                .build();
                        break;
                    case "p":
                    case "div":
                    case "span":
                        content = ContentText.builder().build();
                        break;
                    default:
                        // contentList.remove(contentList.size() - 1);
                }
                tagStack.push(content);
                if (content != null && tableStack.isEmpty()) {
                    content.setTagName(tagName);
                    contentList.add(content);
                }
                analysis(eleNode, contentList, tableStack, tagStack, tdVerStack);

                // 标签结束后事件
                if ("table".equals(tagName)) {
                    tableStack.pop();
                }
                tagStack.pop();
                if (BLOCK_TAG.contains(tagName) && tableStack.isEmpty()) {
                    contentList.add(ContentBlock.builder().build());
                }

            } else {
                TextNode textNode = (TextNode) n;
                System.out.println(textNode.nodeName() + ">>>" + textNode.text());
                Content peek = tagStack.peek();
                if (peek == null) {// 这里基本上不会进入
                    contentList.add(ContentText.builder().text(textNode.text()).build());
                } else {
                    ContentText contentText = (ContentText) peek;
                    if (StringUtils.isEmpty(contentText.getText())) {
                        contentText.setText(textNode.text());
                    } else {
                        contentList.add(ContentText.builder().text(textNode.text()).build());
                    }
                }
            }
        }
    }

    public static XWPFTableCell getHtmlCell(XWPFDocument wordDoc) throws Exception {

        XWPFTable table = wordDoc.getTables().get(0);
        XWPFTableCell cell = table.getRow(0).getCell(0);
        XWPFParagraph paragraph = cell.getParagraphs().get(0);
        XWPFRun run = paragraph.createRun();
        //cell.insertTable();;
//        XmlCursor xmlCursor = paragraph.getCTP().newCursor();
//        XWPFTable table1 = cell.insertNewTbl(xmlCursor);
//        table1.setBottomBorder(XWPFTable.XWPFBorderType.THICK,1,1,"000000");
//        XWPFTableRow row = table1.createRow();
//        row.createCell();
//


        return cell;
    }
}