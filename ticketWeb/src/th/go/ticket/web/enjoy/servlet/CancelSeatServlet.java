package th.go.ticket.web.enjoy.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONObject;

import th.go.ticket.app.enjoy.bean.CancelSeatBean;
import th.go.ticket.app.enjoy.bean.SeatSummaryReservationBean;
import th.go.ticket.app.enjoy.bean.UserDetailsBean;
import th.go.ticket.app.enjoy.dao.CancelSeatDao;
import th.go.ticket.app.enjoy.exception.EnjoyException;
import th.go.ticket.app.enjoy.form.CancelSeatForm;
import th.go.ticket.app.enjoy.main.Constants;
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
 			}else if(pageAction.equals("searchTeam")){
 				onSearchTeamDetail();
 			}else if(pageAction.equals("save")){
 				this.saveCancelTicket();
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
		try{
			this.form.setFieldZoneList(this.dao.getFieldZoneMaster());
			this.form.setSeasonList(this.dao.seasonList());			
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("setRefference is error");
		}finally{
			logger.info("[setRefference][End]");
		}
	}
		
	private void onSearchTeamDetail() throws EnjoyException{
		logger.info("[onSearchTeamDetail][Begin]");
		try{
			logger.info("[onSearchTeamDetail] matchId 		:: " + EnjoyUtils.nullToStr(this.request.getParameter("season")));
			
			this.form.setTeamList(this.dao.getTeamList(EnjoyUtils.nullToStr(this.request.getParameter("season"))));
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("onSearchTeamDetail is error");
		}finally{
			this.setRefference();
			logger.info("[onSearchTeamDetail][End]");
		}
		
	}
	
	private void onSearchTicketDetail() throws EnjoyException{
		logger.info("[onSearchTicketDetail][Begin]");
		
		CancelSeatBean 			bean 						= null;
		
		try{
			bean 				= new CancelSeatBean();
			bean.setMatchId(EnjoyUtils.nullToStr(this.request.getParameter("matchId")));
			bean.setSeason(EnjoyUtils.nullToStr(this.request.getParameter("season")));
			bean.setFieldZoneId(EnjoyUtils.nullToStr(this.request.getParameter("fieldZoneId")));
			bean.setTicketId(EnjoyUtils.nullToStr(this.request.getParameter("ticketId")));
			bean.setSeatingNoBegin(EnjoyUtils.nullToStr(this.request.getParameter("seatingNoBegin")));
			bean.setSeatingNoEnd(EnjoyUtils.nullToStr(this.request.getParameter("seatingNoEnd")));
			
			logger.info("[getSummaryReserv] matchId 		:: " + bean.getMatchId());
			logger.info("[getSummaryReserv] season 			:: " + bean.getSeason());
			logger.info("[getSummaryReserv] fieldZoneId 	:: " + bean.getFieldZoneId());
			logger.info("[getSummaryReserv] ticketId	 	:: " + bean.getTicketId());
			logger.info("[getSummaryReserv] seatingNoBegin 	:: " + bean.getSeatingNoBegin());
			logger.info("[getSummaryReserv] seatingNoEnd 	:: " + bean.getSeatingNoEnd());
			
			this.form.setResultList(this.dao.getSumDetailReservationList(bean));
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
	
	private void saveCancelTicket() throws Exception{
		logger.info("[cancelTicket][Begin]");
		
		SessionFactory 		   sessionFactory		= null;
		Session 			   session				= null;
		JSONObject 			   obj 			    	= null;
		String				   ticketIdList		    = null; 
		SeatSummaryReservationBean 	bean 			= null;
		
		try{	 		
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			obj 				= new JSONObject();
			
			ticketIdList		= EnjoyUtils.nullToStr(this.request.getParameter("ticketIdList"));

			session.beginTransaction();
logger.info("ticketIdList ==> " + ticketIdList);
			this.dao.cancelTicketByTicketId(session, ticketIdList);
			
			obj.put(STATUS, 			SUCCESS);
			
			// ดึงรายละเอียดขึ้นมาใหม่ New Search After Commit
			bean 				= new SeatSummaryReservationBean();
			this.form.setResultList(this.dao.getSumDetailReservationList(bean));
			
			session.getTransaction().commit();
			session.flush();			
		}catch(EnjoyException e){
			session.getTransaction().rollback();
			obj.put("status", 			"ERROR");
			obj.put("errMsg", 			e.getMessage());
			e.printStackTrace();
		}catch(Exception e){
			session.getTransaction().rollback();
			obj.put("status", 			"ERROR");
			obj.put("errMsg", 			"เกิดข้อผิดพลาดในการยกเลิกตั๋วการแข่งขัน");
			e.printStackTrace();
		}finally{ 
			session.close();
			sessionFactory		= null;
			session				= null;
			
			this.enjoyUtil.writeMSG(obj.toString());

			logger.info("[cancelTicket][End]");
		}
	}
}
