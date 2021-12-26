package com.hotelbooking.model;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.hotelbooking.entity.CustomerEntity;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class UserPDFExporter {

	private List<CustomerEntity> customerList;

	public UserPDFExporter(List<CustomerEntity> customerList) {
		super();
		this.customerList = customerList;
	}
	
	 private void writeTableHeader(PdfPTable table) {
	        PdfPCell cell = new PdfPCell();
	        cell.setBackgroundColor(Color.BLUE);
	        cell.setPadding(5);
	         
	        Font font = FontFactory.getFont(FontFactory.HELVETICA);
	        font.setColor(Color.WHITE);
	         
	        cell.setPhrase(new Phrase("Customer ID", font));
	         
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Customer Name", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Room No", font));
	        table.addCell(cell);
	        
	        cell.setPhrase(new Phrase("Age", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("City", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("State", font));
	        table.addCell(cell);     
	        
	        cell.setPhrase(new Phrase("Current Status", font));
	        table.addCell(cell);
	    }
	     
	    private void writeTableData(PdfPTable table) {
	        for (CustomerEntity ce : customerList) {
	            table.addCell(String.valueOf(ce.getCustomerId()));
	            table.addCell(ce.getCustomerName());
	            table.addCell(ce.getRoomNo());
	            table.addCell(String.valueOf(ce.getAge()));
	            table.addCell(ce.getCity());
	            table.addCell(ce.getState());
	            table.addCell(ce.getCurrentStatus());
	        }
	    }
	     
	    public void export(HttpServletResponse response) throws DocumentException, IOException {
	        Document document = new Document(PageSize.A4);
	        PdfWriter.getInstance(document, response.getOutputStream());
	         
	        document.open();
	        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        font.setSize(18);
	        font.setColor(Color.BLUE);
	         
	        Paragraph p = new Paragraph("List of Customers", font);
	        p.setAlignment(Paragraph.ALIGN_CENTER);
	         
	        document.add(p);
	         
	        PdfPTable table = new PdfPTable(7);
	        table.setWidthPercentage(100f);
	        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f});
	        table.setSpacingBefore(10);
	         
	        writeTableHeader(table);
	        writeTableData(table);
	         
	        document.add(table);
	         
	        document.close();
	         
	    }
}
