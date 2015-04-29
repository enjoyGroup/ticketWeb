package th.go.ticket.app.enjoy.pdf;

import java.io.ByteArrayOutputStream;

import org.json.simple.JSONObject;

import th.go.ticket.app.enjoy.pdf.utils.PdfFormService;

import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;

public class ViewPdfMainForm {
	
	public ByteArrayOutputStream writeTicketPDF(String formName, JSONObject jsonObject) throws Exception{
	    String 					formClass					= null;
		Document 				document					= null;
		PdfWriter 				writer 						= null;
		PdfFormService 			pdfForm 					= null;
		ByteArrayOutputStream 	buffer 						= null;
		Rectangle 			pagesize 					= null;
		
		try{
			System.out.println("formName :: " + formName);
			
			formClass					= "th.go.ticket.app.enjoy.pdf."+formName;
			pagesize 					= new Rectangle(173, 432);
			document 					= new Document(pagesize, 5f, 5f, 5f, 5f);

			buffer 						=	new ByteArrayOutputStream();
			writer 						=	PdfWriter.getInstance( document, buffer );
	
			document.addTitle("Motor shop Form");
			Class c 					= 	Class.forName(formClass);
			pdfForm 	        		= 	(PdfFormService) c.newInstance();
			document.open();
					
			pdfForm.setJSONObject(writer, jsonObject);
			pdfForm.createForm(document);
	
			document.close();
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		    formClass					= null;
			document					= null;
			writer 						= null;
			pdfForm 					= null;
			jsonObject 					= null;
		}
		return buffer;
	}
	
}
