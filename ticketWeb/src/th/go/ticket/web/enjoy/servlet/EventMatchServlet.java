package th.go.ticket.web.enjoy.servlet;

import java.io.IOException; 
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import th.go.ticket.app.enjoy.bean.EventMatchBean;
import th.go.ticket.app.enjoy.bean.UserDetailsBean;
import th.go.ticket.app.enjoy.dao.EventMatchDao; 
import th.go.ticket.app.enjoy.exception.EnjoyException;
import th.go.ticket.app.enjoy.form.EventMatchForm; 
import th.go.ticket.app.enjoy.main.Constants;
import th.go.ticket.app.enjoy.utils.EnjoyLogger;
import th.go.ticket.app.enjoy.utils.EnjoyUtils;
import th.go.ticket.app.enjoy.utils.HibernateUtil;
import th.go.ticket.web.enjoy.common.EnjoyStandardSvc;
import th.go.ticket.web.enjoy.utils.EnjoyUtil;

public class EventMatchServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(EventMatchServlet.class);
	
    private static final String FORM_NAME = "eventMatchForm";
    
    private EnjoyUtil               	enjoyUtil                   = null;
    private HttpServletRequest          request                     = null;
    private HttpServletResponse         response                    = null;
    private HttpSession                 session                     = null;
    private UserDetailsBean             userBean                    = null;
    private EventMatchDao		        dao							= null;
    private EventMatchForm		        form						= null; 
    
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
             this.form               	= (EventMatchForm)session.getAttribute(FORM_NAME);
             this.dao					= new EventMatchDao(); 
 			 
             logger.info("[execute][Begin] : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new EventMatchForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
 				logger.info("onLoad : "+pageAction);
 				request.setAttribute("target", Constants.PAGE_URL +"/EventMatchScn.jsp");
 			}else if(pageAction.equals("changeSeason")){
 				this.lp_onclick_changeSeason();
 			}else if(pageAction.equals("delRecord")){
 				//this.lp_onclick_deleteMatch();
 			}else if(pageAction.equals("NewSeason")){
 				this.lp_onclick_saveNewEventMatch();
 			}else if(pageAction.equals("UpdateSeason")){
 				this.lp_onclick_saveEventMatch();
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
	
 
	private void onLoad() throws EnjoyException{
		logger.info("[onLoad][Begin]");
		
		List<String> 					seasonList			= null;
		String							season				= null;
		List<EventMatchBean> 	        detailList			= null;
		EventMatchBean			        detail				= null;
		JSONObject 						obj 				= null;
		JSONObject 						objDetail 			= null;
		JSONArray 						detailJSONArray 	= null;
		
		try{
			seasonList 				= this.dao.seasonList();
			obj 					= new JSONObject();
			detailJSONArray 		= new JSONArray();
			
			if(seasonList!=null && seasonList.size() > 0){
				season 		= seasonList.get(0);
				this.lp_onSet_eventMatchList(season);
				
                detailList 	= this.form.getEventMatchList();
            	
    			
				for(int i=0;i<detailList.size();i++){
					detail 			= detailList.get(i);
					objDetail 		= new JSONObject(); 
					objDetail.put("awayTeamNameTH", 	detail.getAwayTeamNameTH());
					objDetail.put("awayTeamNameEN", 	detail.getAwayTeamNameEN());
					objDetail.put("matchDate", 			detail.getMatchDate());
					objDetail.put("matchTime", 			detail.getMatchTime());
					
					detailJSONArray.add(objDetail);
				}
				
				obj.put("detail", 			detailJSONArray);
		 
			}
			
			this.form.setSeasonList(seasonList);
			
			
		}catch(Exception e){
			e.printStackTrace();
			throw new EnjoyException("onLoad :: " + e.getMessage());
		}finally{
			seasonList			= null;
			season				= null;
			detailList			= null;
			detail				= null;
			obj 				= null;
			objDetail 			= null;
			detailJSONArray 	= null;
	
			logger.info("[onLoad][End]");
		}
		
	}
	
 
	private void lp_onclick_changeSeason() throws EnjoyException{
		logger.info("[lp_onclick_changeSeason][Begin]");
		
		String							season				= null;
		JSONObject 						obj 				= null;
		JSONObject 						objDetail 			= null;
		JSONObject 						objSeason 			= null;
		List<EventMatchBean> 	        detailList			= null;
		EventMatchBean			        detail				= null;
		JSONArray 						detailJSONArray 	= null;
		JSONArray 						seasonJSONArray 	= null;
		List							seasonList			= null;
		
		try{
			season 					= EnjoyUtils.nullToStr(this.request.getParameter("season"));
			logger.debug("servlet get season is  "+season);
			obj 					= new JSONObject();
			detailJSONArray 		= new JSONArray();
			seasonJSONArray 		= new JSONArray();
			
			if(!season.equals("")){
				this.lp_onSet_eventMatchList(season);
			 
				detailList 	= this.form.getEventMatchList();
				seasonList	= this.form.getSeasonList();
				obj.put("season", 			season); 
				obj.put(STATUS, 			SUCCESS);  
				
				for(int i=0;i<detailList.size();i++){
					detail 			= detailList.get(i);
					objDetail 		= new JSONObject(); 
					objDetail.put("matchId", 	        detail.getMatchId());
					objDetail.put("awayTeamNameTH", 	detail.getAwayTeamNameTH());
					objDetail.put("awayTeamNameEN", 	detail.getAwayTeamNameEN());
					objDetail.put("matchDate", 			detail.getMatchDate());
					objDetail.put("matchTime", 			detail.getMatchTime());
					objDetail.put("season", 			detail.getSeason());
					detailJSONArray.add(objDetail); 
				}  
				
				for(int i=0;i<seasonList.size();i++){ 
					this.lp_onSet_eventMatchList(season);
					objSeason = new JSONObject(); 
					objSeason.put("season", seasonList.get(i)); 
					seasonJSONArray.add(objSeason);
				}  
				obj.put("detail", 			detailJSONArray);
				obj.put("seasonList", 		seasonJSONArray);
				obj.put("detailSize", 		detailList.size());
			}else{
				obj.put(STATUS, 			ERROR);
				obj.put(ERR_MSG, 			"ฤดูกาลต้องไม่เท่ากับค่าว่าง");
			}
			
			
		}catch(Exception e){
			obj.put(STATUS, 			ERROR);
			obj.put(ERR_MSG, 			e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("onLoad :: " + e.getMessage());
		}finally{
			this.enjoyUtil.writeMSG(obj.toString());
			season				= null;
			obj 				= null;
			objDetail 			= null;
			objSeason 			= null;
			detailList			= null;
			detail				= null;
			detailJSONArray 	= null;
			seasonJSONArray 	= null;
			seasonList			= null; 
			logger.info("[lp_onclick_changeSeason][End]");
		}
		
	}
	
	private void lp_onSet_eventMatchList(String season) throws EnjoyException{
		logger.info("[lp_onSet_eventMatchList][Begin]");
		List<EventMatchBean> 	eventMatchList	= null;
		
		try{
			logger.info("[lp_onSet_eventMatchList] : send  season :: " +season);
			eventMatchList	= this.dao.detailEventMatchByYear(season);
			
			logger.info("[lp_onSet_eventMatchList] : size :: " +eventMatchList.size());
			logger.info("[lp_onSet_eventMatchList] :  " +eventMatchList.toString());
			
			this.form.setSeason(season);
			this.form.setEventMatchList(eventMatchList);
			
		}catch(Exception e){
			e.printStackTrace();
			throw new EnjoyException("lp_onSet_eventMatchList :: " + e.getMessage());
		}finally{
			eventMatchList		= null;
			logger.info("[lp_onSet_eventMatchList][End]");
		}
		
	}
	 
 
	
	private void lp_onclick_saveEventMatch() throws EnjoyException{
		logger.info("[lp_onclick_saveEventMatch][Begin]");  
			String 			       getDelList 	            = null;
		   	String[]			   getAwayTeamNameENList    = null;
		   	String[]			   getAwayTeamNameTHList    = null;
			String[]			   getDateList 	            = null;
			String[]			   getTimeList 	            = null;
			String[]			   statusList 	            = null;
			String[]               getMatchIdList           = null;
			String  			   season	                = null; 
			String  			   seasonSelect             = null; 
			String                 nameTh                   = null;
			String                 nameEn                   = null;
			String                 matchDate                = null;
			String                 matchTime                = null;
			String                 status                   = null;
			String  			   matchId	                = null; 
			EventMatchBean 	       bean 		    		= null; 
			JSONObject 			   obj 			    		= new JSONObject(); 
			List<EventMatchBean>   delList                  = null;
			SessionFactory 		   sessionFactory		    = null;
			Session 			   session				    = null;
			Transaction            tx                       = null;
			int                    matchIdLast              = 0;
			int                    newMatchid               = 0; 
			
			try{ 
				sessionFactory 				= HibernateUtil.getSessionFactory();
				session 					= sessionFactory.openSession();
				tx                          = session.beginTransaction();
				obj 						= new JSONObject();
				getAwayTeamNameENList		= this.request.getParameterValues("awayTeamNameEN"); 
				getAwayTeamNameTHList		= this.request.getParameterValues("awayTeamNameTH"); 
				getDateList					= this.request.getParameterValues("matchDate"); 
				getTimeList					= this.request.getParameterValues("matchTime");  
				statusList                  = this.request.getParameterValues("hidStartus");
				getMatchIdList    		    = this.request.getParameterValues("matchId");
				getDelList      		    = EnjoyUtils.nullToStr(this.request.getParameter("deleteList"));
				seasonSelect                = EnjoyUtils.nullToStr(this.request.getParameter("hidSeason")); 
				int seasonInt           	= Integer.parseInt(seasonSelect);  
				System.out.println("[lp_onclick_saveEventMatch][seasonSelect  : ]" + seasonSelect);
				System.out.println("[lp_onclick_saveEventMatch][seasonInt  : ]" + seasonInt); 
			
				
				System.out.print("getDelList:"+getDelList);	
				
				if(!"none".equals(getDelList)){   
					List<String> deleteList = EnjoyUtils.getListFromArr(getDelList);
					System.out.print("deleteList : size ::"+deleteList.size());
					int matchDel ;
					if(deleteList.size()>0){
						for(String del :deleteList){
							System.out.print("del"+deleteList.size());
							if(del != ""){ 
								matchDel = Integer.valueOf(del); 
								System.out.print("delete : id is ::" +matchDel); 
								this.dao.deleteEventMatch(session,matchDel,Integer.valueOf(seasonSelect)); 
							}
						}
						
					} 
				}
				
				matchIdLast = this.dao.selectMaxMatchId(session,seasonSelect);
				System.out.println("matchIdLast :: "+matchIdLast);
				newMatchid  = matchIdLast;
				
				if(getAwayTeamNameENList.length>0){  
					for(int i = 0 ; i < getAwayTeamNameENList.length ; i++){   
				 
						nameTh        = EnjoyUtils.nullToStr(getAwayTeamNameTHList[i]);     
						nameEn        = EnjoyUtils.nullToStr(getAwayTeamNameENList[i]);   
						matchDate     = EnjoyUtils.nullToStr(getDateList[i]);   
						matchTime     = EnjoyUtils.nullToStr(getTimeList[i]);  
						status        = EnjoyUtils.nullToStr(statusList[i]); 
						matchId       = EnjoyUtils.nullToStr(getMatchIdList[i]);
						
						bean = new EventMatchBean(); 
						bean.setAwayTeamNameEN(nameEn);
						bean.setAwayTeamNameTH(nameTh);
						bean.setMatchDate(EnjoyUtils.dateToThaiDB(matchDate));
						bean.setMatchTime(EnjoyUtils.timeToDB(matchTime));
						bean.setSeason(seasonSelect);
						bean.setMatchId(matchId);
			            
						System.out.print("bean:"+bean.toString());	
						
						if(status.equals("N")){  
							newMatchid    = newMatchid + 1;
							System.out.println("newMatchid :: "+newMatchid); 
							System.out.println("insert : " + " seasonSelect = "+seasonSelect+" match = "+newMatchid );
							bean.setMatchId(String.valueOf(newMatchid)); 
							this.dao.insertEventMatch(session,bean);   
						}else if(status.equals("U")){
							System.out.println("update : " + " seasonSelect = "+seasonSelect+" match = "+matchId );
							this.dao.updateEventMatch(session,bean);  
						}
						 
					}
					 
				} 
				
				tx.commit(); 
				session.close();
				obj.put("status",   "SUCCESS"); 

			}catch(Exception e){
				session.getTransaction().rollback();
				obj.put("status", 		"ERROR"); 
				obj.put(ERR_MSG, 		"เกิดข้อผิดพลาดในการบันทึกข้อมูล"); 
				throw new EnjoyException("saveEventMatch : " +"เกิดข้อผิดพลาดในการบันทึกข้อมูล");
				
			}finally{   
				this.enjoyUtil.writeMSG(obj.toString());  
				getAwayTeamNameENList    = null;
			   	getAwayTeamNameTHList    = null;
				getDateList 	        = null;
				getTimeList 	        = null; 
				season	                = null; 
				seasonSelect            = null; 
				nameTh                  = null;
				nameEn                  = null;
				matchDate               = null;
				matchTime               = null;
				bean 		    		= null; 
				obj 			    	= null;
				delList                 = null; 
				session                 = null;
				sessionFactory	        = null;
				tx                      = null;
				System.out.println("[lp_onclick_saveEventMatch][End]");
			}
		}
	
 
	private void lp_onclick_saveNewEventMatch() throws EnjoyException{
		logger.info("[lp_onclick_saveNewEventMatch][Begin]");  
			
		   	String[]			   getAwayTeamNameENList    = null;
		   	String[]			   getAwayTeamNameTHList    = null;
			String[]			   getDateList 	            = null;
			String[]			   getTimeList 	            = null;
			String[]			   statusList 	            = null;
			String[]               getMatchIdList           = null;
			String  			   season	                = null; 
			String  			   seasonNew                = null; 
			String                 nameTh                   = null;
			String                 nameEn                   = null;
			String                 matchDate                = null;
			String                 matchTime                = null;
			String                 status                   = null; 
			EventMatchBean 	       bean 		    		= null; 
			JSONObject 			   obj 			    		= new JSONObject();  
			SessionFactory 		   sessionFactory			= null;
			Session 			   session					= null;
			int 				   matchId       			= 0;
			try{
				
				sessionFactory 				= HibernateUtil.getSessionFactory();
				session 					= sessionFactory.openSession();
				obj 		                = new JSONObject(); 
				getAwayTeamNameENList		= this.request.getParameterValues("awayTeamNameEN"); 
				getAwayTeamNameTHList		= this.request.getParameterValues("awayTeamNameTH"); 
				getDateList					= this.request.getParameterValues("matchDate"); 
				getTimeList					= this.request.getParameterValues("matchTime");  
				statusList                  = this.request.getParameterValues("hidStartus");
				getMatchIdList    		    = this.request.getParameterValues("matchId");
			
				if(this.request.getParameter("seasonNew") != null ){
					seasonNew                   = EnjoyUtils.nullToStr(this.request.getParameter("seasonNew"));
					System.out.println("[lp_onclick_saveNewEventMatch][seasonNew  : ]" + seasonNew);
					int seasonInt           	= Integer.parseInt(seasonNew);  
					System.out.println("[lp_onclick_saveNewEventMatch][seasonInt  : ]" + seasonInt);
					 
					session.getTransaction().begin();
					
					if(getAwayTeamNameENList.length>0){ 
						
						for(int i = 0 ; i < getAwayTeamNameENList.length ; i++){   
							nameTh        = EnjoyUtils.nullToStr(getAwayTeamNameTHList[i]);     
							nameEn        = EnjoyUtils.nullToStr(getAwayTeamNameENList[i]);    
							matchDate     = EnjoyUtils.nullToStr(getDateList[i]); 
							matchTime     = EnjoyUtils.nullToStr(getTimeList[i]); 
							status        = EnjoyUtils.nullToStr(statusList[i]); 
							
							 
							bean = new EventMatchBean(); 
							bean.setAwayTeamNameEN(nameEn);
							bean.setAwayTeamNameTH(nameTh);
							bean.setMatchDate(EnjoyUtils.dateToThaiDB(matchDate));
							bean.setMatchTime(EnjoyUtils.timeToDB(matchTime));
							bean.setSeason(seasonNew); 
							bean.setMatchId(String.valueOf(matchId)); 
							this.dao.insertEventMatch(session,bean);    
							matchId++;
						} 
						 
					}
				}
				
				session.getTransaction().commit(); 
				obj.put("status",   "SUCCESS"); 
			}catch(EnjoyException error){ 
				session.getTransaction().rollback();
				obj.put("status", 		"ERROR");
				obj.put(ERR_MSG, 		"เกิดข้อผิดพลาดในการบันทึกข้อมูล"); 
				throw new EnjoyException("saveEventMatch : " + error.getMessage()); 
			}catch(Exception e){
				obj.put("status", 		"ERROR"); 
				obj.put(ERR_MSG, 		"เกิดข้อผิดพลาดในการบันทึกข้อมูล"); 
				throw new EnjoyException("saveEventMatch : " +"เกิดข้อผิดพลาดในการบันทึกข้อมูล");
				
			}finally{   
				getAwayTeamNameENList   = null;
			   	getAwayTeamNameTHList   = null;
				getDateList 	        = null;
				getTimeList 	        = null; 
				season	                = null; 
				seasonNew               = null; 
				nameTh                  = null;
				nameEn                  = null;
				matchDate               = null;
				matchTime               = null;
				bean 		    		= null; 
				this.enjoyUtil.writeMSG(obj.toString());
				obj 			    	= null;   
				session.clear();
				session.close();
				sessionFactory	        = null;
				System.out.println("[lp_onclick_saveNewEventMatch][End]");
			}
		}
  
	private void lp_onclick_deleteMatch() throws EnjoyException{
		logger.info("[lp_onclick_deleteMatch][Begin]");   
			Integer                matchId          = 0;
			JSONObject 			   obj 			    = new JSONObject();
			String                 match            = null;  
			SessionFactory 		   sessionFactory	= null;
			Session 			   session			= null;
			try{ 
				sessionFactory 	= HibernateUtil.getSessionFactory();
				session 		= sessionFactory.openSession();
				
				obj 		   = new JSONObject();
				match	       = EnjoyUtils.nullToStr(this.request.getParameter("matchId"));
				matchId		   = Integer.valueOf(match);  
				System.out.println("[lp_onclick_deleteMatch][matchId is ]" + matchId);
				Integer countOrder = this.dao.countOrderFromMatch(session,matchId);
				System.out.println("[lp_onclick_deleteMatch][countOrder is ]" + countOrder.intValue());
	  
				obj 			        = new JSONObject(); 
				obj.put(STATUS, 		SUCCESS);
				obj.put("COUNT", 		countOrder);
				 
				obj.put("status",   "SUCCESS"); 
			}catch(EnjoyException error){ 
				obj.put("status", 		"ERROR");
				obj.put(ERR_MSG, 		"เกิดข้อผิดพลาดในการลบข้อมูล");
				session.getTransaction().rollback(); 
				throw new EnjoyException("deleteMatch : " + error.getMessage()); 
			}catch(Exception e){
				obj.put("status", 		"ERROR"); 
				obj.put(ERR_MSG, 		"เกิดข้อผิดพลาดในการลบข้อมูล");
				session.getTransaction().rollback(); 
				throw new EnjoyException("deleteMatch : " +"เกิดข้อผิดพลาดในการลบข้อมูล");
				
			}finally{  
				this.enjoyUtil.writeMSG(obj.toString()); 
				session.clear();
				session.close();
				sessionFactory	= null;
				matchId          = 0;
				obj 			 = null;
				match            = null;  
			
				System.out.println("[lp_onclick_deleteMatch][End]");
			}
		}
	
	
	 
}
