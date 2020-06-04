package com.higer.pro.common.word;

import org.docx4j.Docx4J;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.fonts.Mapper;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.model.styles.Node;
import org.docx4j.model.styles.StyleTree;
import org.docx4j.model.styles.Tree;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.WordprocessingML.StyleDefinitionsPart;
import org.docx4j.wml.BooleanDefaultTrue;
import org.docx4j.wml.CTBorder;
import org.docx4j.wml.CTTblPrBase;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.PPrBase;
import org.docx4j.wml.R;
import org.docx4j.wml.RFonts;
import org.docx4j.wml.RPr;
import org.docx4j.wml.RStyle;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.TblBorders;
import org.docx4j.wml.Tc;
import org.docx4j.wml.Tr;

import javax.xml.bind.JAXBElement;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.net.URL;
import java.util.List;

/**
 * @Author: wb_xugz
 * @CreateTime: 2020-06-03 09:16
 */
public class Word2PdfMain {
    public static void main(String[] args) throws Exception {
        String sourceFile = "template.docx";
        String targetFile = "template01.pdf";


        WordprocessingMLPackage mlPackage = WordprocessingMLPackage.load(new File(sourceFile));
        setFont(mlPackage);

        MainDocumentPart mainDocumentPart = mlPackage.getMainDocumentPart();
        List<Object> contentList = mainDocumentPart.getContent();
        StyleTree styleTree = mainDocumentPart.getStyleTree();
        handleStyleTree(styleTree);
        StyleDefinitionsPart styleDefinitionsPart = mainDocumentPart.getStyleDefinitionsPart();
        handleMainContent(contentList, false);
        Docx4J.toPDF(mlPackage, new FileOutputStream(new File(targetFile)));
    }

    public static void handleStyleTree(StyleTree styleTree) {
//        Tree<StyleTree.AugmentedStyle> characterStylesTree = styleTree.getCharacterStylesTree();
//        Node<StyleTree.AugmentedStyle> a9Node = characterStylesTree.get("a9");
//        RPr rPr = a9Node.getData().getStyle().getRPr();
//        rPr.setRFonts(getFont());

        Tree<StyleTree.AugmentedStyle> tableStylesTree = styleTree.getTableStylesTree();
        Node<StyleTree.AugmentedStyle> a3Node = tableStylesTree.get("a3");
        CTTblPrBase tblPr = a3Node.getData().getStyle().getTblPr();
        TblBorders tblBorders = tblPr.getTblBorders();
        CTBorder bottom = tblBorders.getBottom();
        CTBorder insideH = tblBorders.getInsideH();
        CTBorder top = tblBorders.getTop();
        CTBorder insideV = tblBorders.getInsideV();
        CTBorder left = tblBorders.getLeft();
        CTBorder right = tblBorders.getRight();
        setSz(bottom);
        setSz(insideH);
        setSz(top);
        setSz(insideV);
        setSz(left);
        setSz(right);
    }

    private static void setSz(CTBorder ctBorder) {
        ctBorder.setSz(new BigInteger("8"));
    }

    public static void setFont(WordprocessingMLPackage mlPackage) throws Exception {
        // 设置加载的字体 从系统字体安装目录加载
        PhysicalFonts.setRegex("");
        Mapper mapper = new IdentityPlusMapper();
        PhysicalFonts.addPhysicalFonts("deng-self", new URL("file:font/DENG.ttf"));
        PhysicalFonts.addPhysicalFonts("hei-self", new URL("file:font/simhei.ttf"));
        PhysicalFonts.addPhysicalFonts("sun-self", new URL("file:font/simsun.ttc"));
        PhysicalFonts.addPhysicalFonts("sunb-self", new URL("file:font/simsunb.ttc"));
        mapper.put("等线", PhysicalFonts.get("deng-self"));
        mapper.put("黑体", PhysicalFonts.get("hei-self"));
        mapper.put("Times New Roman", PhysicalFonts.get("hei-self"));
        mapper.put("宋体", PhysicalFonts.get("sun-self"));
        mapper.registerBoldForm("宋体", PhysicalFonts.get("sunb-self"));
        mlPackage.setFontMapper(mapper);
    }

    private static void handleMainContent(List<Object> contentList, boolean table) {
        contentList.forEach(c -> {
            if (c instanceof P) {
                P p = (P) c;
                handleP(p, table);
            } else if (c instanceof JAXBElement) {
                JAXBElement jaxbElement = (JAXBElement) c;
                handleJaxb(jaxbElement);
            }
        });
    }

    private static void handleJaxb(JAXBElement jaxbElement) {
        Object value = jaxbElement.getValue();
        if (value != null) {
            if (value instanceof Tbl) {
                Tbl tbl = (Tbl) value;
                handleTable(tbl);
            } else if (value instanceof Tc) {
                Tc tc = (Tc) value;
                handleMainContent(tc.getContent(), true);
            }
        }
    }

    private static void handleTable(Tbl tbl) {
        List<Object> content = tbl.getContent();
        content.forEach(c -> {
            if (c instanceof Tr) {
                Tr tr = (Tr) c;
                List<Object> trContent = tr.getContent();
                trContent.forEach(trc -> {
                    if (trc instanceof JAXBElement) {
                        handleJaxb((JAXBElement) trc);
                    }
                });
            }
        });
    }

    public static void handleP(P p, boolean table) {
        PPr pPr = p.getPPr();
        if (pPr != null && !table) {
            PPrBase.Spacing spacing = new PPrBase.Spacing();
            spacing.setLine(new BigInteger("521"));
            pPr.setSpacing(spacing);
        }
        List<Object> content = p.getContent();
        content.forEach(c -> {
            if (c instanceof R) {
                R r = (R) c;
                RPr rPr = r.getRPr();
                if (rPr != null) {
                    RStyle rStyle = rPr.getRStyle();
                    if (rStyle != null) {
                        if("a9".equals(rStyle.getVal())){
                            rPr.setRFonts(getFont());
                        }
                    } else {
                        BooleanDefaultTrue b = rPr.getB();
                        if (b != null && b.isVal()) {
                            rPr.setRFonts(getFont());
                        }
                    }
                }
            }
        });
    }

    private static RFonts getFont() {
        RFonts rFonts = new RFonts();
        rFonts.setAscii("黑体");
        rFonts.setHAnsi("黑体");
        rFonts.setEastAsia("黑体");
        return rFonts;
    }
}
