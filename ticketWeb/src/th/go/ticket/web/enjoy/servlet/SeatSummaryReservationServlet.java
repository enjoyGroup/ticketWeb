package th.go.ticket.web.enjoy.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import th.go.ticket.app.enjoy.bean.SeatSummaryReservationBean;
import th.go.ticket.app.enjoy.bean.UserDetailsBean;
import th.go.ticket.app.enjoy.dao.SeatSummaryReservationDao;
import th.go.ticket.app.enjoy.exception.EnjoyException;
import th.go.ticket.app.enjoy.form.SeatSummaryReservationForm;
import th.go.ticket.app.enjoy.main.Constants;
import th.go.ticket.app.enjoy.utils.EnjoyLogger;
import th.go.ticket.app.enjoy.utils.EnjoyUtils;
import th.go.ticket.web.enjoy.common.EnjoyStandardSvc;
import th.go.ticket.web.enjoy.utils.EnjoyUtil;

public class SeatSummaryReservationServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(SeatSummaryReservationServlet.class);
	
    private static final String FORM_NAME = "seatSummaryReservationForm";
    
    private EnjoyUtil               	enjoyUtil                   = null;
    private HttpServletRequest          request                     = null;
    private HttpServletResponse         response                    = null;
    private HttpSession                 session                     = null;
    private UserDetailsBean             userBean                    = null;
    private SeatSummaryReservationDao	dao							= null;
    private SeatSummaryReservationForm	form						= null;
    
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
             this.form               	= (SeatSummaryReservationForm)session.getAttribute(FORM_NAME);
             this.dao					= new SeatSummaryReservationDao();
 			
             logger.info("[execute][Begin] : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new SeatSummaryReservationForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				request.setAttribute("target", Constants.PAGE_URL +"/SeatSummaryReservationScn.jsp");
 			}else if(pageAction.equals("getSummaryReserv") ){
 				this.getSummaryReserv();
 				request.setAttribute("target", Constants.PAGE_URL +"/SeatSummaryReservationScn.jsp");
 			}else if(pageAction.equals("print") ){
 				this.print();
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
	
	private void getSummaryReserv() throws EnjoyException{
		
		logger.info("[getSummaryReserv][Begin]");
		
		SeatSummaryReservationBean 			bean 						= null;
		String								matchId						= null;
		String								season						= null;
		String								fieldZoneId					= null;
		List<SeatSummaryReservationBean> 	sumDetailReservationList	= null;
		Double								sumBookingPrices			= 0.00;
		SeatSummaryReservationBean			headerTicketDetail			= null;
		String								ticketIdList				= null;
		JSONParser 							parser 						= null;
		Object 								obj 						= null;
		JSONObject 							jsonObject 					= null;
		JSONArray							jsonTicketIdList			= null;
		JSONObject							jsonTicketIdObj				= null;
		String								ticketId					= null;
		String								ticketIdDb					= null;
		
		try{
			matchId 			= EnjoyUtils.nullToStr(this.request.getParameter("matchId"));
			season 				= EnjoyUtils.nullToStr(this.request.getParameter("season"));
			fieldZoneId 		= EnjoyUtils.nullToStr(this.request.getParameter("fieldZoneId"));
			ticketIdList 		= EnjoyUtils.nullToStr(this.request.getParameter("ticketIdList"));
			
			logger.info("[getSummaryReserv] matchId 		:: " + matchId);
			logger.info("[getSummaryReserv] season 			:: " + season);
			logger.info("[getSummaryReserv] fieldZoneId 	:: " + fieldZoneId);
			logger.info("[getSummaryReserv] ticketIdList 	:: " + ticketIdList);
			
			bean 				= new SeatSummaryReservationBean();
			parser 				= new JSONParser();
			obj 				= parser.parse(ticketIdList);
			jsonObject 			= (JSONObject) obj;
			jsonTicketIdList	= (JSONArray) jsonObject.get("ticketIdList");
			
			for(int i=0;i<jsonTicketIdList.size();i++){
				jsonTicketIdObj = (JSONObject) jsonTicketIdList.get(i);
				
				ticketId = (String) jsonTicketIdObj.get("ticketId");
				
				if(ticketIdDb==null){
					ticketIdDb = "'" + ticketId + "'";
				}else{
					ticketIdDb = ticketIdDb + ", '" + ticketId + "'";
				}
				
			}
			
			logger.info("[getSummaryReserv] ticketIdDb 	:: " + ticketIdDb);
			
			bean.setMatchId(matchId);
			bean.setSeason(season);
			bean.setFieldZoneId(fieldZoneId);
			bean.setUserUniqueId(String.valueOf(this.userBean.getUserUniqueId()));
			bean.setTicketId(ticketIdDb);
			
			this.form.setResultList(this.dao.getSumDetailReservationList(bean));
			
			headerTicketDetail = this.dao.getHeaderTicketDetail(bean);
			
			this.form.setAwayTeamNameTH(headerTicketDetail.getAwayTeamNameTH());
			this.form.setMatchDate(headerTicketDetail.getMatchDate());
			this.form.setMatchTime(headerTicketDetail.getMatchTime());
			this.form.setMatchId(matchId);
			this.form.setFieldZoneId(fieldZoneId);
			
			sumDetailReservationList = this.form.getResultList();
			
			for(SeatSummaryReservationBean beanDb:sumDetailReservationList){
				sumBookingPrices = sumBookingPrices + EnjoyUtils.parseDouble(beanDb.getBookingPrices());
				
				beanDb.setBookingPrices(EnjoyUtils.convertFloatToDisplay(beanDb.getBookingPrices(), 2));
			}
			
			this.form.setSumBookingPrices(EnjoyUtils.convertFloatToDisplay(String.valueOf(sumBookingPrices), 2));
			
		}catch(Exception e){
			e.printStackTrace();
			throw new EnjoyException("getSummaryReserv :: " + e.getMessage());
		}finally{
			logger.info("[getSummaryReserv][End]");
		}
		
	}
	
	private void print() throws EnjoyException{
		logger.info("[print][Begin]");
		
		JSONObject 							obj 						= null;
		JSONArray 							detailJSONArray 			= null;
		JSONObject 							objDetail 					= null;
//		SessionFactory 						sessionFactory				= null;
//		Session 							session						= null;
		List<SeatSummaryReservationBean> 	sumDetailReservationList	= null;
		String								seatRow						= null;
		
		try{
			obj 						= new JSONObject();
			detailJSONArray 			= new JSONArray();
//			sessionFactory 				= HibernateUtil.getSessionFactory();
//			session 					= sessionFactory.openSession();
			sumDetailReservationList	= this.form.getResultList();
			
//			session.beginTransaction();
			
			for(SeatSummaryReservationBean bean:sumDetailReservationList){
				
//				bean.setMatchId(this.form.getMatchId());
//				bean.setFieldZoneId(this.form.getFieldZoneId());
//				
//				this.dao.updateStatusPendingToActive(session, bean);
				
				objDetail 		= new JSONObject();
				seatRow			= bean.getSeatingNo().equals("-")?"-":bean.getSeatingNo().split("-")[0];
				
				objDetail.put("ticketId"			,bean.getTicketId());			//Barcode
				objDetail.put("seatingNo"			,bean.getSeatingNo());			//ที่นั่ง
				objDetail.put("awayTeamNameTH"		,this.form.getAwayTeamNameTH());//แข่งขับ (ไทย)
				objDetail.put("awayTeamNameEN"		,this.form.getAwayTeamNameEN());//แข่งขับ (English)
				objDetail.put("fieldZoneName"		,bean.getFieldZoneName());		//อัฒจันทร์
				objDetail.put("seatRow"				,seatRow);						//แถว
				objDetail.put("matchTime"			,this.form.getMatchTime());		//เวลา
				objDetail.put("matchDate"			,this.form.getMatchDate());		//วันที่
				objDetail.put("bookingTypeName"		,bean.getBookingTypeName());	//บัตร
				objDetail.put("bookingPrices"		,bean.getBookingPrices());		//ราคา
				
				detailJSONArray.add(objDetail);
				
			}
			
//			session.getTransaction().commit();
			
			obj.put(STATUS, 			SUCCESS);
			obj.put("detailList", 			detailJSONArray);
			
		}catch(Exception e){
//			session.getTransaction().rollback();
			obj.put(STATUS, 			ERROR);
			obj.put(ERR_MSG, 			e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("print :: " + e.getMessage());
		}finally{
			
//			session.flush();
//			session.clear();
//			session.close();
			
			this.enjoyUtil.writeMSG(obj.toString());
			logger.info("[print] obj.toString() :: " + obj.toString());
			logger.info("[print][End]");
		}
		
	}
	
}










