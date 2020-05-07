package word.po;

/**
 * 
 * @author：songx
 * @date：2017年12月7日,下午4:02:59
 */
public class WordPO extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8843881417631211826L;

	/**
	 * 文本
	 */
	private String text;

	/**
	 * 字体样式
	 */
	private String font;

	/**
	 * 位置
	 */
	private int fontAlign;

	/**
	 * 字体大小
	 */
	private int fontSize;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public int getFontAlign() {
		return fontAlign;
	}

	public void setFontAlign(int fontAlign) {
		this.fontAlign = fontAlign;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

}
