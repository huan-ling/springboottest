package word.po;

/**
 * 
 * @author：songx
 * @date：2017年12月7日,下午9:01:00
 */
public class WordImagePO extends WordPO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4336343171137731129L;

	/**
	 * 图片地址，本地路径或者网络路径
	 */
	private String imageUrl;

	/**
	 * 图片byte数组
	 */
	private byte[] imageByte;

	/**
	 * 长度
	 */
	private int height;

	/**
	 * 宽度
	 */
	private int width;

	/**
	 * 前文本
	 */
	private String beforeText;

	/**
	 * 后文本
	 */
	private String afterText;

	public WordImagePO(String imageUrl, int fontAlign) {
		super();
		this.imageUrl = imageUrl;
		setFontAlign(fontAlign);
	}

	public WordImagePO(String imageUrl, int fontAlign, int height, int width, String beforeText, String afterText) {
		super();
		this.imageUrl = imageUrl;
		this.height = height;
		this.width = width;
		this.beforeText = beforeText;
		this.afterText = afterText;
		setFontAlign(fontAlign);
	}

	public WordImagePO(byte[] imageByte, int fontAlign) {
		super();
		this.imageByte = imageByte;
		setFontAlign(fontAlign);
	}

	public WordImagePO(byte[] imageByte, int fontAlign, int height, int width, String beforeText, String afterText) {
		super();
		this.imageByte = imageByte;
		this.height = height;
		this.width = width;
		this.beforeText = beforeText;
		this.afterText = afterText;
		setFontAlign(fontAlign);
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public byte[] getImageByte() {
		return imageByte;
	}

	public void setImageByte(byte[] imageByte) {
		this.imageByte = imageByte;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getBeforeText() {
		return beforeText;
	}

	public void setBeforeText(String beforeText) {
		this.beforeText = beforeText;
	}

	public String getAfterText() {
		return afterText;
	}

	public void setAfterText(String afterText) {
		this.afterText = afterText;
	}

}
