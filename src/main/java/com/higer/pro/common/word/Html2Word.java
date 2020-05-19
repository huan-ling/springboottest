package com.higer.pro.common.word;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.web.util.HtmlUtils;

/**
 * @Author: Huan
 * @CreateTime: 2020-05-19 22:05
 */
public class Html2Word {
    public static void main(String[] args) {
        String html = "<div class='s'> </div>";
        Document doc = Jsoup.parse(html);
        Elements elements = doc.getAllElements();

        String result = HtmlUtils.htmlEscape(html);
        System.out.println(result);

        System.out.println(HtmlUtils.htmlUnescape(result));
    }
}
