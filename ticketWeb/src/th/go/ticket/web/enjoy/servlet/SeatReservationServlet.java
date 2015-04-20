package th.go.ticket.web.enjoy.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import th.go.ticket.app.enjoy.bean.UserDetailsBean;
import th.go.ticket.app.enjoy.dao.SeatReservationDao;
import th.go.ticket.app.enjoy.exception.EnjoyException;
import th.go.ticket.app.enjoy.form.SeatReservationForm;
import th.go.ticket.app.enjoy.main.Constants;
import th.go.ticket.app.enjoy.utils.EnjoyLogger;
import th.go.ticket.app.enjoy.utils.EnjoyUtils;
import th.go.ticket.web.enjoy.common.EnjoyStandardSvc;
import th.go.ticket.web.enjoy.utils.EnjoyUtil;

public class SeatReservationServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(SeatReservationServlet.class);
	
    private static final String FORM_NAME = "seatReservationForm";
    
    private EnjoyUtil               	easUtil                     = null;
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
 			 this.easUtil 				= new EnjoyUtil(request, response);
 			 this.request            	= request;
             this.response           	= response;
             this.session            	= request.getSession(false);
             this.userBean           	= (UserDetailsBean)session.getAttribute("userBean");
             this.form               	= (SeatReservationForm)session.getAttribute(FORM_NAME);
             this.dao					= new SeatReservationDao();
 			
             logger.info("[execute][Begin] : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new SeatReservationForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
// 				this.onLoad();
 				request.setAttribute("target", Constants.PAGE_URL +"/SeatReservationScn.jsp");
 			}if( pageAction.equals("getZoneDetail")){
 				this.getZoneDetail();
 				request.setAttribute("target", Constants.PAGE_URL +"/SeatReservationScn.jsp");
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
		
		String							fieldZoneId			= null;
		String							fieldZoneName		= null;
		String							matchId				= null;
		String							season				= null;
		String							awayTeamNameTH		= null;
		
		try{
			
			fieldZoneId 			= EnjoyUtils.nullToStr(this.request.getParameter("fieldZoneId"));
			fieldZoneName 			= EnjoyUtils.nullToStr(this.request.getParameter("fieldZoneName"));
			matchId 				= EnjoyUtils.nullToStr(this.request.getParameter("matchId"));
			season 					= EnjoyUtils.nullToStr(this.request.getParameter("season"));
			awayTeamNameTH 			= EnjoyUtils.nullToStr(this.request.getParameter("awayTeamNameTH"));
			
			logger.info("[getZoneDetail] fieldZoneId 		:: " + fieldZoneId);
			logger.info("[getZoneDetail] fieldZoneName 		:: " + fieldZoneName);
			logger.info("[getZoneDetail] matchId 			:: " + matchId);
			logger.info("[getZoneDetail] season 			:: " + season);
			logger.info("[getZoneDetail] awayTeamNameTH 	:: " + awayTeamNameTH);
			
			this.form.setFieldZoneId(fieldZoneId);
			this.form.setFieldZoneName(fieldZoneName);
			this.form.setMatchId(matchId);
			this.form.setSeason(season);
			this.form.setAwayTeamNameTH(awayTeamNameTH);
			
			//Set ประเภทตั๋ว
			this.form.setTicketTypeList(this.dao.getTicketTypeList(fieldZoneId));
			
			//เลือกที่นั่งทั้งหมดของ Zone นี้
			this.form.setSeatReservationBean(this.dao.getSeatForThisZone(fieldZoneId));
			
		}catch(Exception e){
			throw new EnjoyException("getZoneDetail :: " + e.getMessage());
		}finally{
			logger.info("[getZoneDetail][End]");
		}
		
	}
	
	
	
	
	
	
	
	
	
	
}
