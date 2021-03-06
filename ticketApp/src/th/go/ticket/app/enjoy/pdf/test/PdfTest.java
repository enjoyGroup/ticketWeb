package th.go.ticket.app.enjoy.pdf.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import th.go.ticket.app.enjoy.pdf.utils.PdfFormService;

import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

public class PdfTest {

	public static void main(String[] args) {
		try {
//			writePDFFromFile("D:/motor/PDF/TicketPdfForm_1.pdf");
			writePDF("TicketPdfForm", "D:/motor/JSON/ticket.json", "D:/motor/PDF/TicketPdfForm.pdf");
//			writePDF("SlipPdfTypeTwoForm", "D:/motor/JSON/motor.json", "D:/motor/PDF/SlipPdfTypeTwoForm.pdf");
//			writePDF("SummarySalePdfForm", "D:/motor/JSON/motor.json", "D:/motor/PDF/SummarySalePdfForm.pdf");

//			writeSlipPdfFormPDFFormDB("SlipPdfForm", "PT58/0008", "D:/SlipPdfForm.pdf");
//			writeSlipPdfTypeTwoFormPDFFormDB("SlipPdfTypeTwoForm", "5700000002", "D:/SlipPdfTypeTwoForm.pdf");
//			writeSummarySalePDFFormDB("SummarySalePdfForm", "D:/SummarySalePdfForm.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void writePDF(String formName, String filePath, String pdfPath) throws Exception{
	    String 				formClass					= null;
	    JSONParser 			parser 						= null;
		Document 			document					= null;
		File 				f 							= null;
		FileOutputStream 	fos 						= null;
		PdfWriter 			writer 						= null;
		PdfFormService 		pdfForm 					= null;
		Object 				obj 						= null;
		JSONObject 			jsonObject 					= null;
		Rectangle 			pagesize 					= null;
		
		try{
			System.out.println("formName :: " + formName);
			
			formClass					= "th.go.ticket.app.enjoy.pdf."+formName;
//			document 					= new Document(PageSize.A4);
//			document 					= new Document(pagesize, 36f, 72f, 108f, 180f);
			pagesize 					= new Rectangle(173, 432);
			document 					= new Document(pagesize, 5f, 5f, 5f, 5f);
			parser 						= new JSONParser();
			f 							= new File(pdfPath);
			fos            				= new FileOutputStream(f.getAbsolutePath());			
			writer 						= PdfWriter.getInstance( document,fos  );
	
			document.addTitle("Ticket Form");
			System.out.println(formClass);
	
			Class c 					= 	Class.forName(formClass);
			pdfForm 	        		= 	(PdfFormService) c.newInstance();
		
			document.open();
			
			obj 						= parser.parse(new FileReader(filePath));
			jsonObject 					= (JSONObject) obj;
			
			pdfForm.setJSONObject(writer, jsonObject);
			pdfForm.createForm(document);
	
			document.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
	}
	
	public static void writePDFFromFile(String pdfPath) throws Exception{
		File 				f 							= null;
		FileOutputStream 	fos 						= null;
        PdfStamper 			stamp 						= null;
        InputStream 		inputStream 				= null;
        PdfReader 			reader 						= null;
        AcroFields 			form 						= null;
		
		try{
			
			inputStream 				= (InputStream) new FileInputStream("D://motor//ticket_1.pdf");
            reader 						= new PdfReader(inputStream);
            f 							= new File(pdfPath);
			fos            				= new FileOutputStream(f.getAbsolutePath());
			stamp 						= new PdfStamper(reader, fos);
			form 						= stamp.getAcroFields();
			
			form.setField("gate", "1");
			
			stamp.close();
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
	}
	
//	public static void writeSlipPdfFormPDFFormDB(String formName, String invoiceId, String pdfPath) throws Exception{
//	    String 				formClass					= null;
//		Document 			document					= null;
//		File 				f 							= null;
//		FileOutputStream 	fos 						= null;
//		PdfWriter 			writer 						= null;
//		PdfFormService 		pdfForm 					= null;
////		JSONParser 			parser 						= null;
////		Object 				obj 						= null;
//		JSONObject 			jsonObject 					= null;
//		InvoicedetailsDao   invoicedetailsDao			= null;
//		UserDetailsBean 	userBean					= null;
//		try{
//			System.out.println("formName :: " + formName);
//			
//			formClass					= "th.go.motorcycles.app.enjoy.pdf."+formName;
////			document 					= new Document(PageSize.A5, 36, 36, 36, 70);
//			document 					= new Document(PageSize.A5, 20, 20, 5, 20);
//			//parser 						= new JSONParser();
//			f 							= new File(pdfPath);
//			fos            				= new FileOutputStream(f.getAbsolutePath());			
//			writer 						= PdfWriter.getInstance( document,fos  );
//	
//			document.addTitle("Motor shop Form");
//			System.out.println(formClass);
//	
//			Class c 					= 	Class.forName(formClass);
//			pdfForm 	        		= 	(PdfFormService) c.newInstance();
//		
//			document.open();
//			
//			// สร้าง json Object มาจาก DB
//			invoicedetailsDao			= new InvoicedetailsDao();
//			userBean					= new UserDetailsBean();
//			userBean.setCompanyName("บริษัท สหมอเตอร์ไซด์ จำกัด (สำนักงานใหญ่)");
//			userBean.setTel("02-5215365");
//			userBean.setCompanyAddress("10/102-104 ม.4 ถ.นนทบุรี-ปทุมธานี ต.บางขะแยง อ.เมือง จ.ปทุมธานี 12000");
//			userBean.setBranchName("สำนักงานใหญ่");
//			userBean.setTin("0135552000683");
//			jsonObject 					= invoicedetailsDao.InvoiceSalePDF(invoiceId,userBean);
//					
//			pdfForm.setJSONObject(writer, jsonObject);
//			pdfForm.createForm(document);
//	
//			document.close();
//		}catch(Exception e){
//			e.printStackTrace();
//		}finally{
//			
//		}
//	}
//	
//	public static void writeSlipPdfTypeTwoFormPDFFormDB(String formName, String invoiceId, String pdfPath) throws Exception{
//	    String 				formClass					= null;
//		Document 			document					= null;
//		File 				f 							= null;
//		FileOutputStream 	fos 						= null;
//		PdfWriter 			writer 						= null;
//		PdfFormService 		pdfForm 					= null;
////		JSONParser 			parser 						= null;
////		Object 				obj 						= null;
//		JSONObject 			jsonObject 					= null;
//		InvoicedetailsDao   invoicedetailsDao			= null;
//		UserDetailsBean 	userBean					= null;
//		try{
//			System.out.println("formName :: " + formName);
//			
//			formClass					= "th.go.motorcycles.app.enjoy.pdf."+formName;
//			document 					= new Document(PageSize.A5.rotate());
//			//parser 						= new JSONParser();
//			f 							= new File(pdfPath);
//			fos            				= new FileOutputStream(f.getAbsolutePath());			
//			writer 						= PdfWriter.getInstance( document,fos  );
//	
//			document.addTitle("Motor shop Form");
//			System.out.println(formClass);
//	
//			Class c 					= 	Class.forName(formClass);
//			pdfForm 	        		= 	(PdfFormService) c.newInstance();
//		
//			document.open();
//			
//			// สร้าง json Object มาจาก DB
//			invoicedetailsDao			= new InvoicedetailsDao();
//			userBean					= new UserDetailsBean();
//			userBean.setCompanyName("บริษัท สหมอเตอร์ไซด์ จำกัด (สำนักงานใหญ่)");
//			userBean.setTel("02-5215365");
//			userBean.setCompanyAddress("10/102-104 ม.4 ถ.นนทบุรี-ปทุมธานี ต.บางขะแยง อ.เมือง จ.ปทุมธานี 12000");
//			userBean.setBranchName("สำนักงานใหญ่");
//			userBean.setTin("0135552000683");
//			jsonObject 					= invoicedetailsDao.InvoiceSalePDF(invoiceId,userBean);
//					
//			pdfForm.setJSONObject(writer, jsonObject);
//			pdfForm.createForm(document);
//	
//			document.close();
//		}catch(Exception e){
//			e.printStackTrace();
//		}finally{
//			
//		}
//	}
//		
//	public static void writeSummarySalePDFFormDB(String formName, String pdfPath) throws Exception{
//	    String 				formClass					= null;
//		Document 			document					= null;
//		File 				f 							= null;
//		FileOutputStream 	fos 						= null;
//		PdfWriter 			writer 						= null;
//		PdfFormService 		pdfForm 					= null;
////		JSONParser 			parser 						= null;
////		Object 				obj 						= null;
//		JSONObject 			jsonObject 					= null;
//		InvoicedetailsDao   invoicedetailsDao			= null;
//		UserDetailsBean 	userBean					= null;
//		try{
//			System.out.println("formName :: " + formName);
//			
//			formClass					= "th.go.motorcycles.app.enjoy.pdf."+formName;
//			document 				= new Document(PageSize.A4.rotate());
//			//parser 						= new JSONParser();
//			f 							= new File(pdfPath);
//			fos            				= new FileOutputStream(f.getAbsolutePath());			
//			writer 						= PdfWriter.getInstance( document,fos  );
//	
//			document.addTitle("Motor shop Form");
//			System.out.println(formClass);
//	
//			Class c 					= 	Class.forName(formClass);
//			pdfForm 	        		= 	(PdfFormService) c.newInstance();
//		
//			document.open();
//			
//			// สร้าง json Object มาจาก DB
//			invoicedetailsDao			= new InvoicedetailsDao();
//			userBean					= new UserDetailsBean();
//			userBean.setCompanyName("บริษัท สหมอเตอร์ไซด์ จำกัด (สำนักงานใหญ่)");
//			userBean.setCompanyAddress("10/102-104 ม.4 ถ.นนทบุรี-ปทุมธานี ต.บางขะแยง อ.เมือง จ.ปทุมธานี 12000");
//			userBean.setBranchName("สำนักงานใหญ่");
//			userBean.setTin("0135552000683");
//			jsonObject 					= invoicedetailsDao.SummarySalePDF("", "", "" , "","","",userBean);
//					
//			pdfForm.setJSONObject(writer, jsonObject);
//			pdfForm.createForm(document);
//	
//			document.close();
//		}catch(Exception e){
//			e.printStackTrace();
//		}finally{
//			
//		}
//	}
}
