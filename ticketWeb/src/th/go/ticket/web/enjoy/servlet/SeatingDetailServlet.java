package th.go.ticket.web.enjoy.servlet;

import java.io.IOException; 
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
import th.go.ticket.app.enjoy.utils.HibernateUtil;
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
	
 
	private void onLoad() throws EnjoyException{
		logger.info("[onLoad][Begin]");
		
		List<FieldzonemasterBean> 		zoneList			= null;
		int						        zoneSelect			= 0;
		List<SeatingDetailBean> 	    detailList			= null;
		SeatingDetailBean			    detail				= null;
		FieldZoneDetailBean             booking             = null;
		JSONObject 						obj 				= null;
		JSONObject 						objDetail 			= null;
		JSONObject 						objDetail2 			= null;
		JSONArray 						detailJSONArray 	= null;
		JSONArray 						bookingJSONArray    = null;
		
		try{
			zoneList 				= this.dao.getZoneList();
			obj 					= new JSONObject();
			objDetail2              = new JSONObject();
			detailJSONArray 		= new JSONArray();
			bookingJSONArray        = new JSONArray();
			System.out.println("onLoad zoneList :: "+zoneList.size());	
			if(zoneList!=null && zoneList.size() > 0){
		 
				this.lp_onSet_zoneDetail(zoneList.get(0).getFieldZoneId(),zoneList.get(0).getFieldZoneName());
 			
  /*              detailList 	= this.form.getSeatingDetailBeans();
            	
    			
				for(int i=0;i<detailList.size();i++){
					detail 			= detailList.get(i);
				System.out.println("detail.toString()::"+detail.toString());
					objDetail 		= new JSONObject(); 
					objDetail.put("fieldZoneId", 	detail.getFieldZoneId()); 
					objDetail.put("fieldZoneName", 	detail.getFieldZoneName());
					objDetail.put("rows", 			detail.getRows());
					objDetail.put("seating", 		detail.getSeating());
					objDetail.put("totalSeating", 	detail.getTotalSeating());
					objDetail.put("typeRowName", 	detail.getTypeRowName());
					objDetail.put("rowName", 		detail.getNameRow());

//					objDetail.put("bookingTypeId", 	detail.getBookingTypeId());
//					objDetail.put("bookingTypeName",detail.getBookingTypeName());
//					objDetail.put("bookingPrices", 	detail.getBookingPrices());
//					objDetail.put("seq", 		detail.getSeq());
					
					if(detail.getFieldZoneDetailBeans() != null){
						for(int j=0;j<detail.getFieldZoneDetailBeans().size();j++){
							booking = detail.getFieldZoneDetailBeans().get(j);
							objDetail2.put("bookingTypeId", 	booking.getBookingTypeId());
							objDetail2.put("bookingTypeName",   booking.getBookingTypeName());
							objDetail2.put("bookingPrices", 	booking.getBookingPrices());
							objDetail2.put("seq", 		        booking.getSeq());
						}
						objDetail.put("", value)
					}
					
					detailJSONArray.add(objDetail);
				}
				
				obj.put("detail", 			detailJSONArray);
		 
			}*/
			
			this.form.setFieldzonemasterBeans(zoneList);
		}	
			
//		}catch(Exception e){
//			e.printStackTrace();
//			throw new EnjoyException("onLoad :: " + e.getMessage());
		}finally{
			zoneList			= null;
			zoneSelect			= 0;
			detailList			= null;
			detail				= null;
			obj 				= null;
			objDetail 			= null;
			detailJSONArray 	= null;
	
			logger.info("[onLoad][End]");
		}
		
	}
	
	private void lp_onSet_zoneDetail(int zoneId,String zoneName) throws EnjoyException{
		logger.info("[lp_onSet_zoneDetail][Begin]");
		List<SeatingDetailBean> 	seatingDetailBeans	= null;
		System.out.println("lp_onSet_zoneDetail zoneName :: "+zoneId);
		try{
			logger.info("[lp_onSet_zoneDetail] : send  zoneName :: " +zoneName);
			seatingDetailBeans	= this.dao.getDetailSeatingByZoneName(zoneId);
			
			logger.info("[lp_onSet_zoneDetail] : size :: " +seatingDetailBeans.size());
			logger.info("[lp_onSet_zoneDetail] :  " +seatingDetailBeans.toString());
			
			this.form.setFieldzonemasterName(zoneName);
			this.form.setSeatingDetailBeans(seatingDetailBeans);
			
			System.out.println("lp_onSet_zoneDetail getSeatingDetailBeans :: "+this.form.getFieldzonemasterName());
			System.out.println("lp_onSet_zoneDetail getSeatingDetailBeans :: "+this.form.getSeatingDetailBeans());
		}catch(Exception e){
			e.printStackTrace();
			throw new EnjoyException("lp_onSet_zoneDetail :: " + e.getMessage());
		}finally{
			seatingDetailBeans		= null;
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
		List<SeatingDetailBean> 	    detailList			= null;
		SeatingDetailBean			    detail				= null;
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
			 
				detailList 	= this.form.getSeatingDetailBeans();
				zoneList	= this.form.getFieldzonemasterBeans();
				
				obj.put("ZONE_NAME", 		zoneName); 
				obj.put(STATUS, 			SUCCESS);  
				
				for(int i=0;i<detailList.size();i++){
					detail 			= detailList.get(i);
					objDetail 		= new JSONObject(); 
					objDetail.put("fieldZoneId", 	detail.getFieldZoneId());
					objDetail.put("fieldZoneName", 	detail.getFieldZoneName());
					objDetail.put("rows", 			detail.getRows());
					objDetail.put("seating", 		detail.getSeating());
					objDetail.put("totalSeating", 	detail.getTotalSeating());
					objDetail.put("typeRowName", 	detail.getTypeRowName());
					objDetail.put("rowName", 		detail.getNameRow());
					
					if(detail.getFieldZoneDetailBeans() != null){
						for(int j = 0 ; j < detail.getFieldZoneDetailBeans().size() ; j++){ 
							zoneDetail = detail.getFieldZoneDetailBeans().get(j);
							objBook    = new JSONObject(); 
							objBook.put("bookingTypeId", 	zoneDetail.getBookingTypeId());
							objBook.put("bookingTypeName",zoneDetail.getBookingTypeName());
							objBook.put("bookingPrices", 	zoneDetail.getBookingPrices());
							objBook.put("seq", 	zoneDetail.getSeq());
							bookJSONArray.add(objBook);
						}
						objDetail.put("bookingList",bookJSONArray);
					}
					obj.put("detailSize", 		detail.getFieldZoneDetailBeans().size());
					
 
					detailJSONArray.add(objDetail); 
				}  
				
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
			detail				= null;
			detailJSONArray 	= null;
			zoneJSONArray 	    = null;
			zoneList			= null; 
			logger.info("[lp_onclick_changeZone][End]");
		}
		
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
			Double  			   bookingTypePrice	        = 0.00;
			String  			   status	                = null;
			int                    fieldZoneId              = 0;
			SeatingDetailBean 	   bean 		    		= null; 
			JSONObject 			   obj 			    		= null; 
			List<SeatingDetailBean> delList                 = null; 
			String                 getFieldZoneId           = null;
			int                    bookingTypeId            = 0;
			FieldZoneDetailBean    fieldZoneDetailBean      = null;
			int                    hidZoneId                = 0;
			String[]               zoneMasterList           = null;
			
			try{ 
				obj 						= new JSONObject();
				getSeqList					= this.request.getParameterValues("seq"); 
				getBookingTypeNameList		= this.request.getParameterValues("bookingTypeName"); 
				getBookingTypePriceList		= this.request.getParameterValues("bookingTypePrice");  
				statusList                  = this.request.getParameterValues("hidStartus"); 
				zoneMasterList              = this.request.getParameterValues("fieldZoneMasterId"); 
				getDelList      		    = EnjoyUtils.nullToStr(this.request.getParameter("deleteList"));
				zoneSelect                  = Integer.valueOf(EnjoyUtils.nullToStr(this.request.getParameter("hidZoneId")));
				fieldZoneName               = EnjoyUtils.nullToStr(this.request.getParameter("fieldZoneName"));
				rows                        = Integer.valueOf(EnjoyUtils.nullToStr(this.request.getParameter("rows")));
				seating                     = Integer.valueOf(EnjoyUtils.nullToStr(this.request.getParameter("seating")));
				totalSeating                = Integer.valueOf(EnjoyUtils.nullToStr(this.request.getParameter("totalSeating")));
				nameRowInd                  = Integer.valueOf(EnjoyUtils.nullToStr(this.request.getParameter("nameRowInd")));
				nameRow                     = EnjoyUtils.nullToStr(this.request.getParameter("nameRow")); 
				getFieldZoneId			    = EnjoyUtils.nullToStr(this.request.getParameter("hidZoneId"));
				 
		 
				System.out.println("[saveSeatingDetail][zoneSelect  : ]" + zoneMasterList[0]); 
				System.out.println("[saveSeatingDetail][getBookingTypeNameList.length  : ]" + getBookingTypeNameList.length); 
				
				fieldZoneId         = Integer.valueOf(EnjoyUtils.nullToStr(zoneMasterList[0]));    
				bean = new SeatingDetailBean(); 
				bean.setFieldZoneId(fieldZoneId);
				bean.setFieldZoneName(fieldZoneName);
				bean.setRows(rows); 
				bean.setSeating(seating);
				bean.setTotalSeating(totalSeating);
				bean.setTypeRowName(nameRowInd);
				bean.setNameRow(nameRow);
				this.dao.updateSeatingDetail(bean);
				
				if(getBookingTypeNameList.length>0){ 
					for(int i = 0 ; i < getBookingTypeNameList.length ; i++){  
						seq           		= Integer.valueOf(EnjoyUtils.nullToStr(getSeqList[i]));   
						bookingTypeName     = EnjoyUtils.nullToStr(getBookingTypeNameList[i]);     
						bookingTypePrice    = Double.valueOf(EnjoyUtils.nullToStr(getBookingTypePriceList[i]));   
						status              = statusList[i];
						
						bookingTypeId = this.dao.findBookTypeId(bookingTypeName);
						
						if(bookingTypeId==0){
							obj.put("status", 		"ERROR"); 
							obj.put(ERR_MSG, 		"ระบุประเภทตั๋วไม่ถูกต้อง"); 
							throw new EnjoyException("saveEventMatch : " +"ระบุประเภทตั๋วไม่ถูกต้อง");
						}
						    
						fieldZoneDetailBean = new FieldZoneDetailBean();
						fieldZoneDetailBean.setSeq(seq);
						fieldZoneDetailBean.setBookingTypeName(bookingTypeName);
						fieldZoneDetailBean.setBookingPrices(bookingTypePrice); 
						fieldZoneDetailBean.setBookingTypeId(bookingTypeId);
						fieldZoneDetailBean.setFieldZoneId(bean.getFieldZoneId());
						System.out.println("fieldZoneDetailBean:"+bean.toString());	
						System.out.println("fieldZoneId:"+fieldZoneId);	
						
						if(status.equals("N") ){ //detail
							this.dao.insertFieldZoneDetail(fieldZoneDetailBean);    
						}else if(status.equals("U")){
							this.dao.updateFieldZoneDetail(fieldZoneDetailBean);  
						}
						 
					}
					 
				}
				
				if(!"none".equals(getDelList)){   
					List<String> deleteList = EnjoyUtils.getListFromArr(getDelList);
					System.out.print("deleteList : size ::"+deleteList.size());
					int fieldZoneSeqDel ;
					if(deleteList != null){
						if(deleteList.size()>0){
							for(String del :deleteList){
								System.out.print("del"+deleteList.size());
								if(del != ""){ 
									fieldZoneSeqDel = Integer.valueOf(del); 
									System.out.print("delete : seq is ::" +fieldZoneSeqDel + " and fieldZoneId = " + bean.getFieldZoneId()); 
									this.dao.deleteSeatingDetail(bean.getFieldZoneId(),fieldZoneSeqDel); 
								}
							}
							
						} 
					}
				}
				 
				this.onLoad();
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
				bookingTypePrice	    = 0.00;
				status	                = null;
				fieldZoneId             = 0;
				bean 		    		= null; 
				obj 			    	= null;
				delList                 = null; 
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
		Double  			   bookingTypePrice	        = 0.00;
		String  			   status	                = null;
		int                    fieldZoneId              = 0;
		SeatingDetailBean 	   bean 		    		= null; 
		JSONObject 			   obj 			    		= new JSONObject(); 
		List<SeatingDetailBean> delList                 = null; 
		int						bookingTypeId           = 0;
		FieldZoneDetailBean     fieldZoneDetailBean     = null;
		int                     findFieldZoneMasterId   = 0;
		
		try{ 
			obj 						= new JSONObject();
			getSeqList					= this.request.getParameterValues("seq"); 
			getBookingTypeNameList		= this.request.getParameterValues("bookingTypeName"); 
			getBookingTypePriceList		= this.request.getParameterValues("bookingTypePrice");    
			fieldZoneName               = EnjoyUtils.nullToStr(this.request.getParameter("fieldZoneName"));
			rows                        = Integer.valueOf(EnjoyUtils.nullToStr(this.request.getParameter("rows")));
			seating                     = Integer.valueOf(EnjoyUtils.nullToStr(this.request.getParameter("seating")));
			totalSeating                = Integer.valueOf(EnjoyUtils.nullToStr(this.request.getParameter("totalSeating")));
			nameRowInd                  = Integer.valueOf(EnjoyUtils.nullToStr(this.request.getParameter("nameRowInd")));
			nameRow                     = EnjoyUtils.nullToStr(this.request.getParameter("nameRow")); 
 
			System.out.println("[lp_onclick_saveNewSeatingDetail][zoneSelect  : ]" + zoneSelect); 
			
			bean = new SeatingDetailBean();  
			bean.setFieldZoneName(fieldZoneName);
			bean.setRows(rows); 
			bean.setSeating(seating);
			bean.setTotalSeating(totalSeating);
			bean.setTypeRowName(nameRowInd);
			bean.setNameRow(nameRow);
		    this.dao.insertSeatingMaster(bean);
			 
			findFieldZoneMasterId = this.dao.findFieldZoneMasterId(fieldZoneName);
			System.out.println("findFieldZoneMasterId:"+findFieldZoneMasterId);	
			
			if(getBookingTypeNameList.length>0){ 
				for(int i = 0 ; i < getBookingTypeNameList.length ; i++){  
					seq           		= Integer.valueOf(EnjoyUtils.nullToStr(getSeqList[i]));   
					bookingTypeName     = EnjoyUtils.nullToStr(getBookingTypeNameList[i]);     
					bookingTypePrice    = Double.valueOf(EnjoyUtils.nullToStr(getBookingTypePriceList[i]));   
					
					bookingTypeId = this.dao.findBookTypeId(bookingTypeName);
					
					if(bookingTypeId==0){
						obj.put("status", 		"ERROR"); 
						obj.put(ERR_MSG, 		"ระบุประเภทตั๋วไม่ถูกต้อง"); 
						throw new EnjoyException("saveEventMatch : " +"ระบุประเภทตั๋วไม่ถูกต้อง");
					}
					    
					System.out.println("fieldZoneId:"+fieldZoneId);	
					
					fieldZoneDetailBean = new FieldZoneDetailBean();
					fieldZoneDetailBean.setSeq(seq); 
					fieldZoneDetailBean.setBookingPrices(bookingTypePrice); 
					fieldZoneDetailBean.setBookingTypeId(bookingTypeId); 
					fieldZoneDetailBean.setFieldZoneId(findFieldZoneMasterId);
					System.out.println("fieldZoneDetailBean:"+fieldZoneDetailBean.toString());	
					
					
					this.dao.insertNewSeatingDetail(fieldZoneDetailBean);   
					  
				}
				 
			}
			   
			obj.put("status",   "SUCCESS"); 
			 
			this.onLoad();
			
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
				bookingTypePrice	    = 0.00;
				status	                = null;
				fieldZoneId             = 0;
				bean 		    		= null; 
				obj 			    	= null;
				delList                 = null; 
				System.out.println("[lp_onclick_saveNewSeatingDetail][End]");
			}
		}
  
 
	
	
}
