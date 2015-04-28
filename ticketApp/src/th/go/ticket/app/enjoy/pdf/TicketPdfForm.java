package th.go.ticket.app.enjoy.pdf;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.simple.JSONObject;

import th.go.ticket.app.enjoy.pdf.utils.MotorItext;
import th.go.ticket.app.enjoy.pdf.utils.PdfFormService;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class TicketPdfForm extends MotorItext implements PdfFormService {
	
	private PdfWriter 	writer;
	private JSONObject 	formDataObj;
	
	private void setWriter(PdfWriter writer) {
		this.writer = writer;
	}

	public void setJSONObject(PdfWriter writer, JSONObject jsonObject) {
		this.formDataObj  	= jsonObject;
		setWriter(writer);
		
	}
	
	public Document createForm(Document document) {
		System.out.println("[SlipPdfForm][createForm][Begin]");
		
		try{
			document.add(this.genHeader());
			document.add(this.brLine());
			document.add(this.brLine());
			document.add(this.brLine());
			document.add(this.brLine());
			document.add(this.brLine());
			document.add(this.brLine());
			document.add(this.brLine());
			document.add(this.brLine());
			document.add(this.genDetail());
//			document.add(this.brLine());
//			document.add(this.genProduct());
//			document.add(this.brLine());
//			document.add(this.genTotalCost());
//			document.add(this.brLine());
//			document.add(this.brLine());
//			document.add(this.brLine());
//			document.add(this.genFooter());
			
			
		}
		catch(DocumentException de){
			de.printStackTrace();
		}catch(MalformedURLException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		finally{
			System.out.println("[SlipPdfForm][createForm][End]");
		}
		
		return document;
	}
	
	private PdfPTable genHeader() throws DocumentException, MalformedURLException, IOException {
		
		float[] 	widths	 		= {60f ,10f ,10f, 10f, 10f};
		PdfPTable 	table 			= new PdfPTable(widths);
		
		
		table.addCell(setCellWB("THAI PREMEIER LEAGE", getFont3(), 1, Element.ALIGN_LEFT, 0));
		table.addCell(setCellWB("", getFont3(), 4, Element.ALIGN_LEFT, 0));
		
		table.addCell(setCellWB("", getFont3(), 1, Element.ALIGN_LEFT, 0));
		table.addCell(setCellWB("1", getFont3(), 1, Element.ALIGN_LEFT, 0));
		table.addCell(setCellWB("W", getFont3(), 1, Element.ALIGN_LEFT, 0));
		table.addCell(setCellWB("L", getFont3(), 1, Element.ALIGN_LEFT, 0));
		table.addCell(setCellWB("L-17", getFont3(), 1, Element.ALIGN_LEFT, 0));
		
		table.addCell(setCellWB("BANGKOK UD         18.00           15/02/2015", getFont3(), 5, Element.ALIGN_LEFT, 0));
		
		table.setWidthPercentage(100);
	
		return table;
	}
	
	private PdfPTable genDetail() throws DocumentException, MalformedURLException, IOException {
		
		float[] 	widths	 		= {1};
		PdfPTable 	table 			= new PdfPTable(widths);
		float[] 	subW1	 		= {25f ,25f ,25f, 25f};
		PdfPTable 	subTab1 		= new PdfPTable(subW1);
		PdfPCell 	cell			= null;
		
		cell = setCellWB("THAI PREMEIER LEAGE", getFont4Bold(), 1, Element.ALIGN_LEFT, 0);
		cell.setRotation(90);
		table.addCell(cell);
		
		cell = setCellWB("1", getFont4Bold(), 1, Element.ALIGN_LEFT, 0);
		cell.setRotation(90);
		subTab1.addCell(cell);
		cell = setCellWB("W", getFont4Bold(), 1, Element.ALIGN_LEFT, 0);
		cell.setRotation(90);
		subTab1.addCell(cell);
		cell = setCellWB("L", getFont4Bold(), 1, Element.ALIGN_LEFT, 0);
		cell.setRotation(90);
		subTab1.addCell(cell);
		cell = setCellWB("L-17", getFont4Bold(), 1, Element.ALIGN_LEFT, 0);
		cell.setRotation(90);
		subTab1.addCell(cell);
		
		cell = setCell(subTab1, 1);
//		cell.setRotation(90);
		table.addCell(cell);
		
//		cell = setCellWB("BANGKOK UTD", getFont4Bold(), 4, Element.ALIGN_LEFT, 0);
//		cell.setRotation(90);
//		table.addCell(cell);
//		
//		cell = setCellWB("18.00", getFont3(), 1, Element.ALIGN_LEFT, 0);
//		cell.setRotation(90);
//		subTabL.addCell(cell);
//		
//		cell = setCellWB("15/02/2015", getFont3(), 1, Element.ALIGN_LEFT, 0);
//		cell.setRotation(90);
//		subTabL.addCell(cell);
//		
//		cell = setCell(subTabL, 4);
//		cell.setRotation(90);
//		table.addCell(cell);
		
		table.setWidthPercentage(100);
	
		return table;
	}
	
	private PdfPTable genProduct() throws DocumentException, MalformedURLException, IOException {
		
		float[] 	widths	 		= {20f ,20f ,20f ,20f ,20f};
		PdfPTable 	table 			= new PdfPTable(widths);
		
		table.addCell(setCell("ยี่ห้อ", getFont8Bold(), 1, 1, Element.ALIGN_CENTER));
		table.addCell(setCell("รุ่น", getFont8Bold(), 1, 1, Element.ALIGN_CENTER));
		table.addCell(setCell("หมายเลขเครื่อง", getFont8Bold(), 1, 1, Element.ALIGN_CENTER));
		table.addCell(setCell("หมายเลขตัวถัง", getFont8Bold(), 1, 1, Element.ALIGN_CENTER));
		table.addCell(setCell("สี", getFont8Bold(), 1, 1, Element.ALIGN_CENTER));
		
		table.addCell(setCell("HONDA", getFont8(), 1, 1, Element.ALIGN_CENTER));
		table.addCell(setCell("ACG110CSFF TH", getFont8(), 1, 1, Element.ALIGN_CENTER));
		table.addCell(setCell("JF382E-0113623", getFont8(), 1, 1, Element.ALIGN_CENTER));
		table.addCell(setCell("MLHJF3823F5113623", getFont8(), 1, 1, Element.ALIGN_CENTER));
		table.addCell(setCell("เหลือง-ดำ", getFont8(), 1, 1, Element.ALIGN_CENTER));
		
		table.setWidthPercentage(100);
	
		return table;
	}
	
	private PdfPTable genTotalCost() throws DocumentException, MalformedURLException, IOException {
		
		float[] 	widths	 		= {55f ,5f ,50f};
		PdfPTable 	table 			= new PdfPTable(widths);
		
		PdfPTable 	subTabL 		= new PdfPTable(1);
		
		float[] 	subWR	 		= {20f, 30f};
		PdfPTable 	subTabR 		= new PdfPTable(subWR);
		
		subTabL.addCell(setCellWB("", getFont12Bold(), 1, Element.ALIGN_LEFT, 0));
		subTabL.addCell(setCellWB("", getFont12Bold(), 1, Element.ALIGN_LEFT, 0));
		subTabL.addCell(setCellWB("", getFont12Bold(), 1, Element.ALIGN_LEFT, 0));
		subTabL.addCell(setCellWB("", getFont12Bold(), 1, Element.ALIGN_LEFT, 0));
		subTabL.addCell(setCellWB("", getFont12Bold(), 1, Element.ALIGN_LEFT, 0));
		subTabL.addCell(setCellWB("", getFont12Bold(), 1, Element.ALIGN_LEFT, 0));
		subTabL.addCell(setCellWB("", getFont12Bold(), 1, Element.ALIGN_LEFT, 0));
		subTabL.addCell(setCellWB("", getFont12Bold(), 1, Element.ALIGN_LEFT, 0));
		subTabL.addCell(setCellWB("", getFont12Bold(), 1, Element.ALIGN_LEFT, 0));
		subTabL.addCell(setCellWB("", getFont12Bold(), 1, Element.ALIGN_LEFT, 0));
		subTabL.addCell(setCellWB("", getFont12Bold(), 1, Element.ALIGN_LEFT, 0));
		subTabL.addCell(setCellWB("", getFont12Bold(), 1, Element.ALIGN_LEFT, 0));
		subTabL.addCell(setCellWB("", getFont12Bold(), 1, Element.ALIGN_LEFT, 0));
		subTabL.addCell(setCell("ห้าหมื่นหนึ่งพันห้าร้อยบาทถ้วน", getFont10(), 1, 1, Element.ALIGN_CENTER));
		table.addCell(setCellWB(subTabL, 1, Element.ALIGN_LEFT, 0, false, false));
		
		table.addCell(setCellWB("", getFont12Bold(), 1, Element.ALIGN_LEFT, 0));
		
		subTabR.addCell(setCell("\n มูลค่าสินค้า \n", getFont8Bold(), 1, 1, Element.ALIGN_RIGHT));
		subTabR.addCell(setCell("\n 48,130.84 \n", getFont8(), 1, 1, Element.ALIGN_RIGHT));
		subTabR.addCell(setCell("\n ภาษีมูลค่าเพิ่ม 7% \n", getFont8Bold(), 1, 1, Element.ALIGN_RIGHT));
		subTabR.addCell(setCell("\n 3,369.16 \n", getFont8(), 1, 1, Element.ALIGN_RIGHT));
		subTabR.addCell(setCell("\n จำนวนเงินรวมทั้งสิ้น 7% \n", getFont8Bold(), 1, 1, Element.ALIGN_RIGHT));
		subTabR.addCell(setCell("\n 51,500 \n", getFont8(), 1, 1, Element.ALIGN_RIGHT));
		table.addCell(setCell(subTabR, 1));
		
		table.setWidthPercentage(100);
	
		return table;
	}
	
	private PdfPTable genFooter() throws DocumentException, MalformedURLException, IOException {
		
		float[] 		widths	 		= {1};
		PdfPTable 		table 			= new PdfPTable(widths);
		PdfContentByte 	cb 				= writer.getDirectContent();
		Barcode128 		code128 		= new Barcode128();
		
		code128.setCode("0123456789abcdefg");
		
		PdfPCell cell1 = new PdfPCell(code128.createImageWithBarcode(cb, null, null));
		
		cell1.setRotation(90);
		table.addCell(cell1);
//		table.addCell(code128.createImageWithBarcode(cb, null, null));
		
		table.setWidthPercentage(100);
	
		return table;
	}

	public PdfPTable brLine() throws DocumentException, MalformedURLException, IOException {
		
		PdfPTable 	table 			= new PdfPTable(1);
		
		table.addCell(setCellWB("", getFont3(), 1, Element.ALIGN_LEFT, 0));
		
		table.setWidthPercentage(100);
	
		return table;
	}
	
}
