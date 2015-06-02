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

import th.go.ticket.app.enjoy.bean.CancelSeatBean;
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
 			}else if(pageAction.equals("getAwayTeamNameTH")){
 				this.getAwayTeamNameTH();
 			}else if(pageAction.equals("save")){
 				this.saveCancelTicket();
 			}else if(pageAction.equals("getSeason")){
 				this.getSeason();
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
//			this.form.setSeasonList(this.dao.seasonList());			
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("setRefference is error");
		}finally{
			logger.info("[setRefference][End]");
		}
	}
		
	/*private void onSearchTeamDetail() throws EnjoyException{
		logger.info("[onSearchTeamDetail][Begin]");
		JSONObject 						obj 				= null;
		JSONObject 						objDetail 			= null;
		JSONArray 						detailJSONArray 	= null;
		List<CancelSeatBean>			detailList			= null;
		CancelSeatBean					detail				= null;

		try{
			logger.info("[onSearchTeamDetail] matchId 		:: " + EnjoyUtils.nullToStr(this.request.getParameter("season")));
			obj 					= new JSONObject();
			detailJSONArray 		= new JSONArray();
			detailList 				= this.dao.getTeamList(EnjoyUtils.nullToStr(this.request.getParameter("season")));
			this.form.setTeamList(detailList);
			
			for(int i=0;i<detailList.size();i++){
				detail 			= (CancelSeatBean) detailList.get(i);
				objDetail 		= new JSONObject();
				
				objDetail.put("matchId", 				detail.getMatchId());
				objDetail.put("awayTeamNameTH",			detail.getAwayTeamNameTH());
				objDetail.put("awayTeamNameEN",			detail.getAwayTeamNameEN());
				
				detailJSONArray.add(objDetail);
			}
			obj.put("teamList", 		detailJSONArray);
			obj.put(STATUS, 			SUCCESS);
		}catch(EnjoyException e){
			logger.info(e.getMessage());
			obj.put(STATUS, 			ERROR);
			obj.put(ERR_MSG, 			e.getMessage());
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			obj.put(STATUS, 			ERROR);
			obj.put(ERR_MSG, 			e.getMessage());
			throw new EnjoyException("onSearchTeamDetail is error");
		}finally{
			//this.setRefference();
			this.enjoyUtil.writeMSG(obj.toString());
			logger.info("[onSearchTeamDetail][End]");
		}
		
	}*/
	
	private void onSearchTicketDetail() throws EnjoyException{
		logger.info("[onSearchTicketDetail][Begin]");
		
		CancelSeatBean 			bean 						= null;
		List<CancelSeatBean>	list						= null;
		String					season						= null;
		String					awayTeamNameTH				= null;
		String					fieldZoneId					= null;
		String					ticketId					= null;
		String					seatingNoBegin				= null;
		String					seatingNoEnd				= null;
		
		try{
			season 				= EnjoyUtils.nullToStr(this.request.getParameter("season"));
			awayTeamNameTH 		= EnjoyUtils.nullToStr(this.request.getParameter("awayTeamNameTH"));
			fieldZoneId 		= EnjoyUtils.nullToStr(this.request.getParameter("fieldZoneId"));
			ticketId 			= EnjoyUtils.nullToStr(this.request.getParameter("ticketId"));
			seatingNoBegin 		= EnjoyUtils.nullToStr(this.request.getParameter("seatingNoBegin"));
			seatingNoEnd 		= EnjoyUtils.nullToStr(this.request.getParameter("seatingNoEnd"));
			
			
			logger.info("[onSearchTicketDetail] season 				:: " + season);
			logger.info("[onSearchTicketDetail] awayTeamNameTH 		:: " + awayTeamNameTH);
			logger.info("[onSearchTicketDetail] fieldZoneId 		:: " + fieldZoneId);
			logger.info("[onSearchTicketDetail] ticketId 			:: " + ticketId);
			logger.info("[onSearchTicketDetail] seatingNoBegin 		:: " + seatingNoBegin);
			logger.info("[onSearchTicketDetail] seatingNoEnd 		:: " + seatingNoEnd);
			
			this.form.setSeason				(season);
			this.form.setAwayTeamNameTH		(awayTeamNameTH);
			this.form.setFieldZoneId		(fieldZoneId);
			this.form.setTicketId			(ticketId);
			this.form.setSeatingNoBegin		(seatingNoBegin);
			this.form.setSeatingNoEnd		(seatingNoEnd);
			
			
			bean 				= new CancelSeatBean();
			bean.setSeason			(season);
			bean.setAwayTeamNameTH	(awayTeamNameTH);
			bean.setFieldZoneId		(fieldZoneId);
			bean.setTicketId		(ticketId);
			bean.setSeatingNoBegin	(seatingNoBegin);
			bean.setSeatingNoEnd	(seatingNoEnd);
			
			list = this.dao.getSumDetailReservationList(bean);
			
			this.form.setResultList(list);
//			this.form.setCancelSeatBean(bean);
			
			this.form.setResultSize(String.valueOf(list.size()));
			
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
		
		try{	 		
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			obj 				= new JSONObject();
			
			ticketIdList		= EnjoyUtils.nullToStr(this.request.getParameter("ticketIdList"));

			session.beginTransaction();
			this.dao.cancelTicketByTicketId(session, ticketIdList);
			
			obj.put(STATUS, 			SUCCESS);
			
			session.getTransaction().commit();
			session.flush();			

			// ดึงรายละเอียดขึ้นมาใหม่ New Search After Commit
			this.form.setResultList(this.dao.getSumDetailReservationList(this.form.getCancelSeatBean()));
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
	
	private void getSeason(){
	   logger.info("[getSeason][Begin]");
	   
	   String							season					= null;
       List<String> 					list 					= new ArrayList<String>();
       String[]							strArray				= null;
       
	   try{
		   season					= EnjoyUtils.nullToStr(this.request.getParameter("season"));
		   
		   logger.info("[getSeason] season 			:: " + season);
		   
		   this.form.setSeason(season);
		   
		   list 		= this.dao.seasonList(season);
		   strArray 	= new String[list.size()];
		   strArray 	= list.toArray(strArray); 
		   
		   this.enjoyUtil.writeJsonMSG((String[]) strArray);
		   
		   this.form.setSeasonList(list);		
		   
	   }catch(Exception e){
		   e.printStackTrace();
		   logger.info("[getSeason] " + e.getMessage());
	   }finally{
		   logger.info("[getSeason][End]");
	   }
   }
	
	private void getAwayTeamNameTH(){
	   logger.info("[getAwayTeamNameTH][Begin]");
	   
	   String							season					= null;
	   String							awayTeamNameTH			= null;
       List<String> 					list 					= new ArrayList<String>();
       String[]							strArray				= null;
       
	   try{
		   season					= EnjoyUtils.nullToStr(this.request.getParameter("season"));
		   awayTeamNameTH			= EnjoyUtils.nullToStr(this.request.getParameter("awayTeamNameTH"));
		   
		   logger.info("[getAwayTeamNameTH] season 			:: " + season);
		   logger.info("[getAwayTeamNameTH] awayTeamNameTH 	:: " + awayTeamNameTH);
		   
		   this.form.setSeason(season);
		   this.form.setAwayTeamNameTH(awayTeamNameTH);
		   
		   list 		= this.dao.getTeamList(season, awayTeamNameTH);
		   strArray 	= new String[list.size()];
		   strArray 	= list.toArray(strArray); 
		   
		   this.enjoyUtil.writeJsonMSG((String[]) strArray);
		   
		   this.form.setSeasonList(list);		
		   
	   }catch(Exception e){
		   e.printStackTrace();
		   logger.info("[getAwayTeamNameTH] " + e.getMessage());
	   }finally{
		   logger.info("[getAwayTeamNameTH][End]");
	   }
   }
	
}
