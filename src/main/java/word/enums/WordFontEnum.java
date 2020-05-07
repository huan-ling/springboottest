package word.enums;

/**
 * 
 * @author：songx
 * @date：2017年12月7日,下午2:38:34
 */
public enum WordFontEnum {

	/*
	 * 正常
	 */
	TEXT_FONT("textFont"),

	/**
	 * 正常，红色
	 */
	RED_TEXT_FONT("redTextFont"),

	/**
	 * 加粗
	 */
	BOLD_FONT("boldFont"),

	/**
	 * 加粗,红色
	 */
	RED_BOLD_FONT("redBoldFont"),

	/**
	 * 一级标题
	 */
	FIRSET_TITLE_FONT("firsetTitleFont"),

	/**
	 * 二级标题
	 */
	SECOND_TITLE_FONT("secondTitleFont"),

	/**
	 * 下划线斜体
	 */
	UNDER_LINE_FONT("underlineFont");

	/**
	 * 值
	 */
	public final String value;

	/**
	 * 构造
	 * 
	 * @param value
	 */
	private WordFontEnum(String value) {
		this.value = value;
	}

}
