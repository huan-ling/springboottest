package com.higer.pro.common.word;

import com.huan.springboottest.common.util.JsonUtil;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.collections4.ListUtils;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author: Huan
 * @CreateTime: 2020-05-19 22:05
 */
public class Html2Word {

    private static final String BREAK = "<br>";

    enum WordType {
        TEXT, TABLE, IMG, BREAK;
    }

    @Data
    @Builder
    static class Content {
        private WordType wordType;
        private List<List<TextType>> tables;
        private TextType text;
        private String src;
    }

    @Data
    @Builder
    static class TextType {
        private String text;
        private boolean bold;
    }


    public static void main(String[] args) {
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
        //Stack<Boolean> tableFlag = new Stack<>();
        AtomicBoolean tableFlag = new AtomicBoolean();
        TextType currentTextType = null;
        analysis(element, contentList, tableFlag, currentTextType);
        System.out.println(JsonUtil.obj2Json(contentList));
    }

    private static void analysis(Node node, List<Content> contentList, AtomicBoolean tableFlag, TextType currentTextType) {

        List<Node> nodes = node.childNodes();
        if (nodes.isEmpty()) {
            return;
        }
        for (Node n : nodes) {
            if (n instanceof Element) {
                currentTextType = TextType.builder().build();
                Element eleNode = (Element) n;
                //Document nodeDoc = (Document)n;
                //System.out.println(nodeDoc.tagName() + ":::" + nodeDoc.text());
                String tagName = eleNode.tagName();
                switch (tagName) {
                    case "table":
                        tableFlag.set(true);
                        contentList.add(Content.builder()
                                .wordType(WordType.TABLE)
                                .tables(new ArrayList<>())
                                .build());
                        break;
                    case "tr":
                        Content content = contentList.get(contentList.size() - 1);
                        List<List<TextType>> tables = content.getTables();
                        // 增加一行
                        tables.add(new ArrayList<>());
                        break;
                    case "td":
                        Content contentTable = contentList.get(contentList.size() - 1);
                        List<List<TextType>> tdTableList = contentTable.getTables();
                        // 获得当前行
                        List<TextType> tdTextType = tdTableList.get(tdTableList.size() - 1);
                        // 增加一列
                        tdTextType.add(currentTextType);
                    case "img":
                        contentList.add(Content.builder()
                                .wordType(WordType.IMG)
                                .src(eleNode.attr("src"))
                                .build());
                        break;
                    case "b":
                    case "strong":
                        currentTextType.setBold(true);
                        contentList.add(Content.builder()
                                .wordType(WordType.TEXT)
                                .text(currentTextType)
                                .build());
                        break;
                    case "br":
                        contentList.add(Content.builder()
                                .wordType(WordType.BREAK)
                                .build());
                        break;
//                    case "p":
//                        contentList.add(Content.builder()
//                                .wordType(WordType.TEXT)
//                                .text(TextType.builder()
//                                        .text(BREAK)
//                                        .build())
//                                .build());
//                        break;
                    case "p":
                    case "span":
                        contentList.add(Content.builder()
                                .wordType(WordType.TEXT)
                                .text(currentTextType)
                                .build());
                        break;
                    default:
                }
                analysis(eleNode, contentList, tableFlag, currentTextType);
                tableFlag.set(false);
            } else {
                TextNode textNode = (TextNode) n;
                System.out.println(textNode.nodeName() + ">>>" + textNode.text());
                currentTextType.setText(textNode.text());
            }
        }
    }

    public static XWPFTableCell getHtmlCell() throws Exception {
        XWPFDocument wordDoc = new XWPFDocument(POIXMLDocument.openPackage("template.docx"));
        XWPFTable table = wordDoc.getTables().get(0);
        XWPFTableCell cell = table.getRow(0).getCell(0);
        XWPFParagraph paragraph = cell.getParagraphs().get(0);
        XWPFRun run = paragraph.createRun();
        //cell.insertTable();
        return cell;
    }
}
