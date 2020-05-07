package word.enums;

import com.lowagie.text.Element;

/**
 * 
 * @author：songx
 * @date：2017年12月7日,下午8:41:39
 */
public enum WordAlignEnum {

	UNDEFINED(Element.ALIGN_UNDEFINED), // 默认值
	LEFT(Element.ALIGN_LEFT), // 居左
	RIGHT(Element.ALIGN_RIGHT), // 居右
	CENTER(Element.ALIGN_CENTER), // 居中
	TOP(Element.ALIGN_TOP), // 居上
	BOTTOM(Element.ALIGN_BOTTOM); // 居下

	/**
	 * 值
	 */
	public final int value;

	/**
	 * 构造
	 * 
	 * @param value
	 */
	private WordAlignEnum(int value) {
		this.value = value;
	}

}
