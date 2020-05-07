package word.po;

import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;

/**
 * 
 * @author：songx
 * @date：2017年12月7日,下午4:02:41
 */
public class WordTablePO extends WordPO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3228342471964948078L;

	/**
	 * 列宽,默认100
	 */
	private float[] columnWidths;

	/**
	 * 列的内容
	 */
	private String[][] contents;

	/**
	 * 设置单元格高度，仅当表格的时候使用
	 */
	private float minimumHeight;

	/**
	 * 高度
	 */
	private int height;

	/**
	 * 表格对齐
	 */
	private XWPFVertAlign vertAlign;

	/**
	 * 2003版word
	 * 
	 * @param columnWidths
	 * @param contents
	 * @param minimumHeight
	 * @param align
	 */
	public WordTablePO(float[] columnWidths, String[][] contents, float minimumHeight, int fontAlign) {
		this.columnWidths = columnWidths;
		this.contents = contents;
		this.minimumHeight = minimumHeight;
		setFontAlign(fontAlign);
	}

	/**
	 * 2007版word
	 * 
	 * @param columnWidths
	 * @param contents
	 * @param height
	 * @param vertAlign
	 */
	public WordTablePO(String[][] contents, int height, XWPFVertAlign vertAlign) {
		this.contents = contents;
		this.height = height;
		this.vertAlign = vertAlign;
	}

	public float[] getColumnWidths() {
		return columnWidths;
	}

	public void setColumnWidths(float[] columnWidths) {
		this.columnWidths = columnWidths;
	}

	public String[][] getContents() {
		return contents;
	}

	public void setContents(String[][] contents) {
		this.contents = contents;
	}

	public float getMinimumHeight() {
		return minimumHeight;
	}

	public void setMinimumHeight(float minimumHeight) {
		this.minimumHeight = minimumHeight;
	}

	public XWPFVertAlign getVertAlign() {
		return vertAlign;
	}

	public void setVertAlign(XWPFVertAlign vertAlign) {
		this.vertAlign = vertAlign;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
