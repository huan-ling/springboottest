package com.higer.pro.common.word;

import ch.qos.logback.core.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.fonts.truetype.TTFFile;
import org.docx4j.Docx4J;
import org.docx4j.Docx4jProperties;
import org.docx4j.convert.out.FOSettings;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.fonts.Mapper;
import org.docx4j.fonts.PhysicalFont;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.fonts.fop.fonts.EmbedFontInfo;
import org.docx4j.model.styles.Node;
import org.docx4j.model.styles.StyleTree;
import org.docx4j.model.styles.Tree;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.org.apache.poi.util.IOUtils;
import org.docx4j.wml.BooleanDefaultTrue;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.PPrBase;
import org.docx4j.wml.ParaRPr;
import org.docx4j.wml.R;
import org.docx4j.wml.RPr;
import org.docx4j.wml.Style;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.io.File;

/**
 * @Author: Huan
 * @CreateTime: 2020-05-19 21:15
 */
@Slf4j
public class Word2Pdf4Docx4j {

    public static void main(String[] args) throws Exception {
        convertDocxToPdf("test.docx", "template01.pdf");
    }

    /**
     * docx文档转换为PDF
     * @param body     文档
     * @param response 响应给前端
     * @return pdf 输出流
     * @throws Exception 可能为Docx4JException, FileNotFoundException, IOException等
     */
    public static void convertDocxToPdf(byte[] body, HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
//        File docxFile = FileUtils.byteToFile(body, UUID.randomUUID().toString() + ".docx");
//        try {
//            WordprocessingMLPackage mlPackage = WordprocessingMLPackage.load(docxFile);
//            setFontMapper(mlPackage);
//            Docx4J.toPDF(mlPackage, response.getOutputStream());
//        }catch (Exception e){
//            e.printStackTrace();
//            log.error("docx文档转换为PDF失败");
//        }
        //FileUtil.deleteTempFile(docxFile);
    }


    /**
     * docx文档转换为PDF
     * @param pdfPath PDF文档存储路径
     * @throws Exception 可能为Docx4JException, FileNotFoundException, IOException等
     */
    public static void convertDocxToPdf(String docxPath, String pdfPath) throws Exception {

        FileOutputStream fileOutputStream = null;
        try {
            File file = new File(docxPath);
            fileOutputStream = new FileOutputStream(new File(pdfPath));
            WordprocessingMLPackage mlPackage = WordprocessingMLPackage.load(file);
            MainDocumentPart mainDocumentPart = mlPackage.getMainDocumentPart();

            List<Object> content = mainDocumentPart.getContent();
            content.stream().forEach(c ->{
                if(!(c instanceof P)){
                    return;
                }
                P  p = (P)c;
                PPr pPr = p.getPPr();
                if(pPr == null){
                    return;
                }
                PPrBase.Spacing spacing = pPr.getSpacing();
                ParaRPr rPr1 = pPr.getRPr();
//                BooleanDefaultTrue booleanDefaultTrue = new BooleanDefaultTrue();
//                booleanDefaultTrue.setVal(true);
//                rPr1.setB(booleanDefaultTrue);
//                rPr1.setBCs(booleanDefaultTrue);
                PPrBase.Spacing spacing1 = new PPrBase.Spacing();
                spacing1.setLine(new BigInteger("480"));
                pPr.setSpacing(spacing1);
//                if(spacing != null){
//                    spacing.setLine(new BigInteger("680"));
//                    System.out.println(">>>>>>>>>>>"+c.getClass());
//                    System.out.println(c);
//                }
//                List<Object> content1 = p.getContent();
//                content1.stream().forEach(t->{
//                    if(!(t instanceof R)){
//                        return;
//                    }
//                    R r = (R)t;
//                    RPr rPr = r.getRPr();
//
//                    //booleanDefaultTrue.setVal(true);
//                    rPr.setB(booleanDefaultTrue);
//                    rPr.setBCs(booleanDefaultTrue);
//                });

                // rPr.setB(BooleanDefaultTrue.);
                c.toString();
            });

            setFontMapper(mlPackage);
            Docx4J.toPDF(mlPackage, new FileOutputStream(new File(pdfPath)));


//            FOSettings fo = new FOSettings();
//            fo.setWmlPackage(mlPackage);
//            Docx4J.toFO(fo, new FileOutputStream(new File(pdfPath)), Docx4J.FLAG_EXPORT_PREFER_XSL);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("docx文档转换为PDF失败");
        } finally {
            IOUtils.closeQuietly(fileOutputStream);
        }
    }

    private static void setFontMapper(WordprocessingMLPackage mlPackage) throws Exception {
        Mapper fontMapper = new IdentityPlusMapper();
//        fontMapper.put("隶书", PhysicalFonts.get("LiSu"));
       // fontMapper.put("宋体", PhysicalFonts.get("SimSun"));
//        fontMapper.put("微软雅黑", PhysicalFonts.get("Microsoft Yahei"));
//        fontMapper.put("黑体", PhysicalFonts.get("SimHei"));
//        fontMapper.put("楷体", PhysicalFonts.get("KaiTi"));
//        fontMapper.put("新宋体", PhysicalFonts.get("NSimSun"));
//        fontMapper.put("华文行楷", PhysicalFonts.get("STXingkai"));
//        fontMapper.put("华文仿宋", PhysicalFonts.get("STFangsong"));
//        fontMapper.put("宋体扩展", PhysicalFonts.get("simsun-extB"));
//        fontMapper.put("仿宋", PhysicalFonts.get("FangSong"));
//        fontMapper.put("仿宋_GB2312", PhysicalFonts.get("FangSong_GB2312"));
//        fontMapper.put("幼圆", PhysicalFonts.get("YouYuan"));
//        fontMapper.put("华文宋体", PhysicalFonts.get("STSong"));
//        fontMapper.put("华文中宋", PhysicalFonts.get("STZhongsong"));

//        PhysicalFonts.put("PMingLiU", PhysicalFonts.get("SimSun"));


//       // fontMapper.registerBoldForm("宋体", PhysicalFonts.get("SimSun"));

        PhysicalFonts.addPhysicalFonts("deng", new URL("file:Deng.ttf"));
        PhysicalFonts.addPhysicalFonts("DengXian Bold", new URL("file:Dengb.ttf"));
//
//        PhysicalFonts.addPhysicalFonts("songti", new URL("file:宋体-粗体.ttf"));
//        //宋体&新宋体
//        PhysicalFont simsunFont = PhysicalFonts.get("dengb");
//        PhysicalFont simsunFont1 = PhysicalFonts.get("songti");
//
//        //PhysicalFont boldForm = PhysicalFonts.getBoldForm(simsunFont);
////        System.out.println(">>>>>>>>>>>>>>"+boldForm.getName());
//
//        fontMapper.put("SimSun", simsunFont);
//        fontMapper.put("Times-Bold", simsunFont);
//        //fontMapper.put("Times New Roman", simsunFont1);
        fontMapper.put("等线", PhysicalFonts.get("DengXian Bold"));
//        fontMapper.put("DengXian Bold", PhysicalFonts.get("DengXian Bold"));
//        fontMapper.put("等线 Bold", PhysicalFonts.get("dengb"));
//        fontMapper.put("PMingLiU", PhysicalFonts.get("dengb"));
        fontMapper.put("等线", PhysicalFonts.get("deng"));
        PhysicalFont dengb = PhysicalFonts.get("dengb");
     //   EmbedFontInfo embedFontInfo = dengb.getEmbedFontInfo();
        fontMapper.registerBoldForm("等线",dengb);
        fontMapper.registerRegularForm("等线",PhysicalFonts.get("deng"));

        mlPackage.setFontMapper(fontMapper);
        log.info("{}", PhysicalFonts.get("any"));
        log.info(">>>>>>>>>>{}",mlPackage.getMainDocumentPart().fontsInUse());
        //Docx4jProperties.g

    }

}
