package word.test;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.ResourceUtils;
import org.springframework.web.util.HtmlUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: wb_xugz
 * @CreateTime: 2020-05-08 13:50
 */
public class MyTest {

    public static void main(String[] args) {
        String content = "<p><img title=\"C:\\Users\\chenmin2.KLSZ\\Desktop\\1.jpg\" alt=\"C:\\Users\\chenmin2.KLSZ\\Desktop\\1.jpg\"\n" +
                "        src=\"图片1.png\"/></p>";
        writeWordFile(content);
    }

    public static String writeWordFile(String content) {
        String path = "D:/wordFile";
        Map<String, Object> param = new HashMap<String, Object>();

        if (!"".equals(path)) {
            File fileDir = new File(path);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            content = HtmlUtils.htmlUnescape(content);
            List<HashMap<String, String>> imgs = getImgStr(content);
            int count = 0;
            for (HashMap<String, String> img : imgs) {
                count++;
                //处理替换以“/>”结尾的img标签
                content = content.replace(img.get("img"), "${imgReplace" + count + "}");
                //处理替换以“>”结尾的img标签
                content = content.replace(img.get("img1"), "${imgReplace" + count + "}");
                Map<String, Object> header = new HashMap<String, Object>();

                try {
                    File filePath = new File(ResourceUtils.getURL("classpath:").getPath());
                    String imagePath = filePath.getAbsolutePath()+File.separator;
                    imagePath += img.get("src").replaceAll("/", "\\\\");
                    //如果没有宽高属性，默认设置为400*300
                    if(img.get("width") == null || img.get("height") == null) {
                        header.put("width", 400);
                        header.put("height", 300);
                    }else {
                        header.put("width", (int) (Double.parseDouble(img.get("width"))));
                        header.put("height", (int) (Double.parseDouble(img.get("height"))));
                    }
                    header.put("type", "jpg");
                    header.put("content", OfficeUtil.inputStream2ByteArray(new FileInputStream(imagePath), true));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                param.put("${imgReplace" + count + "}", header);
            }
            try {
                // 生成doc格式的word文档，需要手动改为docx
                byte by[] = content.getBytes("UTF-8");
                ByteArrayInputStream bais = new ByteArrayInputStream(by);
                POIFSFileSystem poifs = new POIFSFileSystem();
                DirectoryEntry directory = poifs.getRoot();
                DocumentEntry documentEntry = directory.createDocument("WordDocument", bais);
                FileOutputStream ostream = new FileOutputStream("D:\\wordFile\\temp.doc");
                poifs.writeFilesystem(ostream);
                bais.close();
                ostream.close();

                // 临时文件（手动改好的docx文件）
                CustomXWPFDocument doc = OfficeUtil.generateWord(param, "D:\\wordFile\\temp.docx");
                //最终生成的带图片的word文件
                FileOutputStream fopts = new FileOutputStream("D:\\wordFile\\final.docx");
                doc.write(fopts);
                fopts.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return "D:/wordFile/final.docx";
    }

    //获取html中的图片元素信息
    public static List<HashMap<String, String>> getImgStr(String htmlStr) {
        List<HashMap<String, String>> pics = new ArrayList<HashMap<String, String>>();

        Document doc = Jsoup.parse(htmlStr);
        Elements imgs = doc.select("img");
        for (Element img : imgs) {
            HashMap<String, String> map = new HashMap<String, String>();
            if(!"".equals(img.attr("width"))) {
                map.put("width", img.attr("width").substring(0, img.attr("width").length() - 2));
            }
            if(!"".equals(img.attr("height"))) {
                map.put("height", img.attr("height").substring(0, img.attr("height").length() - 2));
            }
            map.put("img", img.toString().substring(0, img.toString().length() - 1) + "/>");
            map.put("img1", img.toString());
            map.put("src", img.attr("src"));
            pics.add(map);
        }
        return pics;
    }

}
