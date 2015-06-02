package th.go.ticket.web.enjoy.servlet;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import th.go.ticket.app.enjoy.bean.BookingTypeBean;
import th.go.ticket.app.enjoy.bean.EventMatchBean;
import th.go.ticket.app.enjoy.bean.FieldZoneDetailBean;
import th.go.ticket.app.enjoy.bean.FieldzonemasterBean;
import th.go.ticket.app.enjoy.bean.SeatingDetailBean;
import th.go.ticket.app.enjoy.bean.UserDetailsBean;
import th.go.ticket.app.enjoy.dao.SeatingDetailDao;
import th.go.ticket.app.enjoy.exception.EnjoyException;
import th.go.ticket.app.enjoy.form.SeatingDetailForm;
import th.go.ticket.app.enjoy.main.Constants;
import th.go.ticket.app.enjoy.utils.EnjoyLogger;
import th.go.ticket.app.enjoy.utils.EnjoyUtils;
import th.go.ticket.web.enjoy.common.EnjoyStandardSvc;
import th.go.ticket.web.enjoy.utils.EnjoyUtil;

  
public class SeatingDetailServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(SeatingDetailServlet.class);
	
    private static final String FORM_NAME = "seatingDetailForm";
    
    private EnjoyUtil               	enjoyUtil                   = null;
    private HttpServletRequest          request                     = null;
    private HttpServletResponse         response                    = null;
    private HttpSession                 session                     = null;
    private UserDetailsBean             userBean                    = null;
    private SeatingDetailDao		    dao							= null;
    private SeatingDetailForm		    form						= null; 
    
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		doProcess(request, response);
	}
	
	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("[execute][Begin]");
		
         String pageAction = null; 
 		
 		try{
 			 pageAction 				= EnjoyUtil.nullToStr(request.getParameter("pageAction"));
 			 this.enjoyUtil 			= new EnjoyUtil(request, response);
 			 this.request            	= request;
             this.response           	= response;
             this.session            	= request.getSession(false);
             this.userBean           	= (UserDetailsBean)session.getAttribute("userBean");
             this.form               	= (SeatingDetailForm)session.getAttribute(FORM_NAME);
             this.dao					= new SeatingDetailDao(); 
 			 
             logger.info("[execute][Begin] : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new SeatingDetailForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){ 
 				this.onLoad();
 				logger.info("new : "+pageAction);
 				request.setAttribute("target", Constants.PAGE_URL +"/SeatingDetailScn.jsp");
 			}else if(pageAction.equals("changeZone")){
 				this.lp_onclick_changeZone();
 			} else if(pageAction.equals("NewZone")){
 				this.lp_onclick_saveNewSeatingDetail();
 			}else if(pageAction.equals("UpdateZone")){
 				this.lp_onclick_saveSeatingDetail();
 			}else if(pageAction.equals("getBookingTypeName")){
 				this.getBookingTypeName(); 
 			}else if(pageAction.equals("validateBookingTypeName")){
 				this.validateBookingTypeName();
 			}
 		
 			System.out.print("userBean.getUserLevel()::"+this.userBean.getUserLevel());
 			session.setAttribute(FORM_NAME, this.form);
 			this.form.setUserLevel(this.userBean.getUserLevel());
 		}catch(EnjoyException e){
 			e.printStackTrace();
 			logger.info(e.getMessage());
 		}catch(Exception e){
 			e.printStackTrace();
 			logger.info(e.getMessage());
 		}finally{
 			logger.info("[execute][End]");
 		}
	}
	
 
//	private void onLoad() throws EnjoyException{
//		logger.info("[onLoad][Begin]");
//		
//		List<FieldzonemasterBean> 		zoneList			= null; 
//		
//		try{
//			zoneList 				= this.dao.getZoneList(); 
//			System.out.println("onLoad zoneList :: "+zoneList.size());	
//			if(zoneList!=null && zoneList.size() > 0){ 
//				this.lp_onSet_zoneDetail(zoneList.get(0).getFieldZoneId(),zoneList.get(0).getFieldZoneName()); 
//				this.form.setZoneMasterList(zoneList); 
//			} 
//			
//		}catch(Exception e){
//			e.printStackTrace();
//			throw new EnjoyException("onLoad :: " + e.getMessage());
//		}finally{
//			zoneList			= null;  
//			logger.info("[onLoad][End]");
//		}
//		
//	}
	
	private void onLoad() throws EnjoyException{
		logger.info("[onLoad][Begin]");
		int							    zoneId			    = 0;
		String                          zoneName            = null;
		JSONObject 						obj 				= null; 
		JSONObject 						objDetail 			= null;
		JSONObject                      objBook             = null;
		JSONObject 						objSeason 			= null;
		List<FieldZoneDetailBean> 	    detailList			= null;
		FieldzonemasterBean			    master				= null;
		FieldZoneDetailBean			    zoneDetail			= null;
		JSONArray 						detailJSONArray 	= null; 
		JSONArray 						bookJSONArray 	    = null;
		JSONArray 						zoneJSONArray 	    = null;
		List<FieldzonemasterBean>		zoneList			= null;
		
		try{
			zoneList 				= this.dao.getZoneList(); 
			System.out.println("onLoad zoneList :: "+zoneList.size());	
			if(zoneList!=null && zoneList.size() > 0){  
				this.form.setZoneMasterList(zoneList);
				zoneId   = zoneList.get(0).getFieldZoneId();
				zoneName = zoneList.get(0).getFieldZoneName();
				this.lp_onSet_zoneDetail(zoneId,zoneName);
				this.form.setFieldZoneId(zoneId);
				detailList 	= this.form.getFieldZoneDetailBeans();
				zoneList	= this.form.getZoneMasterList();
				master      = this.form.getFieldzonemasterBean();
				obj 					= new JSONObject();
				detailJSONArray 		= new JSONArray();
				zoneJSONArray 		    = new JSONArray();
				bookJSONArray           = new JSONArray();
				obj.put("ZONE_NAME", 		zoneName); 
				obj.put(STATUS, 			SUCCESS);  
				 
				objDetail 		= new JSONObject(); 
				objDetail.put("fieldZoneId", 	master.getFieldZoneId());
				objDetail.put("fieldZoneName", 	master.getFieldZoneName());
				objDetail.put("rows", 			master.getRows());
				objDetail.put("seating", 		master.getSeating());
				objDetail.put("totalSeating", 	master.getTotalSeating());
				objDetail.put("typeRowName", 	master.getTypeRowName());
				objDetail.put("rowName", 		master.getNameRow());
				objDetail.put("fieldZoneNameTicket", master.getFieldZoneNameTicket());
				objDetail.put("startSeatingNo", 	master.getStartSeatingNo());
				
				for(int i=0;i<detailList.size();i++){		 
					zoneDetail = detailList.get(i);
					objBook    = new JSONObject(); 
					objBook.put("bookingTypeId", 	zoneDetail.getBookingTypeId());
					objBook.put("bookingTypeName",zoneDetail.getBookingTypeName());
					objBook.put("bookingPrices", 	zoneDetail.getBookingPrices());
					objBook.put("seq", 	zoneDetail.getSeq());
					bookJSONArray.add(objBook);
					 
					objDetail.put("bookingList",bookJSONArray);
				}
				
				obj.put("detailSize", 	detailList.size()); 
 
				detailJSONArray.add(objDetail); 

				obj.put("detail", 			detailJSONArray);
		
				
			}else{
				obj.put(STATUS, 			ERROR);
				obj.put(ERR_MSG, 			"Zone ต้องไม่เท่ากับค่าว่าง");
			}
			  
			
		}catch(Exception e){
			e.printStackTrace();
			throw new EnjoyException("onLoad :: " + e.getMessage());
		}finally{
			zoneList			= null;  
			logger.info("[onLoad][End]");
		}
		
	}
	
	private void lp_onSet_zoneDetail(int zoneId,String zoneName) throws EnjoyException{
		logger.info("[lp_onSet_zoneDetail][Begin]"); 
		FieldzonemasterBean         fieldzonemasterBean 	= null;
		List<FieldZoneDetailBean> 	fieldZoneDetailBeans	= null;
		System.out.println("lp_onSet_zoneDetail zoneName :: "+zoneId);
		try{
			logger.info("[lp_onSet_zoneDetail] : send  zoneName :: " +zoneName); 
			fieldZoneDetailBeans = this.dao.getDetailSeatingByZoneId(zoneId); 
			fieldzonemasterBean = this.dao.getFieldMasterByZoneName(zoneId);
			
			this.form.setFieldzonemasterName(zoneName);
			this.form.setFieldZoneId(zoneId);
			this.form.setFieldzonemasterBean(fieldzonemasterBean);
			this.form.setFieldZoneDetailBeans(fieldZoneDetailBeans); 
			
			System.out.println("lp_onSet_zoneDetail getSeatingDetailBeans :: "+this.form.getFieldzonemasterName()); 
			System.out.println("lp_onSet_zoneDetail getSeatingDetailBeans :: "+this.form.getFieldzonemasterBean());
			System.out.println("lp_onSet_zoneDetail fieldZoneDetailBeans :: "+this.form.getFieldZoneDetailBeans());
		}catch(Exception e){
			e.printStackTrace();
			throw new EnjoyException("lp_onSet_zoneDetail :: " + e.getMessage());
		}finally{
			fieldzonemasterBean		= null;
			fieldZoneDetailBeans	= null;
			logger.info("[lp_onSet_zoneDetail][End]");
		}
		
	}
 
	
	private void lp_onclick_changeZone() throws EnjoyException{
		logger.info("[lp_onclick_changeZone][Begin]");
		
		int							    zoneId			    = 0;
		String                          zoneName            = null;
		JSONObject 						obj 				= null; 
		JSONObject 						objDetail 			= null;
		JSONObject                      objBook             = null;
		JSONObject 						objSeason 			= null;
		List<FieldZoneDetailBean> 	    detailList			= null;
		FieldzonemasterBean			    master				= null;
		FieldZoneDetailBean			    zoneDetail			= null;
		JSONArray 						detailJSONArray 	= null; 
		JSONArray 						bookJSONArray 	    = null;
		JSONArray 						zoneJSONArray 	    = null;
		List<FieldzonemasterBean>		zoneList			= null;
		
		try{
			zoneId 					= Integer.valueOf(EnjoyUtils.nullToStr(this.request.getParameter("zoneId")));
			zoneName 				= EnjoyUtils.nullToStr(this.request.getParameter("zoneName"));
			logger.debug("servlet get zoneId is  "+zoneId);
			obj 					= new JSONObject();
			detailJSONArray 		= new JSONArray();
			zoneJSONArray 		    = new JSONArray();
			bookJSONArray           = new JSONArray();
			
			if(zoneName != ""){
				this.lp_onSet_zoneDetail(zoneId,zoneName);
				this.form.setFieldZoneId(zoneId);
				detailList 	= this.form.getFieldZoneDetailBeans();
				zoneList	= this.form.getZoneMasterList();
				master      = this.form.getFieldzonemasterBean();
				
				obj.put("ZONE_NAME", 		zoneName); 
				obj.put(STATUS, 			SUCCESS);  
				 
				objDetail 		= new JSONObject(); 
				objDetail.put("fieldZoneId", 	master.getFieldZoneId());
				objDetail.put("fieldZoneName", 	master.getFieldZoneName());
				objDetail.put("rows", 			master.getRows());
				objDetail.put("seating", 		master.getSeating());
				objDetail.put("totalSeating", 	master.getTotalSeating());
				objDetail.put("typeRowName", 	master.getTypeRowName());
				objDetail.put("rowName", 		master.getNameRow());
				objDetail.put("fieldZoneNameTicket", master.getFieldZoneNameTicket());
				objDetail.put("startSeatingNo", 	master.getStartSeatingNo());
				
				for(int i=0;i<detailList.size();i++){		 
					zoneDetail = detailList.get(i);
					objBook    = new JSONObject(); 
					objBook.put("bookingTypeId", 	zoneDetail.getBookingTypeId());
					objBook.put("bookingTypeName",zoneDetail.getBookingTypeName());
					objBook.put("bookingPrices", 	zoneDetail.getBookingPrices());
					objBook.put("seq", 	zoneDetail.getSeq());
					bookJSONArray.add(objBook);
					 
					objDetail.put("bookingList",bookJSONArray);
				}
				
				obj.put("detailSize", 	detailList.size()); 
 
					detailJSONArray.add(objDetail); 
				   
				
				for(int i=0;i<zoneList.size();i++){ 
					this.lp_onSet_zoneDetail(zoneId,zoneName);
					objSeason = new JSONObject();
					objSeason.put("ZONE_ID", zoneList.get(i).getFieldZoneId()); 
					objSeason.put("ZONE", zoneList.get(i).getFieldZoneName()); 
					zoneJSONArray.add(objSeason);
				}   

				obj.put("detail", 			detailJSONArray);
				obj.put("zoneList", 		zoneJSONArray);
				
			}else{
				obj.put(STATUS, 			ERROR);
				obj.put(ERR_MSG, 			"Zone ต้องไม่เท่ากับค่าว่าง");
			}
			
			
		}catch(Exception e){
			obj.put(STATUS, 			ERROR);
			obj.put(ERR_MSG, 			e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("onLoad :: " + e.getMessage());
		}finally{
			this.enjoyUtil.writeMSG(obj.toString());
			zoneName			= null;
			obj 				= null;
			objDetail 			= null;
			objSeason 			= null;
			detailList			= null;
			master				= null;
			detailJSONArray 	= null;
			zoneJSONArray 	    = null;
			zoneList			= null; 
			logger.info("[lp_onclick_changeZone][End]");
		}
		
	}
	
	private void validateBookingTypeName(){
		System.out.println("[validateBookingTypeName][Begin]");
		String							bookingTypeName			= null;
		int			        			CountbookingTypeName 	= 0; 
		BookingTypeBean 				bookingTypeBean			= null;
		JSONObject 			   			obj 			        = null;  
		
	   try{
		   bookingTypeName				= EnjoyUtils.nullToStr(this.request.getParameter("bookingTypeName"));
		   bookingTypeBean				= this.form.getBookingTypeBean();
		   obj                          = new JSONObject();
		   
		   logger.info("[validateBookingTypeName] bookingTypeName :: " + bookingTypeName);
		   
		   bookingTypeBean.setBookingTypeName(bookingTypeName);
		   CountbookingTypeName 		= this.dao.findBookType(bookingTypeName);
		    
			if(CountbookingTypeName==0){
				obj.put("status", 		"ERROR"); 
				obj.put(ERR_MSG, 		"ระบุประเภทตั๋วไม่ถูกต้อง"); 
				throw new EnjoyException("validateBookingTypeName : " +"ระบุประเภทตั๋วไม่ถูกต้อง");
			}else{
				obj.put("status", 		"SUCCESS"); 
			}
			
	   }catch(Exception e){
		   e.printStackTrace();
		   logger.info("[validateBookingTypeName] " + e.getMessage());
	   }finally{
		   this.enjoyUtil.writeMSG(obj.toString());  
		   logger.info("[validateBookingTypeName][End]");
	   }
	   System.out.println("[validateBookingTypeName][End]");
	}
	
	private void getBookingTypeName(){
		System.out.println("[getBookingTypeName][Begin]");
		String							bookingTypeName			= null;
		List<String>			        list 					= new ArrayList<String>();
		String[]						strArray				= null;
		BookingTypeBean 				bookingTypeBean			= null;
	  
		
	   try{
		   bookingTypeName				= EnjoyUtils.nullToStr(this.request.getParameter("bookingTypeName"));
		   bookingTypeBean				= this.form.getBookingTypeBean();
		                     
		   logger.info("[getBookingTypeName] bookingTypeName :: " + bookingTypeName);
		   
		   bookingTypeBean.setBookingTypeName(bookingTypeName);
		   list 		= this.dao.getBookNameList(bookingTypeName);
		  
		   strArray 	= new String[list.size()];
		   strArray 	= list.toArray(strArray); 
		   
		   this.enjoyUtil.writeJsonMSG((String[]) strArray);
		   
	   }catch(Exception e){
		   e.printStackTrace();
		   logger.info("[getBookingTypeName] " + e.getMessage());
	   }finally{
		   logger.info("[getBookingTypeName][End]");
	   }
	   System.out.println("[getBookingTypeName][End]");
	}
	

	private void lp_onclick_saveSeatingDetail() throws EnjoyException{
		logger.info("[lp_onclick_saveSeatingDetail][Begin]");  
			String 			       getDelList 	            = null; 
			String[]               getSeqList               = null; 
			String[]               getBookingTypeNameList   = null; 
			String[]               getBookingTypePriceList  = null;
			String[]			   statusList 	            = null;
			String                 fieldZoneName            = null;
			String                 fieldNameTicket          = null;
			int                    startSeating             = 0;
			int                    rows                     = 0;
			int                    seating                	= 0;
			int                    totalSeating             = 0;
			int                    nameRowInd               = 0;
			String  			   nameRow	                = null;
			int                    hidZoneMasterId          = 0;
			int  	  			   seq	                	= 0;
			String  			   bookingTypeName	        = null; 
			String  			   bookingTypePrice	        = null; 
			int                    fieldZoneId              = 0;
			FieldzonemasterBean    bean 		    		= null; 
			JSONObject 			   obj 			    		= null;    
			int                    bookingTypeId            = 0;
			FieldZoneDetailBean    fieldZoneDetailBean      = null; 
			String[]               zoneMasterList           = null; 
			String                 status                   = null;
			List<FieldZoneDetailBean>   fieldZoneDetailBeans  = null;
			int                    seqLast              	  = 0;
			int                    newSeq               	  = 0;   
			
			try{  
				obj 						= new JSONObject();
				getSeqList					= this.request.getParameterValues("hidSeq");  
				getBookingTypeNameList		= this.request.getParameterValues("bookingTypeName"); 
				getBookingTypePriceList		= this.request.getParameterValues("bookingTypePrice");   
				zoneMasterList              = this.request.getParameterValues("fieldZoneMasterId"); 
				getDelList      		    = EnjoyUtils.nullToStr(this.request.getParameter("deleteList"));
				statusList                  = this.request.getParameterValues("hidStartus");
				fieldZoneName               = EnjoyUtils.nullToStr(this.request.getParameter("fieldZoneName"));
				rows                        = Integer.valueOf(EnjoyUtils.nullToStr(this.request.getParameter("rows")));
				seating                     = Integer.valueOf(EnjoyUtils.nullToStr(this.request.getParameter("seating")));
				totalSeating                = Integer.valueOf(EnjoyUtils.nullToStr(this.request.getParameter("totalSeating")));
				nameRowInd                  = Integer.valueOf(EnjoyUtils.nullToStr(this.request.getParameter("nameRowInd")));
				nameRow                     = EnjoyUtils.nullToStr(this.request.getParameter("nameRow"));  
				fieldNameTicket   			= EnjoyUtils.nullToStr(this.request.getParameter("nameTicket")); 
				startSeating 				= Integer.valueOf(EnjoyUtils.nullToStr(this.request.getParameter("startNo")));
				hidZoneMasterId				= Integer.valueOf(EnjoyUtils.nullToStr(this.request.getParameter("hidZoneId")));
			  
				System.out.println("[saveSeatingDetail][zoneSelect  : ]" + zoneMasterList[0]); 
				System.out.println("[saveSeatingDetail][getBookingTypeNameList.length  : ]" + getBookingTypeNameList.length); 
 
				fieldZoneId         = Integer.valueOf(EnjoyUtils.nullToStr(zoneMasterList[0]));    
				bean = new FieldzonemasterBean(); 
				bean.setFieldZoneId(hidZoneMasterId);
				bean.setFieldZoneName(fieldZoneName);
				bean.setRows(rows); 
				bean.setSeating(seating);
				bean.setTotalSeating(totalSeating);
				bean.setTypeRowName(nameRowInd);
				bean.setNameRow(nameRow);
				bean.setFieldZoneNameTicket(fieldNameTicket);
				bean.setStartSeatingNo(startSeating); 
				System.out.println("SeatingDetailBean:"+bean.toString());	
				
				 
				seqLast = this.dao.selectMaxSeq(hidZoneMasterId);
				System.out.println("seqLast :: "+seqLast);
				newSeq  = seqLast;
			
				if(getBookingTypeNameList.length>0){ 
					
					fieldZoneDetailBeans = new ArrayList<FieldZoneDetailBean>();
					for(int i = 0 ; i < getBookingTypeNameList.length ; i++){    
						seq           		= Integer.valueOf(EnjoyUtils.nullToStr(getSeqList[i]));   
						System.out.println("seq:"+seq);	 
 						bookingTypeName     = EnjoyUtils.nullToStr(getBookingTypeNameList[i]);     
 						System.out.println("bookingTypeName:"+bookingTypeName);	 
						bookingTypePrice    = EnjoyUtils.nullToStr(getBookingTypePriceList[i]);  
						System.out.println("bookingTypePrice:"+bookingTypePrice);	
						status        		= EnjoyUtils.nullToStr(statusList[i]); 
						System.out.println("status:"+status);	
						bookingTypeId 		= this.dao.findBookTypeId(bookingTypeName);
						System.out.println("bookingTypeId:"+bookingTypeId);	 	
						
				 
						if(bookingTypeId==0){
							obj.put("status", 		"ERROR"); 
							obj.put(ERR_MSG, 		"ระบุประเภทตั๋วไม่ถูกต้อง"); 
							throw new EnjoyException("saveSeatingDetail : " +"ระบุประเภทตั๋วไม่ถูกต้อง");
						}
					 
						
					
						System.out.println("status detail :"+status);	
						fieldZoneDetailBean = new FieldZoneDetailBean();
						fieldZoneDetailBean.setSeq(seq);
						fieldZoneDetailBean.setBookingTypeName(bookingTypeName);
						fieldZoneDetailBean.setBookingPrices(bookingTypePrice); 
						fieldZoneDetailBean.setBookingTypeId(bookingTypeId);
						fieldZoneDetailBean.setFieldZoneId(hidZoneMasterId);
						fieldZoneDetailBean.setStatusPage(status);
						
						if(status.equals("N")){  
							newSeq    = newSeq + 1;
							System.out.println("newSeq :: "+newSeq); 
							System.out.println("insert : " + " hidZoneMasterId = "+hidZoneMasterId+" newSeq = "+newSeq );
							fieldZoneDetailBean.setSeq(newSeq);  
						}

						System.out.println("FieldZoneDetailBean:"+fieldZoneDetailBean.toString());	
						System.out.println("fieldZoneId:"+fieldZoneId);	
						 
						fieldZoneDetailBeans.add(fieldZoneDetailBean); 
					}
					 
				}
				
				this.dao.saveSeatingDetail(bean,fieldZoneDetailBeans, getDelList); 
				obj.put("status",   "SUCCESS"); 

			}catch(Exception e){ 
				obj.put("status", 		"ERROR"); 
				obj.put(ERR_MSG, 		"เกิดข้อผิดพลาดในการรบันทึกข้อมูล"); 
				throw new EnjoyException("saveEventMatch : " +"เกิดข้อผิดพลาดในการรบันทึกข้อมูล");
				
			}finally{   
				this.enjoyUtil.writeMSG(obj.toString());  
				getDelList 	             = null; 
				getSeqList               = null;
				getBookingTypeNameList   = null; 
				getBookingTypePriceList  = null; 
				statusList               = null;
				fieldZoneName            = null;
				rows                     = 0;
				seating                	= 0;
				totalSeating            = 0;
				nameRowInd              = 0;
				nameRow	                = null;
				seq	                    = 0;
				bookingTypeName	        = null;
				bookingTypePrice	    = null; 
				fieldZoneId             = 0;
				bean 		    		= null; 
				obj 			    	= null;  
				fieldZoneDetailBeans    = null;
				status                  = null;
				System.out.println("[lp_onclick_saveSeatingDetail][End]");
			}
		}
	
 
	private void lp_onclick_saveNewSeatingDetail() throws EnjoyException{
		logger.info("[lp_onclick_saveNewSeatingDetail][Begin]");  
		String 			       getDelList 	            = null; 
		String[]               getSeqList               = null;
		String[]               getBookingTypeNameList   = null; 
		String[]               getBookingTypePriceList  = null;
		String[]               getFieldZoneIdList       = null;
		String[]			   statusList 	            = null;  
		int		  			   zoneSelect               = 0; 
		String                 fieldZoneName            = null;
		int                    rows                     = 0;
		int                    seating                	= 0;
		int                    totalSeating             = 0;
		int                    nameRowInd               = 0;
		String  			   nameRow	                = null;
		int  	  			   seq	                	= 0;
		String  			   bookingTypeName	        = null;
		String  			   bookingTypePrice	        = null;
		//Double  			   bookingTypePrice	        = 0.00;
		String  			   status	                = null;
		int                    fieldZoneId              = 0;
		FieldzonemasterBean    bean 		    		= null; 
		JSONObject 			   obj 			    		= new JSONObject();  
		int						bookingTypeId           = 0;
		FieldZoneDetailBean     fieldZoneDetailBean     = null;
		int                     findFieldZoneMasterId   = 0;
		String                 fieldNameTicket          = null;
		int                    startSeating             = 0; 
		List<FieldZoneDetailBean>   fieldZoneDetailBeans  = null;
		int 				   seqNo       			    = 1;
		try{  
			obj 						= new JSONObject(); 
			getBookingTypeNameList		= this.request.getParameterValues("bookingTypeName"); 
			getBookingTypePriceList		= this.request.getParameterValues("bookingTypePrice");  
			fieldZoneName               = EnjoyUtils.nullToStr(this.request.getParameter("fieldZoneName"));
			rows                        = Integer.valueOf(EnjoyUtils.nullToStr(this.request.getParameter("rows")));
			seating                     = Integer.valueOf(EnjoyUtils.nullToStr(this.request.getParameter("seating")));
			totalSeating                = Integer.valueOf(EnjoyUtils.nullToStr(this.request.getParameter("totalSeating")));
			nameRowInd                  = Integer.valueOf(EnjoyUtils.nullToStr(this.request.getParameter("nameRowInd")));
			nameRow                     = EnjoyUtils.nullToStr(this.request.getParameter("nameRow")); 
			fieldNameTicket   			= EnjoyUtils.nullToStr(this.request.getParameter("nameTicket")); 
			startSeating 				= Integer.valueOf(EnjoyUtils.nullToStr(this.request.getParameter("startNo")));
   
			bean = new FieldzonemasterBean();  
			bean.setFieldZoneName(fieldZoneName);
			bean.setRows(rows); 
			bean.setSeating(seating);
			bean.setTotalSeating(totalSeating);
			bean.setTypeRowName(nameRowInd);
			bean.setNameRow(nameRow);
			bean.setFieldZoneNameTicket(fieldNameTicket);
			bean.setStartSeatingNo(startSeating);
//			this.dao.saveNewSeatingMaster(bean);
 	 
			if(getBookingTypeNameList.length>0){  
				fieldZoneDetailBeans = new ArrayList<>();
				for(int i = 0 ; i < getBookingTypeNameList.length ; i++){    
					System.out.println("getBookingTypeNameList[i]:"+getBookingTypeNameList[i]);	
					bookingTypeName     = EnjoyUtils.nullToStr(getBookingTypeNameList[i]);     
					//bookingTypePrice    = Double.valueOf(EnjoyUtils.nullToStr(getBookingTypePriceList[i]));   
					bookingTypePrice    = EnjoyUtils.nullToStr(getBookingTypePriceList[i]);   
					
					bookingTypeId = this.dao.findBookTypeId(bookingTypeName);
					
					if(bookingTypeId==0){
						obj.put("status", 		"ERROR"); 
						obj.put(ERR_MSG, 		"ระบุประเภทตั๋วไม่ถูกต้อง"); 
						throw new EnjoyException("saveEventMatch : " +"ระบุประเภทตั๋วไม่ถูกต้อง");
					}
					     
					System.out.println("fieldZoneId:"+fieldZoneId);	
					
					fieldZoneDetailBean = new FieldZoneDetailBean();
					fieldZoneDetailBean.setSeq(seqNo++); 
					fieldZoneDetailBean.setBookingPrices(bookingTypePrice); 
					fieldZoneDetailBean.setBookingTypeId(bookingTypeId); 
					//fieldZoneDetailBean.setFieldZoneId(findFieldZoneMasterId);
					System.out.println("fieldZoneDetailBean:"+fieldZoneDetailBean.toString());	
					
					fieldZoneDetailBeans.add(fieldZoneDetailBean);
					  
				}
				  
			}
			
			this.dao.saveNewSeatingDetail(bean,fieldZoneDetailBeans);      
			obj.put("status",   "SUCCESS");  
			 
			}catch(Exception e){  
				obj.put("status", 		"ERROR"); 
				obj.put(ERR_MSG, 		"เกิดข้อผิดพลาดในการบันทึกข้อมูล"); 
				throw new EnjoyException("saveEventMatch : " +"เกิดข้อผิดพลาดในการบันทึกข้อมูล");
				
			}finally{   
				this.enjoyUtil.writeMSG(obj.toString());  
				getDelList 	             = null; 
				getSeqList               = null;
				getBookingTypeNameList   = null; 
				getBookingTypePriceList  = null;
				getFieldZoneIdList       = null;
				statusList 	             = null;  
				zoneSelect               = 0; 
				fieldZoneName            = null;
				rows                     = 0;
				seating                	= 0;
				totalSeating            = 0;
				nameRowInd              = 0;
				nameRow	                = null;
				seq	                    = 0;
				bookingTypeName	        = null;
				bookingTypePrice	    = null;
				status	                = null;
				fieldZoneId             = 0;
				bean 		    		= null; 
				obj 			    	= null;
				fieldZoneDetailBeans    = null;
				System.out.println("[lp_onclick_saveNewSeatingDetail][End]");
			}
		}
  
}
