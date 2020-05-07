package word;

import com.google.code.appengine.awt.Color;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author：songx
 * @date：2017年12月7日,下午2:50:43
 */
public class WordFontUtils {

    /**
     *
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WordFontUtils.class);

    /**
     *
     */
    private static Font textFont = null;

    /**
     *
     */
    private static Font redTextFont = null;

    /**
     *
     */
    private static Font boldFont = null;

    /**
     *
     */
    private static Font redBoldFont = null;

    /**
     *
     */
    private static Font firsetTitleFont = null;

    /**
     *
     */
    private static Font secondTitleFont = null;

    /**
     *
     */
    private static Font underlineFont = null;

    static {
        // 添加中文字体
        BaseFont bfChinese = null;
        try {
            bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        } catch (Exception e) {
            LOGGER.error("【PDF字体】初始化字体失败:" + e.getMessage(), e);
        }
        if (bfChinese != null) {
            // 初始化设置字体样式
            textFont = new Font(Font.NORMAL, 13, Font.NORMAL); // 正常
            redTextFont = new Font(Font.NORMAL, 13, Font.NORMAL, Color.RED); // 正常,红色
            boldFont = new Font(Font.NORMAL, 13, Font.BOLD); // 加粗
            redBoldFont = new Font(Font.NORMAL, 13, Font.BOLD, Color.RED); // 加粗,红色
            firsetTitleFont = new Font(Font.NORMAL, 25, Font.BOLD); // 一级标题
            secondTitleFont = new Font(Font.NORMAL, 17, Font.BOLD); // 二级标题
            underlineFont = new Font(Font.NORMAL, 13, Font.UNDERLINE); // 下划线斜体
        }
    }

    public static Font getByType(String font) {
        if (font == null) {
            return textFont;
        }
        switch (font) {
            case "textFont":
                return textFont;
            case "redTextFont":
                return redTextFont;
            case "boldFont":
                return boldFont;
            case "redBoldFont":
                return redBoldFont;
            case "firsetTitleFont":
                return firsetTitleFont;
            case "secondTitleFont":
                return secondTitleFont;
            case "underlineFont":
                return underlineFont;
            default:
                return textFont;
        }
    }

}
