package th.go.ticket.web.enjoy.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import th.go.ticket.app.enjoy.bean.SeatReservationBean;
import th.go.ticket.app.enjoy.bean.UserDetailsBean;
import th.go.ticket.app.enjoy.dao.SeatReservationDao;
import th.go.ticket.app.enjoy.exception.EnjoyException;
import th.go.ticket.app.enjoy.form.SeatReservationForm;
import th.go.ticket.app.enjoy.main.Constants;
import th.go.ticket.app.enjoy.model.Genseqticketid;
import th.go.ticket.app.enjoy.model.GenseqticketidPK;
import th.go.ticket.app.enjoy.utils.EnjoyLogger;
import th.go.ticket.app.enjoy.utils.EnjoyUtils;
import th.go.ticket.app.enjoy.utils.HibernateUtil;
import th.go.ticket.web.enjoy.common.EnjoyStandardSvc;
import th.go.ticket.web.enjoy.utils.EnjoyUtil;

public class SeatReservationServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(SeatReservationServlet.class);
	
    private static final String FORM_NAME = "seatReservationForm";
    
    private EnjoyUtil               	enjoyUtil                   = null;
    private HttpServletRequest          request                     = null;
    private HttpServletResponse         response                    = null;
    private HttpSession                 session                     = null;
    private UserDetailsBean             userBean                    = null;
    private SeatReservationDao			dao							= null;
    private SeatReservationForm			form						= null;
    
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
             this.form               	= (SeatReservationForm)session.getAttribute(FORM_NAME);
             this.dao					= new SeatReservationDao();
 			
             logger.info("[execute][Begin] : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new") || pageAction.equals("getZoneDetail")) this.form = new SeatReservationForm();
 			
 			this.form.setUserUniqueId(String.valueOf(this.userBean.getUserUniqueId()));
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				request.setAttribute("target", Constants.PAGE_URL +"/SeatReservationScn.jsp");
 			}else if( pageAction.equals("getZoneDetail")){
 				this.getZoneDetail();
 				request.setAttribute("target", Constants.PAGE_URL +"/SeatReservationScn.jsp");
 			}else if( pageAction.equals("getSeatBooking")){
 				this.getSeatBooking();
 			}else if( pageAction.equals("booking")){
 				this.booking();
 			}else if( pageAction.equals("goBack")){
 				this.clearReservationForUser();
 			}else if( pageAction.equals("forStandZone")){
 				this.forStandZone();
 			}else if( pageAction.equals("goNext")){
 				this.goNext();
 			}
 			
 			session.setAttribute(FORM_NAME, this.form);
 			
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
	
	private void getZoneDetail() throws EnjoyException{
		logger.info("[getZoneDetail][Begin]");
		
		String							fieldZoneId				= null;
		String							matchId					= null;
		SeatReservationBean				seatReservationBean 	= null;
		String[]						rowNames				= null;
		String							rowName					= null;
		String 							rows					= null;
		String 							seating					= null;
		int								rowNameSize				= 0;
		Map								rowsMap					= null;
		List<SeatReservationBean>		seatPerRowList			= null;
		SeatReservationBean				seatPerRowBean			= null;
		String							seatingNo				= null;
		int								startSeatingNo			= 0;
		int								seatIndex				= 0;
		List<SeatReservationBean> 		ticketTypeList			= null;
		String							bookingTypeId			= null;
		String							bookingTypeName			= null;
		boolean							firstTicketType			= true;
		Map<String, Integer>			mapBookingType			= null;
		SeatReservationBean 			headerTicketReservBean	= null;
		
		try{
			
			fieldZoneId 			= EnjoyUtils.nullToStr(this.request.getParameter("fieldZoneId"));
			matchId 				= EnjoyUtils.nullToStr(this.request.getParameter("matchId"));
			rowsMap					= this.form.getRowsMap();
			mapBookingType			= this.form.getMapBookingType();
			
			logger.info("[getZoneDetail] fieldZoneId 				:: " + fieldZoneId);
			logger.info("[getZoneDetail] matchId 					:: " + matchId);
			
			headerTicketReservBean = this.dao.getHeaderTicketReservation(matchId, fieldZoneId);
			
			this.form.setFieldZoneId(fieldZoneId);
			this.form.setFieldZoneName(headerTicketReservBean.getFieldZoneName());
			this.form.setFieldZoneNameTicket(headerTicketReservBean.getFieldZoneNameTicket());
			this.form.setMatchId(matchId);
			this.form.setSeason(headerTicketReservBean.getSeason());
			this.form.setAwayTeamNameTH(headerTicketReservBean.getAwayTeamNameTH());
			
			//ลบรายการที่ผู้ใช้นี้เคยทำแล้วสถานะเป็น P ทั้งหมด
			this.clearReservationForUser();
			
			//Set ประเภทตั๋ว
			this.form.setTicketTypeList(this.dao.getTicketTypeList(fieldZoneId));
			
			ticketTypeList = this.form.getTicketTypeList();
			
			if(ticketTypeList!=null){
				for(SeatReservationBean ticketTypeBean:ticketTypeList){
					bookingTypeId 	= ticketTypeBean.getBookingTypeId();
					
					if(firstTicketType==true){
						firstTicketType = false;
						bookingTypeName = ticketTypeBean.getBookingTypeName();
						
						this.form.setBookingTypeId	(bookingTypeId);
						this.form.setBookingTypeName(bookingTypeName);
						
						ticketTypeBean.setClassTxt("txt-select");
					}else{
						ticketTypeBean.setClassBtn("btn-unSelect");
						ticketTypeBean.setClassTxt("txt-unSelect");
					}
					
					mapBookingType.put(bookingTypeId, 0);
					
				}
			}
			
			//เลือกที่นั่งทั้งหมดของ Zone นี้
			seatReservationBean = this.dao.getSeatForThisZone(fieldZoneId);
			
			if(seatReservationBean!=null){
				
				rows 	= EnjoyUtils.nullToStr(seatReservationBean.getRows());
				seating = EnjoyUtils.nullToStr(seatReservationBean.getSeating());
				
				//เช็คว่าสามารถเลือกที่นั่งได้มั้ย
				if((!rows.equals("") && !rows.equals("0")) && (!seating.equals("") && !seating.equals("0"))){
					if(seatReservationBean.getRowName()!=null && !seatReservationBean.getRowName().equals("")){
						this.form.setFlagAlterSeat("1");//เลือกที่นั่งได้
						
						rowNames 	= seatReservationBean.getRowName().split(",");
						rowNameSize	= rowNames.length;
						
						for(int i=(rowNameSize-1);i>=0;i--){
							
							seatPerRowList 		= new ArrayList<SeatReservationBean>();
							startSeatingNo		= Integer.parseInt(seatReservationBean.getStartSeatingNo());
							
							for(int j=1;j<=Integer.parseInt(seating);j++){
								rowName			= rowNames[i];
								seatingNo 		= this.genSeatNo(rowName, startSeatingNo);
								seatPerRowBean	= new SeatReservationBean();
								
								seatPerRowBean.setSeatingNo(seatingNo);
								seatPerRowBean.setNumSeat(String.valueOf(startSeatingNo));
								seatPerRowBean.setTicketStatus(SeatReservationForm.FREE);
								seatPerRowBean.setSeatIndex(String.valueOf(seatIndex));
								seatPerRowBean.setClassSeat(this.getClassSeat(SeatReservationForm.FREE, ""));
								
								seatPerRowList.add(seatPerRowBean);
								startSeatingNo++;
								seatIndex++;
							}
							
							rowsMap.put(rowName, seatPerRowList);
							this.form.getSeatNameList().add(rowName);
							
						}
						
					}else{
						throw new EnjoyException("เกิดข้อผิดพลาดในการสร้างแถว");
					}
				}
			}else{
				throw new EnjoyException("เกิดข้อผิดพลาดในการดึงข้อมูลสำหรับการจองที่นั่ง");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new EnjoyException("getZoneDetail :: " + e.getMessage());
		}finally{
			logger.info("[getZoneDetail][End]");
		}
		
	}
	
	private String genSeatNo(String rowName, int seatingNo) throws EnjoyException{
//		logger.info("[genSeatNo][Begin]");
		
		try{
			return rowName + "-" + String.format(SeatReservationForm.FILL_ZERO, seatingNo);
		}catch(Exception e){
			throw new EnjoyException("genSeatNo :: " + e.getMessage());
		}finally{
//			logger.info("[genSeatNo][End]");
		}
		
	}
	
	private String getClassSeat(String ticketStatus, String ticketUserUniqueId) throws EnjoyException{
		
		String classSeat = "seat-col seat-col-free seat-blue round";
		
		try{
			if(ticketStatus.equals(SeatReservationForm.ACTIVE)){
				classSeat = "seat-col seat-col-occupy seat- round";
			}else if(ticketStatus.equals(SeatReservationForm.PENDING)){
				
				if(ticketUserUniqueId.equals(String.valueOf(this.userBean.getUserUniqueId()))){
					classSeat = "seat-col seat-col-bookking seat- round";
				}else{
					classSeat = "seat-col seat-col-bookking-oth seat- round";
				}
			}
			
		}catch(Exception e){
			throw new EnjoyException("genSeatNo :: " + e.getMessage());
		}finally{
			logger.info("[genSeatNo][End]");
		}
		
		return classSeat;
		
	}
	
	private void getSeatBooking() throws EnjoyException{
		
		logger.info("[getSeatBooking][Begin]");
		
		String 						matchId 			= null;
		String 						fieldZoneId 		= null;
		List<SeatReservationBean> 	seatBookingList 	= null;
		JSONObject 					obj 				= null;
		JSONArray 					detailJSONArray 	= null;
		JSONObject 					objDetail 			= null;
		String						season				= null;
		
		try{
			obj 				= new JSONObject();
			detailJSONArray 	= new JSONArray();
			matchId 			= EnjoyUtils.nullToStr(this.request.getParameter("matchId"));
			fieldZoneId 		= EnjoyUtils.nullToStr(this.request.getParameter("fieldZoneId"));
			season 				= EnjoyUtils.nullToStr(this.request.getParameter("season"));
			seatBookingList		= this.dao.getSeatBookingList(matchId, fieldZoneId, season);
			
			obj.put(STATUS, 			SUCCESS);
			
			if(seatBookingList!=null){
				
				obj.put("sizeList", 	seatBookingList.size());
				
				for(SeatReservationBean bean:seatBookingList){
					objDetail 		= new JSONObject();
					
					objDetail.put("ticketId"			,bean.getTicketId());
					objDetail.put("seatingNo"			,bean.getSeatingNo());
					objDetail.put("ticketUserUniqueId"	,bean.getTicketUserUniqueId());
					objDetail.put("ticketStatus"		,bean.getTicketStatus());
					objDetail.put("seatBookingTypeId"	,bean.getSeatBookingTypeId());
					objDetail.put("classSeat"			,this.getClassSeat(bean.getTicketStatus(), bean.getTicketUserUniqueId()));
					
					detailJSONArray.add(objDetail);
				}
			}else{
				obj.put("sizeList", 		"0");
			}
			
			obj.put("detail", 			detailJSONArray);
			
		}catch(EnjoyException e){
			obj.put(STATUS, 			ERROR);
			obj.put(ERR_MSG, 			e.getMessage());
			throw new EnjoyException("getSeatBooking :: " + e.getMessage());
		}catch(Exception e){
			obj.put(STATUS, 			ERROR);
			obj.put(ERR_MSG, 			e.getMessage());
			throw new EnjoyException("getSeatBooking :: " + e.getMessage());
		}finally{
			this.enjoyUtil.writeMSG(obj.toString());
			logger.info("[getSeatBooking][End]");
		}
	}
	
	private void booking() throws EnjoyException{
		
		logger.info("[booking][Begin]");
		
		String 						ticketId 			= null;
		String 						seatingNo 			= null;
		String 						matchId 			= null;
		String						season				= null;
		String 						fieldZoneId 		= null;
		String 						bookingTypeId 		= null;
		String 						userUniqueId 		= null;
		String						ticketStatus		= null;
		JSONObject 					obj 				= null;
		SessionFactory 				sessionFactory		= null;
		Session 					session				= null;
		SeatReservationBean			bean				= null;
		int							checkSeatNo			= 1;
		
		try{
			obj 				= new JSONObject();
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			ticketId 			= EnjoyUtils.nullToStr(this.request.getParameter("ticketId"));
			seatingNo 			= EnjoyUtils.nullToStr(this.request.getParameter("seatingNo"));
			matchId 			= EnjoyUtils.nullToStr(this.request.getParameter("matchId"));
			season 				= EnjoyUtils.nullToStr(this.request.getParameter("season"));
			fieldZoneId 		= EnjoyUtils.nullToStr(this.request.getParameter("fieldZoneId"));
			bookingTypeId 		= EnjoyUtils.nullToStr(this.request.getParameter("bookingTypeId"));
			userUniqueId 		= EnjoyUtils.nullToStr(this.request.getParameter("userUniqueId"));
			ticketStatus 		= EnjoyUtils.nullToStr(this.request.getParameter("ticketStatus"));
			bean				= new SeatReservationBean();
			
			logger.info("[booking] ticketId 		:: " + ticketId);
			logger.info("[booking] seatingNo 		:: " + seatingNo);
			logger.info("[booking] matchId 			:: " + matchId);
			logger.info("[booking] season 			:: " + season);
			logger.info("[booking] fieldZoneId 		:: " + fieldZoneId);
			logger.info("[booking] bookingTypeId 	:: " + bookingTypeId);
			logger.info("[booking] userUniqueId 	:: " + userUniqueId);
			logger.info("[booking] ticketStatus 	:: " + ticketStatus);
			
			session.beginTransaction();
			
			//ticketStatus เป็นว่างแสดงว่าบันทึก record ใหม่
			if(ticketStatus.equals("")){
				bean.setTicketId(genTicketId(season, matchId, fieldZoneId, bookingTypeId));//ใช้รอสูตรการ gen TicketId จากพี่เอ
				bean.setSeatingNo(seatingNo);
				bean.setMatchId(matchId);
				bean.setSeason(season);
				bean.setFieldZoneId(fieldZoneId);
				bean.setBookingTypeId(bookingTypeId);
				bean.setUserUniqueId(userUniqueId);
				bean.setTicketStatus(SeatReservationForm.PENDING);
				
				checkSeatNo = this.dao.checkSeatNo(bean);
				
				if(checkSeatNo > 0){
					throw new EnjoyException("ไม่สามารถทำรายการได้เนื่องจากที่นั่งนี้มีคนจองแล้ว");
				}else{
					this.dao.insertTicketorder(session, bean);
				}
				
			}else{
				bean.setSeatingNo(seatingNo);
				bean.setUserUniqueId(userUniqueId);
				bean.setTicketStatus(ticketStatus);
				
				this.dao.cancelReservationByUser(session, bean);
			}
			
			
			session.getTransaction().commit();
			
			obj.put(STATUS, 			SUCCESS);
			
			
			
		}catch(EnjoyException e){
			session.getTransaction().rollback();
			obj.put(STATUS, 			ERROR);
			obj.put(ERR_MSG, 			e.getMessage());
		}catch(Exception e){
			session.getTransaction().rollback();
			obj.put(STATUS, 			ERROR);
			obj.put(ERR_MSG, 			e.getMessage());
			throw new EnjoyException("getSeatBooking :: " + e.getMessage());
		}finally{
			
			session.flush();
			session.clear();
			session.close();
			
			this.enjoyUtil.writeMSG(obj.toString());
			logger.info("[booking][End]");
		}
	}
	
	private void clearReservationForUser() throws EnjoyException{
		
		logger.info("[clearReservationForUser][Begin]");
		
		String 						userUniqueId 		= null;
		JSONObject 					obj 				= null;
		SessionFactory 				sessionFactory		= null;
		Session 					session				= null;
		SeatReservationBean			bean				= null;
		
		try{
			obj 				= new JSONObject();
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			userUniqueId 		= String.valueOf(this.userBean.getUserUniqueId());
			bean				= new SeatReservationBean();
			
			logger.info("[clearReservationForUser] userUniqueId 	:: " + userUniqueId);
			
			session.beginTransaction();
			
			bean.setUserUniqueId(userUniqueId);
			this.dao.clearReservationForUser(session, bean);
			
			session.getTransaction().commit();
			
			obj.put(STATUS, 			SUCCESS);
			
		}catch(Exception e){
			session.getTransaction().rollback();
			obj.put(STATUS, 			ERROR);
			obj.put(ERR_MSG, 			e.getMessage());
			throw new EnjoyException("clearReservationForUser :: " + e.getMessage());
		}finally{
			
			session.flush();
			session.clear();
			session.close();
			
			this.enjoyUtil.writeMSG(obj.toString());
			logger.info("[clearReservationForUser][End]");
		}
	}
	
	private void forStandZone() throws EnjoyException{
		logger.info("[forStandZone][Begin]");
		
		JSONObject 					obj 				= null;
		Map<String, Integer>		mapBookingType		= null;
		int							numTicketType		= 0;
		String						seatBookingTypeId	= null;
		
		try{
			obj 				= new JSONObject();
			mapBookingType		= this.form.getMapBookingType();
			seatBookingTypeId 	= EnjoyUtils.nullToStr(this.request.getParameter("seatBookingTypeId"));
			numTicketType 		= EnjoyUtils.parseInt(this.request.getParameter("numTicketType"));
			
			logger.info("[forStandZone] seatBookingTypeId 	:: " + seatBookingTypeId);
			logger.info("[forStandZone] numTicketType 		:: " + numTicketType);
			
			if(mapBookingType.containsKey(seatBookingTypeId)){
				mapBookingType.put(seatBookingTypeId, numTicketType);
			}else{
				throw new EnjoyException("ไม่พบประเภทตั๋วที่ระบุ");
			}
			
			obj.put(STATUS, 			SUCCESS);
		}catch(EnjoyException e){
			obj.put(STATUS, 			ERROR);
			obj.put(ERR_MSG, 			e.getMessage());
		}catch(Exception e){
			obj.put(STATUS, 			ERROR);
			obj.put(ERR_MSG, 			e.getMessage());
			throw new EnjoyException("forStandZone :: " + e.getMessage());
		}finally{
			this.enjoyUtil.writeMSG(obj.toString());
			logger.info("[forStandZone][End]");
		}
		
	}
	
	private void goNext() throws EnjoyException{
		logger.info("[goNext][Begin]");
		
		JSONObject 					obj 				= null;
		JSONArray 					detailJSONArray 	= null;
		JSONObject 					objDetail 			= null;
		Map<String, Integer>		mapBookingType		= null;
		int							numTicketType		= 0;
		String						flagAlterSeat		= null;
		SeatReservationBean			bean				= null;
		SessionFactory 				sessionFactory		= null;
		Session 					session				= null;
		String						matchId				= null;
		String						fieldZoneId			= null;
		String						ticketId			= null;
		List<String>				ticketIdList		= null;
		String						season				= null;
		
		try{
			obj 				= new JSONObject();
			detailJSONArray 	= new JSONArray();
			mapBookingType		= this.form.getMapBookingType();
			flagAlterSeat		= this.form.getFlagAlterSeat();
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			matchId 			= EnjoyUtils.nullToStr(this.request.getParameter("matchId"));
			season 				= EnjoyUtils.nullToStr(this.request.getParameter("season"));
			fieldZoneId 		= EnjoyUtils.nullToStr(this.request.getParameter("fieldZoneId"));
			
			session.beginTransaction();
			
			//Case ไม่สามารถเลือกที่นั่งได้
			if(flagAlterSeat.equals("0")){
				
				for (Object key : mapBookingType.keySet()) {
					
					numTicketType = (int) mapBookingType.get(key);
					
					for(int i=0;i<numTicketType;i++){
						
						bean 		= new SeatReservationBean();
						objDetail 	= new JSONObject();
						ticketId	= genTicketId(season, matchId, fieldZoneId, key.toString());//EnjoyUtil.genPassword(17);
						
						bean.setTicketId(ticketId);//ใช้รอสูตรการ gen TicketId จากพี่เอ
						bean.setSeatingNo("");
						bean.setMatchId(matchId);
						bean.setSeason(season);
						bean.setFieldZoneId(fieldZoneId);
						bean.setBookingTypeId(key.toString());
						bean.setUserUniqueId(String.valueOf(this.userBean.getUserUniqueId()));
						bean.setTicketStatus(SeatReservationForm.ACTIVE);
						
						objDetail.put("ticketId"			,ticketId);
						detailJSONArray.add(objDetail);
						
						this.dao.insertTicketorder(session, bean);
					}
				}
				
			}else{
				ticketIdList = this.dao.getAllTicketIdIsPending(this.userBean.getUserUniqueId());
				
				logger.info("[goNext] ticketIdList :: " + ticketIdList);
				
				for(String ticketIdDb:ticketIdList){
					objDetail 	= new JSONObject();
					
					objDetail.put("ticketId"			,ticketIdDb);
					detailJSONArray.add(objDetail);
				}
				
				
				this.dao.updateStatusPendingToActive(session, this.userBean.getUserUniqueId());
			}
			
			session.getTransaction().commit();
			
			obj.put(STATUS, 			SUCCESS);
			obj.put("ticketIdList", 	detailJSONArray);
		}catch(EnjoyException e){
			session.getTransaction().rollback();
			obj.put(STATUS, 			ERROR);
			obj.put(ERR_MSG, 			e.getMessage());
			e.printStackTrace();
		}catch(Exception e){
			session.getTransaction().rollback();
			obj.put(STATUS, 			ERROR);
			obj.put(ERR_MSG, 			e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("goNext :: " + e.getMessage());
		}finally{
			
			session.flush();
			session.clear();
			session.close();
			
			this.enjoyUtil.writeMSG(obj.toString());
			logger.info("[goNext][End]");
		}
		
	}
	
	private String genTicketId(String season, String matchId, String fieldZoneId, String bookingTypeId) throws Exception{
		logger.info("[genTicketId][Begin]");
		
		String 						currDate 			= null;
		double 						a					= 0;
		double 						b					= 0;
		double 						c					= 0;
		double 						d					= 0;
		Integer 					ticketSeq			= null;
		SessionFactory 				sessionFactory		= null;
		Session 					session				= null;
		Genseqticketid				genseqticketid		= null;
		GenseqticketidPK			genseqticketidPK	= null;
		double 						ab					= 0;
		double 						cd					= 0;
		int 						verifyNum			= 0;
		String						ticketId			= null;
		Integer						bookingPrices		= 0;
		
		try{
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			currDate 			= EnjoyUtils.currDateThai().substring(2, 4);
			bookingPrices		= this.dao.getBookingPrices(fieldZoneId, bookingTypeId);
			a					= Math.pow(EnjoyUtils.parseDouble(currDate) - EnjoyUtils.parseDouble(fieldZoneId) ,2) % 1000;
			b					= bookingPrices % 1000;
			c					= (EnjoyUtils.parseDouble(matchId) * 5) % 1000;
			ticketSeq			= this.dao.getTicketSeq(season, matchId);
			genseqticketidPK	= new GenseqticketidPK();
			genseqticketid		= new Genseqticketid();
			
			session.beginTransaction();
			
			if(ticketSeq==null){
				ticketSeq = 0;
				
				genseqticketidPK.setSeason(EnjoyUtils.parseInt(season));
				genseqticketidPK.setMatchId(EnjoyUtils.parseInt(matchId));
				
				genseqticketid.setId(genseqticketidPK);
				genseqticketid.setTicketSeq(ticketSeq);
				
				this.dao.insertGenseqticketid(session, genseqticketid);
			}else{
				ticketSeq = ticketSeq + 1;
				
				this.dao.updateGenseqticketid(session, EnjoyUtils.parseInt(season), EnjoyUtils.parseInt(matchId), ticketSeq);
			}
			
			d 			= (ticketSeq*5)%1000;
			ab 			= (a + b)%100;
			cd 			= (c + d)%100;
			verifyNum 	= ab > cd?(int) ab:(int) cd;
			
//			logger.info("[genTicketId] a 		:: " + a);
//			logger.info("[genTicketId] b 		:: " + b);
//			logger.info("[genTicketId] c 		:: " + c);
//			logger.info("[genTicketId] d 		:: " + d);
//			logger.info("[genTicketId] ab 		:: " + ab);
//			logger.info("[genTicketId] cd 		:: " + cd);
//			
//			logger.info("[genTicketId] verifyNum 		:: " + verifyNum);
//			logger.info("[genTicketId] fieldZoneId 		:: " + fieldZoneId);
//			logger.info("[genTicketId] bookingPrices 	:: " + bookingPrices);
//			logger.info("[genTicketId] ticketSeq 		:: " + ticketSeq);
			
			ticketId	= currDate 
						+ String.format(SeatReservationForm.FILL_VERRIFY_NUM		, verifyNum) 
						+ String.format(SeatReservationForm.FILL_ZONE_ID			, EnjoyUtils.parseInt(fieldZoneId)) 
						+ String.format(SeatReservationForm.FILL_ZERO_BOOK_PRICE	, bookingPrices)
						+ String.format(SeatReservationForm.FILL_ZERO_TICKET_ID		, ticketSeq);
			
			logger.info("[genTicketId] ticketId :: " + ticketId);
			
			session.getTransaction().commit();
			
		}catch(Exception e){
			session.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}finally{
			session.flush();
			session.clear();
			session.close();
			logger.info("[genTicketId][End]");
		}
		
		return ticketId;
	}
	
	
}
