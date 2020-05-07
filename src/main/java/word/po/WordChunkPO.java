package word.po;

/**
 * 
 * @author：songx
 * @date：2017年12月7日,下午2:36:08
 */
public class WordChunkPO extends WordPO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7098149324642811490L;

	/**
	 * 倾斜角色
	 */
	private float skew;

	/**
	 * 是否标题
	 */
	private boolean isLeanding;

	/**
	 * 标题字体大小,大于0时生效，否则使用默认值
	 */
	private float leading;

	/**
	 * 段落前间距，与前一个段落之间的间距
	 */
	private float spacingBefore;

	/**
	 * 段落后间距，与后一个段落之间的间距
	 */
	private float spacingAfter;

	/**
	 * 2003版word,适用于文本段落
	 * 
	 * @param text
	 *            文本
	 * @param pdfFontEnum
	 *            字体
	 * @param isLeanding
	 *            是否标题
	 * @param isAlignCenter
	 *            是否居中
	 * @param leading
	 *            字体大小
	 * @param spacingBefore
	 *            与前一段落的间距
	 * @param spacingAfter
	 *            与后一段落的间距
	 */
	public WordChunkPO(String text, String font, boolean isLeanding, int fontAlign, float spacingBefore,
			float spacingAfter) {
		super();
		setText(text);
		setFont(font);
		setFontAlign(fontAlign);
		this.isLeanding = isLeanding;
		this.spacingBefore = spacingBefore;
		this.spacingAfter = spacingAfter;
	}

	/**
	 * 2007版word
	 * 
	 * @param text
	 * @param align
	 * @param spacingBefore
	 * @param spacingAfter
	 * @param fontSize
	 */
	public WordChunkPO(String text, int fontAlign, float spacingBefore, float spacingAfter, int fontSize) {
		super();
		setText(text);
		setFontAlign(fontAlign);
		this.spacingBefore = spacingBefore;
		this.spacingAfter = spacingAfter;
		setFontSize(fontSize);
	}

	public float getSkew() {
		return skew;
	}

	public void setSkew(float skew) {
		this.skew = skew;
	}

	public boolean isLeanding() {
		return isLeanding;
	}

	public void setLeanding(boolean isLeanding) {
		this.isLeanding = isLeanding;
	}

	public float getLeading() {
		return leading;
	}

	public void setLeading(float leading) {
		this.leading = leading;
	}

	public float getSpacingBefore() {
		return spacingBefore;
	}

	public void setSpacingBefore(float spacingBefore) {
		this.spacingBefore = spacingBefore;
	}

	public float getSpacingAfter() {
		return spacingAfter;
	}

	public void setSpacingAfter(float spacingAfter) {
		this.spacingAfter = spacingAfter;
	}

}
