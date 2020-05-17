package com.huan.springboottest.pdf;

import com.google.code.appengine.awt.Color;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author: Huan
 * @CreateTime: 2020-05-10 14:41
 */
@Component
public class PdfView extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> map, Document document, PdfWriter pdfWriter,
                                    HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        document.setPageSize(PageSize.A4);
        document.addTitle("用户信息");
        document.add(new Chunk("\n"));
        PdfPTable table = new PdfPTable(3);
        PdfPCell cell = null;
        Font f8 = new Font();
        f8.setColor(Color.BLUE);
        f8.setStyle(Font.BOLD);
        try {
            cell = new PdfPCell(new Paragraph("id", f8));
            cell.setHorizontalAlignment(1);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("user_name",f8));
            cell.setHorizontalAlignment(1);
            table.addCell(cell);
            document.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
