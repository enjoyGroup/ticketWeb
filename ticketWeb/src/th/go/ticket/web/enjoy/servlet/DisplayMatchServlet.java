package th.go.ticket.web.enjoy.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import th.go.ticket.app.enjoy.bean.DisplayMatchDetailBean;
import th.go.ticket.app.enjoy.bean.UserDetailsBean;
import th.go.ticket.app.enjoy.dao.DisplayMatchDetailDao;
import th.go.ticket.app.enjoy.exception.EnjoyException;
import th.go.ticket.app.enjoy.form.DisplayMacthForm;
import th.go.ticket.app.enjoy.main.Constants;
import th.go.ticket.app.enjoy.utils.EnjoyLogger;
import th.go.ticket.app.enjoy.utils.EnjoyUtils;
import th.go.ticket.web.enjoy.common.EnjoyStandardSvc;
import th.go.ticket.web.enjoy.utils.EnjoyUtil;

public class DisplayMatchServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(DisplayMatchServlet.class);
	
    private static final String FORM_NAME = "displayMacthForm";
    
    private EnjoyUtil               	enjoyUtil                   = null;
    private HttpServletRequest          request                     = null;
    private HttpServletResponse         response                    = null;
    private HttpSession                 session                     = null;
    private UserDetailsBean             userBean                    = null;
    private DisplayMatchDetailDao		dao							= null;
    private DisplayMacthForm			form						= null;
    
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
             this.form               	= (DisplayMacthForm)session.getAttribute(FORM_NAME);
             this.dao					= new DisplayMatchDetailDao();
 			
             logger.info("[execute][Begin] : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new DisplayMacthForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
 				request.setAttribute("target", Constants.PAGE_URL +"/DisplayMatchScn.jsp");
 			}else if( pageAction.equals("getReportByTicketType") ){
 				this.getReportByTicketType();
 				request.setAttribute("target", Constants.PAGE_URL +"/DisplayMatchScn.jsp");
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
		
		List<String> 					seasonList					= null;
		String							season						= null;
		List<DisplayMatchDetailBean> 	matchList					= null;
		String							matchId						= null;
		boolean							flag						= false;
		String							awayTeamName				= null;
		
		try{
			seasonList 				= this.dao.seasonList();
			
			if(seasonList!=null && seasonList.size() > 0){

				for(int i=0;i<seasonList.size();i++){
					season 					= seasonList.get(i);
					matchList				= this.dao.matchList(season);
					
					this.form.getMatchMap().put(season, matchList);
					
					if(flag==false){
						if(matchList!=null && matchList.size() > 0){
							matchId 				= matchList.get(0).getMatchId();
							awayTeamName			= matchList.get(0).getAwayTeamNameTH();
							flag					= true;
							
							this.form.setSeason(season);
							this.form.setMatchId(matchId);
							this.form.setAwayTeamName(awayTeamName);
							
						}
						
					}
				}
			}
			
			this.form.setSeasonList(seasonList);
			
			
		}catch(Exception e){
			e.printStackTrace();
			throw new EnjoyException("onLoad :: " + e.getMessage());
		}finally{
			seasonList		= null;
			season			= null;
			
			logger.info("[onLoad][End]");
		}
		
	}
	
	
	
	private void getReportByTicketType() throws EnjoyException{
		logger.info("[getReportByTicketType][Begin]");
		
		List<String> 					seasonList					= null;
		String							season						= null;
		List<DisplayMatchDetailBean> 	matchList					= null;
		String							matchId						= null;
		boolean							flag						= false;
		String							awayTeamName				= null;
		
		try{
			seasonList 				= this.dao.seasonList();
			season 					= EnjoyUtils.nullToStr(this.request.getParameter("season"));
			matchId 				= EnjoyUtils.nullToStr(this.request.getParameter("matchId"));
			awayTeamName 			= EnjoyUtils.nullToStr(this.request.getParameter("awayTeamName"));
			
			if(seasonList!=null && seasonList.size() > 0){

				for(int i=0;i<seasonList.size();i++){
					season 					= seasonList.get(i);
					matchList				= this.dao.matchList(season);
					
					this.form.getMatchMap().put(season, matchList);
					
				}
				
				this.form.setSeason(season);
				this.form.setMatchId(matchId);
				this.form.setAwayTeamName(awayTeamName);
				
			}
			
			this.form.setSeasonList(seasonList);
			
			
		}catch(Exception e){
			e.printStackTrace();
			throw new EnjoyException("getReportByTicketType :: " + e.getMessage());
		}finally{
			seasonList		= null;
			season			= null;
			
			logger.info("[getReportByTicketType][End]");
		}
		
	}
	
	
	
	
	
	
	
	
	
}
