/**
 *
 */
package word;

import com.github.pagehelper.util.StringUtil;
import com.google.common.collect.Lists;
import com.huan.springboottest.common.exception.BaseException;
import com.huan.springboottest.common.util.UUIDUtil;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.rtf.RtfWriter2;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import word.po.WordChunkPO;
import word.po.WordImagePO;
import word.po.WordPO;
import word.po.WordTablePO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 *
 * @author：songx
 * @date：2018年1月12日,下午4:19:51
 */
public class WordUtils {

    /**
     *
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WordUtils.class);
//
//    /**
//     * 创建WORD
//     *
//     * @param pdfChunkDOs
//     * @return
//     * @throws Exception
//     */
//    public static String create(List<WordPO> wordPOs) {
//        LOGGER.info("【WORD】创建WORD文档,<START>");
//        if (ListUtils.isEmpty(wordPOs)) {
//            return null;
//        }
//        String outPath = null;
//        Document document = null;
//        OutputStream os = null;
//        RtfWriter2 rtfWriter2 = null;
//        try {
//            // 输出路径
//            outPath = FilePathConst.WORD_PATH + UUIDUitl.taskName() + ".doc";
//            FileUtils.creatFiles(outPath);
//            // 创建输出流
//            os = new FileOutputStream(new File(outPath));
//            // 初始化文档
//            // 创建文档实例
//            // 创建word文档,并设置纸张的大小
//            document = new Document(PageSize.A4);
//            rtfWriter2 = RtfWriter2.getInstance(document, os);
//            document.open();
//            // 添加内容添加PDF实例中
//            addChunks(wordPOs, document);
//        } catch (Exception e) {
//            //FileUtils.deleteFile(outPath);
//            LOGGER.error("【WORD】创建WORD文档失败:" + e.getMessage(), e);
//           // throw new FileException(FileException.FILE_CONTENT_ERROR, e.getMessage());
//        } finally {
//            close(document, os, rtfWriter2);
//        }
//        LOGGER.info("【WORD】创建WORD文档,<END>");
//        return outPath;
//    }

    /**
     * 添加内容到PDF中
     *
     */
    private static void addChunks(List<WordPO> wordPOs, Document document)
            throws BaseException, DocumentException, MalformedURLException, IOException {
        for (WordPO wordPO : wordPOs) {
            if (wordPO instanceof WordChunkPO) {
                addChunk(wordPO, document);
            } else if (wordPO instanceof WordTablePO) {
                addTable(wordPO, document);
            } else if (wordPO instanceof WordImagePO) {
                addImage(wordPO, document);
            }
        }
    }

    /**
     * 添加块
     *

     * @throws DocumentException
     */
    private static void addChunk(WordPO wordPO, Document document) throws BaseException, DocumentException {
        WordChunkPO wordChunkPO = (WordChunkPO) wordPO;
        // 段落
        Paragraph paragraph = null;
        Font font = WordFontUtils.getByType(wordChunkPO.getFont());
        if (wordChunkPO.isLeanding()) {
            paragraph = new Paragraph(wordChunkPO.getText(), font);
            paragraph.setLeading(wordChunkPO.getLeading() > 0 ? wordChunkPO.getLeading() : Float.NaN);
        } else {
            paragraph = new Paragraph();
            // 短语
            Phrase phrase = new Phrase();
            // 短语中的某一块
            Chunk chunk = new Chunk(StringUtil.isEmpty(wordChunkPO.getText()) ? "" : wordChunkPO.getText(), font);
            // skew大于0 的时候生效.字体倾斜角度
            if (wordChunkPO.getSkew() > 0) {
                chunk.setSkew(0, wordChunkPO.getSkew());
            }
            // 将块添加到短语
            phrase.add(chunk);
            // 将短语添加到段落
            paragraph.add(phrase);
        }
        // 段落前间距
        paragraph.setSpacingBefore(wordChunkPO.getSpacingBefore() > 0 ? wordChunkPO.getSpacingBefore() : Float.NaN);
        // 段落后间距
        paragraph.setSpacingAfter(wordChunkPO.getSpacingAfter() > 0 ? wordChunkPO.getSpacingAfter() : Float.NaN);
        // 段落位置
        paragraph.setAlignment(wordChunkPO.getFontAlign());
        // 将段落添加到文档
        document.add(paragraph);
    }

    /**
     * 添加表格

     * @throws DocumentException
     */
    private static void addTable(WordPO wordPO, Document document) throws BaseException, DocumentException {
        WordTablePO wordTablePO = (WordTablePO) wordPO;
        String[][] contents = wordTablePO.getContents();
        if (contents == null || contents.length < 1) {
            return;
        }
        int rowNum = contents.length; // 总行数
        int colNum = wordTablePO.getColumnWidths().length; // 总列数
        if (colNum < 1) {
           // throw new FileException(FileException.FILE_CONTENT_ERROR, "表格列数与内容列数不一致");
            throw new BaseException();
        }
        // 字体
        Font font = WordFontUtils.getByType(wordTablePO.getFont());
        // 创建表格
        Table table = new Table(colNum);
        table.setWidths(wordTablePO.getColumnWidths()); // 设置列宽
        table.setLocked(true); // 锁定列宽
        // 设置行和列
        for (int i = 0; i < rowNum; i++) {
            String[] cols = contents[i];
            for (int j = 0; j < colNum; j++) {
                String text = (j >= cols.length || StringUtil.isEmpty(cols[j])) ? "" : cols[j];
                // 填充单元格内容
                Cell cell = new Cell(new Phrase(text, font));
                // 设置上边的边框宽度
                cell.setBorderWidthTop(0);
                // 设置左边的边框宽度
                cell.setBorderWidthLeft(0);
                // 设置右边的边框宽度
                if (j == (colNum - 1)) {
                    cell.setBorderWidthRight(1);
                }
                // 设置底部的边框宽度
                if (i == (rowNum - 1)) {
                    cell.setBorderWidthBottom(1);
                }
                // 设置单元格高度
                // cell.setMinimumHeight(wordTablePO.getMinimumHeight() > 0 ?
                // wordTablePO.getMinimumHeight() : Float.NaN);
                // 设置可以居中
                cell.setUseAscender(true);
                // 设置水平居中
                cell.setHorizontalAlignment(wordTablePO.getFontAlign());
                // 设置垂直居中
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);
            }
        }
        // 将表格添加到文档
        document.add(table);
    }

    /**
     * 添加图片
     *
     * @param document
     * @throws MalformedURLException
     * @throws IOException
     * @throws DocumentException
     */
    private static void addImage(WordPO wordPO, Document document)
            throws MalformedURLException, IOException, DocumentException {
        WordImagePO wordImagePO = (WordImagePO) wordPO;
        Image image = null;
        if (!StringUtil.isEmpty(wordImagePO.getImageUrl())) {
            if (wordImagePO.getImageUrl().startsWith("http")) {
                image = Image.getInstance(new URL(wordImagePO.getImageUrl()));
            } else {
                image = Image.getInstance(wordImagePO.getImageUrl());
            }
            image.setAlignment(wordImagePO.getFontAlign());
            if (wordImagePO.getHeight() > 0 || wordImagePO.getWidth() > 0) {
                image.scaleAbsolute(wordImagePO.getHeight(), wordImagePO.getWidth());
            }
        } else if (wordImagePO.getImageByte() != null) {
            image = Image.getInstance(wordImagePO.getImageByte());
        } else {
            return;
        }
        // 将表格添加到文档
        document.add(image);
    }

    /**
     * 结束关闭文件流
     *
     * @param document
     * @param os
     */
    private static void close(Document document, OutputStream os, RtfWriter2 rtfWriter2) {
        try {
            if (document != null) {
                document.close();
                document = null;
            }
            if (os != null) {
                os.close();
                os = null;
            }
            if (rtfWriter2 != null) {
                rtfWriter2.close();
            }
        } catch (Exception e) {
            LOGGER.error("【WORD】WORD文件流关闭失败:" + e.getMessage(), e);
        }
    }

    /**
     * 结束关闭文件流
     *
     * @param document
     * @param os
     */
    private static void close(CustomXWPFDocument document, OutputStream os) {
        try {
            if (document != null) {
                document.close();
                document = null;
            }
            if (os != null) {
                os.close();
                os = null;
            }
        } catch (Exception e) {
            LOGGER.error("【WORD】WORD文件流关闭失败:" + e.getMessage(), e);
        }
    }


    private static final String WORD_PATH = System.getProperty("user.dir")+File.separator+"word"+File.separator;
    /**
     * 创建WORD
     *
     * @return
     * @throws Exception
     */
    public static String create2(List<WordPO> wordPOs)  {
        LOGGER.info("【WORD】创建WORD文档,<START>");
//        if (ListUtils.isEmpty(wordPOs)) {
//            return null;
//        }
        String wordTemplate = null;
        String outPath = null;
        CustomXWPFDocument document = null;
        FileOutputStream fos = null;
        try {
            wordTemplate = WORD_PATH  + "template.docx";
            //FileUtils.downloadFileFromUrl(WordConstant.TEMPLATE_URL, wordTemplate);
            OPCPackage pack = POIXMLDocument.openPackage(wordTemplate);
            document = new CustomXWPFDocument(pack);
            WordTest.createHeader(document, "公司", "32");
            // 添加内容到文档中
            addChunks(wordPOs, document);
            // 生成目标word
            outPath = WORD_PATH + UUIDUtil.getTaskName() + ".docx";
            fos = new FileOutputStream(outPath);
            document.write(fos);
            fos.flush();
        } catch (Exception e) {
            LOGGER.error("【WORD】创建WORD文档失败:" + e.getMessage(), e);
            //throw new FileException(FileException.FILE_CONTENT_ERROR, e.getMessage());
        } finally {
            close(document, fos);
           // FileUtils.deleteFile(wordTemplate);
        }
        LOGGER.info("【WORD】创建WORD文档,<END>.[outPath]=" + outPath);
        return outPath;
    }

    /**
     * 添加内容到WORD中
     */
    private static void addChunks(List<WordPO> wordPOs, CustomXWPFDocument document)
            throws BaseException, DocumentException, MalformedURLException, IOException {
        for (WordPO wordPO : wordPOs) {
            if (wordPO instanceof WordChunkPO) {
                addChunk(wordPO, document);
            } else if (wordPO instanceof WordTablePO) {
                addTable(wordPO, document);
            } else if (wordPO instanceof WordImagePO) {
                addImage(wordPO, document);
            }
        }
    }

    /**
     * 添加块
     *
     * @param wordPO
     * @throws DocumentException
     */
    private static void addChunk(WordPO wordPO, CustomXWPFDocument document) {
        WordChunkPO wordChunkPO = (WordChunkPO) wordPO;
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun xwpfRun = paragraph.createRun();
        xwpfRun.setText(wordChunkPO.getText());
        if (wordChunkPO.getFontSize() > 0)
            xwpfRun.setFontSize(wordChunkPO.getFontSize());
        //
        paragraph.setFontAlignment(wordChunkPO.getFontAlign());
        paragraph.setSpacingBeforeLines((int) wordChunkPO.getSpacingBefore());
        paragraph.setSpacingAfterLines((int) wordChunkPO.getSpacingAfter());
        paragraph.setVerticalAlignment(TextAlignment.CENTER);
    }

    public static void main(String[] args) throws IOException {
        List<WordPO> wordPOs = Lists.newArrayList();
        wordPOs.add(new WordChunkPO("标题", ParagraphAlignment.CENTER.getValue(), 20, 20, 20));
        wordPOs.add(new WordChunkPO("qweqweasdasd\raweqdasd\nasdasfas\tfasdfca", ParagraphAlignment.CENTER.getValue(),
                13, 13, 20));
        wordPOs.add(new WordTablePO(new String[][]{{"a", "b", "c", "d"}, {"q", "w", "e"}}, 60,
                XWPFVertAlign.CENTER));
       // wordPOs.add(new WordImagePO("D:\\1.png", ParagraphAlignment.CENTER.getValue(), 200, 200, "1.", null));
        //wordPOs.add(new WordImagePO("D:\\读者圈.jpg", ParagraphAlignment.CENTER.getValue(), 200, 200, "2.", null));
        try {
            System.out.println(create2(wordPOs));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        System.out.println("Game over!");
    }

    /**
     * 添加表格
     *

     * @throws DocumentException
     */
    private static void addTable(WordPO wordPO, CustomXWPFDocument document)  {
        WordTablePO wordTablePO = (WordTablePO) wordPO;
        String[][] contents = wordTablePO.getContents();
        if (contents == null || contents.length < 1) {
            return;
        }
        int rowNum = contents.length; // 总行数
        int colNum = contents[0].length;
        //int colNum = wordTablePO.getColumnWidths().length; // 总列数
        if (colNum < 1) {
            //throw new FileException(FileException.FILE_CONTENT_ERROR, "表格列数与内容列数不一致");
        }
        // 创建表格
        XWPFTable ComTable = document.createTable();
        // 列宽自动分割
        CTTblWidth comTableWidth = ComTable.getCTTbl().addNewTblPr().addNewTblW();
        comTableWidth.setType(STTblWidth.DXA);
        comTableWidth.setW(BigInteger.valueOf(9072));
        // 设置行和列
        for (int i = 0; i < rowNum; i++) {
            String[] cols = contents[i];
            // 创建行
            XWPFTableRow comTableRow = i == 0 ? ComTable.getRow(0) : ComTable.createRow();
            for (int j = 0; j < colNum; j++) {
                String text = (j >= cols.length || StringUtil.isEmpty(cols[j])) ? "" : cols[j];
                // 填充单元格内容
                XWPFTableCell cell = null;
                if (i == 0) {
                    cell = j == 0 ? comTableRow.getCell(0) : comTableRow.addNewTableCell();
                } else {
                    cell = comTableRow.getCell(j);
                }
                cell.setText(text);
                cell.setVerticalAlignment(wordTablePO.getVertAlign());
            }
            // 行高
            if (wordTablePO.getHeight() > 0)
                comTableRow.setHeight(wordTablePO.getHeight());
        }
    }

    /**
     * 添加图片
     *
     */
    private static void addImage(WordPO wordPO, CustomXWPFDocument document) {
        try {
            WordImagePO wordImagePO = (WordImagePO) wordPO;
            XWPFParagraph paragraph = document.createParagraph();
            String relationId = null;
            if (!StringUtil.isEmpty(wordImagePO.getImageUrl())) {
                if (wordImagePO.getImageUrl().startsWith("http")) {
//                    relationId = document.addPictureData(FileUtils.downloadByteFromUrl(wordImagePO.getImageUrl()),
//                            XWPFDocument.PICTURE_TYPE_JPEG);
                } else {
//                    relationId = document.addPictureData(FileUtils.file2byte(wordImagePO.getImageUrl()),
//                            XWPFDocument.PICTURE_TYPE_JPEG);
                }
            } else if (wordImagePO.getImageByte() != null) {
                relationId = document.addPictureData(wordImagePO.getImageByte(), XWPFDocument.PICTURE_TYPE_JPEG);
            } else {
                return;
            }
            if (!StringUtil.isEmpty(relationId)) {
                // 前置文本
                String beforeText = wordImagePO.getBeforeText();
                if (!StringUtil.isEmpty(beforeText)) {
                    XWPFRun xwpfRun = paragraph.createRun();
                    xwpfRun.setText(beforeText);
                    if (wordImagePO.getFontSize() > 0)
                        xwpfRun.setFontSize(wordImagePO.getFontSize());
                }
                // 图片
                int id = document.getAllPictures().size() - 1;
                document.createPicture(paragraph, relationId, id, wordImagePO.getWidth(), wordImagePO.getHeight());
                // 后置文本
                String afterText = wordImagePO.getAfterText();
                if (!StringUtil.isEmpty(afterText)) {
                    XWPFRun xwpfRun = paragraph.createRun();
                    xwpfRun.setText(afterText);
                    if (wordImagePO.getFontSize() > 0)
                        xwpfRun.setFontSize(wordImagePO.getFontSize());
                }
                paragraph.setFontAlignment(wordPO.getFontAlign());
            }
        } catch (Exception e) {
            LOGGER.error("【WORD】创建WORD文档失败:" + e.getMessage(), e);
        }
    }

    /**
     * 根据图片类型获取对应的图片类型代码
     *
     * @param picType
     * @return
     */
    public static int getPictureType(String picType) {
        int res = CustomXWPFDocument.PICTURE_TYPE_PICT;
        if (picType != null) {
            if (picType.equalsIgnoreCase("png")) {
                res = CustomXWPFDocument.PICTURE_TYPE_PNG;
            } else if (picType.equalsIgnoreCase("dib")) {
                res = CustomXWPFDocument.PICTURE_TYPE_DIB;
            } else if (picType.equalsIgnoreCase("emf")) {
                res = CustomXWPFDocument.PICTURE_TYPE_EMF;
            } else if (picType.equalsIgnoreCase("jpg") || picType.equalsIgnoreCase("jpeg")) {
                res = CustomXWPFDocument.PICTURE_TYPE_JPEG;
            } else if (picType.equalsIgnoreCase("wmf")) {
                res = CustomXWPFDocument.PICTURE_TYPE_WMF;
            }
        }
        return res;
    }

}
