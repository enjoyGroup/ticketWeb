package th.go.ticket.web.enjoy.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import th.go.ticket.app.enjoy.bean.SeatSummaryReservationBean;
import th.go.ticket.app.enjoy.bean.UserDetailsBean;
import th.go.ticket.app.enjoy.dao.CancelSeatDao;
import th.go.ticket.app.enjoy.exception.EnjoyException;
import th.go.ticket.app.enjoy.form.CancelSeatForm;
import th.go.ticket.app.enjoy.form.UserDetailsMaintananceForm;
import th.go.ticket.app.enjoy.main.Constants;
import th.go.ticket.app.enjoy.model.Userprivilege;
import th.go.ticket.app.enjoy.utils.EnjoyLogger;
import th.go.ticket.app.enjoy.utils.EnjoyUtils;
import th.go.ticket.app.enjoy.utils.HibernateUtil;
import th.go.ticket.web.enjoy.common.EnjoyStandardSvc;
import th.go.ticket.web.enjoy.utils.EnjoyUtil;

public class CancelSeatServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(CancelSeatServlet.class);
	
    private static final String FORM_NAME = "cancelSeatForm";
    
    private EnjoyUtil               	enjoyUtil                   = null;
    private HttpServletRequest          request                     = null;
    private HttpServletResponse         response                    = null;
    private HttpSession                 session                     = null;
    private UserDetailsBean             userBean                    = null;
    private CancelSeatDao				dao							= null;
    private CancelSeatForm				form						= null;
    
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
             this.userBean           	= (UserDetailsBean) session.getAttribute("userBean");
             this.form               	= (CancelSeatForm)  session.getAttribute(FORM_NAME);
             this.dao					= new CancelSeatDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new CancelSeatForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
				request.setAttribute("target", Constants.PAGE_URL +"/CancelSeatScn.jsp");
 			}else if(pageAction.equals("searchTicketDetail")){
 				this.onSearchTicketDetail();
 			}
 			
 			session.setAttribute(FORM_NAME, this.form);
 		}catch(EnjoyException e){
 			this.form.setErrMsg(e.getMessage());
 		}catch(Exception e){
 			e.printStackTrace();
 			logger.info(e.getMessage());
 		}finally{
 			logger.info("[execute][End]");
 		}
	}
	
	private void onLoad() throws EnjoyException{
		logger.info("[onLoad][Begin]");
		
		try{
			this.setRefference();			
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("onLoad is error");
		}finally{			
			logger.info("[onLoad][End]");
		}
		
	}
	
	private void setRefference() throws EnjoyException{
		
		logger.info("[setRefference][Begin]");
		
		SessionFactory 		sessionFactory	= null;
		Session 			session			= null;
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			
			this.form.setFieldZoneList(this.dao.getFieldZoneMaster());
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("setRefference is error");
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			logger.info("[setRefference][End]");
		}
	}
	
	
	private void onSearchTicketDetail() throws EnjoyException{
		logger.info("[onSearchTicketDetail][Begin]");
		
		SeatSummaryReservationBean 			bean 						= null;
//		String								matchId						= null;
//		String								season						= null;
//		String								fieldZoneId					= null;
//		List<SeatSummaryReservationBean> 	sumDetailReservationList	= null;
//		Double								sumBookingPrices			= 0.00;
//		SeatSummaryReservationBean			headerTicketDetail			= null;
//		String								ticketIdList				= null;
//		JSONParser 							parser 						= null;
//		Object 								obj 						= null;
//		JSONObject 							jsonObject 					= null;
//		JSONArray							jsonTicketIdList			= null;
//		JSONObject							jsonTicketIdObj				= null;
//		String								ticketId					= null;
//		String								ticketIdDb					= null;
		
		try{
//			matchId 			= EnjoyUtils.nullToStr(this.request.getParameter("matchId"));
//			season 				= EnjoyUtils.nullToStr(this.request.getParameter("season"));
//			fieldZoneId 		= EnjoyUtils.nullToStr(this.request.getParameter("fieldZoneId"));
//			ticketIdList 		= EnjoyUtils.nullToStr(this.request.getParameter("ticketIdList"));
//			
//			logger.info("[getSummaryReserv] matchId 		:: " + matchId);
//			logger.info("[getSummaryReserv] season 			:: " + season);
//			logger.info("[getSummaryReserv] fieldZoneId 	:: " + fieldZoneId);
//			logger.info("[getSummaryReserv] ticketIdList 	:: " + ticketIdList);
			
			bean 				= new SeatSummaryReservationBean();
//			parser 				= new JSONParser();
//			obj 				= parser.parse(ticketIdList);
//			jsonObject 			= (JSONObject) obj;
//			jsonTicketIdList	= (JSONArray) jsonObject.get("ticketIdList");
//			
//			for(int i=0;i<jsonTicketIdList.size();i++){
//				jsonTicketIdObj = (JSONObject) jsonTicketIdList.get(i);
//				
//				ticketId = (String) jsonTicketIdObj.get("ticketId");
//				
//				if(ticketIdDb==null){
//					ticketIdDb = "'" + ticketId + "'";
//				}else{
//					ticketIdDb = ticketIdDb + ", '" + ticketId + "'";
//				}
//				
//			}
//			
//			logger.info("[getSummaryReserv] ticketIdDb 	:: " + ticketIdDb);
			
//			bean.setMatchId(matchId);
//			bean.setSeason(season);
//			bean.setFieldZoneId(fieldZoneId);
//			bean.setUserUniqueId(String.valueOf(this.userBean.getUserUniqueId()));
//			bean.setTicketId(ticketIdDb);
			
			this.form.setResultList(this.dao.getSumDetailReservationList(bean));
			
//			headerTicketDetail = this.dao.getHeaderTicketDetail(bean);
			
//			this.form.setAwayTeamNameTH(headerTicketDetail.getAwayTeamNameTH());
//			this.form.setAwayTeamNameEN(headerTicketDetail.getAwayTeamNameEN());
//			this.form.setMatchDate(headerTicketDetail.getMatchDate());
//			this.form.setMatchTime(headerTicketDetail.getMatchTime());
//			this.form.setMatchId(matchId);
//			this.form.setFieldZoneId(fieldZoneId);
//			
//			sumDetailReservationList = this.form.getResultList();
//			
//			for(SeatSummaryReservationBean beanDb:sumDetailReservationList){
//				sumBookingPrices = sumBookingPrices + EnjoyUtils.parseDouble(beanDb.getBookingPrices());
//				
//				beanDb.setBookingPrices(EnjoyUtils.convertFloatToDisplay(beanDb.getBookingPrices(), 2));
//			}
//			
//			this.form.setSumBookingPrices(EnjoyUtils.convertFloatToDisplay(String.valueOf(sumBookingPrices), 2));
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("onSearchTicketDetail is error");
		}finally{
			this.setRefference();
			logger.info("[onSearchTicketDetail][End]");
		}
		
	}
	
}
