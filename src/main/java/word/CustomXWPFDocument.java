package word;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;

import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * 
 * @author：songx
 * @date：2018年1月16日,上午9:27:10
 */
public class CustomXWPFDocument extends XWPFDocument {

	public CustomXWPFDocument(InputStream in) throws IOException {
		super(in);
	}

	public CustomXWPFDocument() {
		super();
	}

	/**
	 * @param pkg
	 * @throws IOException
	 */
	public CustomXWPFDocument(OPCPackage pkg) throws IOException {
		super(pkg);
	}

	public void createPicture(XWPFParagraph paragraph, String relationId, int id, int width, int height) {
		final int EMU = 9525;
		width *= EMU;
		height *= EMU;
		// String blipId =
		// getAllPictures().get(0).getPackageRelationship().getId();
		CTInline inline = paragraph.createRun().getCTR().addNewDrawing().addNewInline();
		StringBuilder sb = new StringBuilder();
		sb.append("<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">");
		sb.append("<a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">");
		sb.append("<pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">");
		sb.append("<pic:nvPicPr>");
		sb.append("<pic:cNvPr id=\"" + id + "\" name=\"generated.jpg\" /><pic:cNvPicPr />");
		sb.append("</pic:nvPicPr>");
		sb.append("<pic:blipFill>");
		sb.append("<a:blip r:embed=\"" + relationId
				+ "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" />");
		sb.append("<a:stretch><a:fillRect /></a:stretch>");
		sb.append("</pic:blipFill>");
		sb.append("<pic:spPr>");
		sb.append("<a:xfrm>");
		sb.append("<a:off x=\"0\" y=\"0\" />");
		sb.append("<a:ext cx=\"" + width + "\" cy=\"" + height + "\" />");
		sb.append("</a:xfrm>");
		sb.append("<a:prstGeom prst=\"rect\"><a:avLst /></a:prstGeom>");
		sb.append("</pic:spPr>");
		sb.append("</pic:pic>");
		sb.append("</a:graphicData>");
		sb.append("</a:graphic>");

		inline.addNewGraphic().addNewGraphicData();
		XmlToken xmlToken = null;
		try {
			xmlToken = XmlToken.Factory.parse(sb.toString());
		} catch (XmlException xe) {
			xe.printStackTrace();
		}
		inline.set(xmlToken);
		// graphicData.set(xmlToken);

		inline.setDistT(0);
		inline.setDistB(0);
		inline.setDistL(0);
		inline.setDistR(0);

		CTPositiveSize2D extent = inline.addNewExtent();
		extent.setCx(width);
		extent.setCy(height);

		CTNonVisualDrawingProps docPr = inline.addNewDocPr();
		docPr.setId(id);
		docPr.setName("图片:" + id);
		docPr.setDescr("123");
	}
}
