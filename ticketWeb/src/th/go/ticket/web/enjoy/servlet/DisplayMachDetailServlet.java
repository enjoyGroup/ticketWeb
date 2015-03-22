package th.go.ticket.web.enjoy.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import th.go.ticket.app.enjoy.bean.DisplayMachDetailBean;
import th.go.ticket.app.enjoy.bean.UserDetailsBean;
import th.go.ticket.app.enjoy.dao.DisplayMachDetailDao;
import th.go.ticket.app.enjoy.exception.EnjoyException;
import th.go.ticket.app.enjoy.form.DisplayMachDetailForm;
import th.go.ticket.app.enjoy.main.Constants;
import th.go.ticket.app.enjoy.utils.EnjoyLogger;
import th.go.ticket.app.enjoy.utils.EnjoyUtils;
import th.go.ticket.web.enjoy.common.EnjoyStandardSvc;
import th.go.ticket.web.enjoy.utils.EnjoyUtil;

public class DisplayMachDetailServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(DisplayMachDetailServlet.class);
	
    private static final String FORM_NAME = "displayMachDetailForm";
    
    private EnjoyUtil               	enjoyUtil                   = null;
    private HttpServletRequest          request                     = null;
    private HttpServletResponse         response                    = null;
    private HttpSession                 session                     = null;
    private UserDetailsBean             userBean                    = null;
    private DisplayMachDetailDao		dao							= null;
    private DisplayMachDetailForm		form						= null;
    
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
             this.form               	= (DisplayMachDetailForm)session.getAttribute(FORM_NAME);
             this.dao					= new DisplayMachDetailDao();
 			
             logger.info("[execute][Begin] : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new DisplayMachDetailForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
 				request.setAttribute("target", Constants.PAGE_URL +"/DisplayMachDetailScn.jsp");
 			}else if(pageAction.equals("changeMatch")){
 				this.lp_changeMatch();
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
		List<DisplayMachDetailBean> 	matchList					= null;
		DisplayMachDetailBean			matchDetail					= null;
		JSONObject 						obj 						= null;
		JSONObject 						objSeason 					= null;
		JSONObject 						objMatch 					= null;
		JSONArray 						seasonJSONArray 			= null;
		JSONArray 						matchJSONArray 				= null;
		String							matchId						= null;
		boolean							flag						= false;
		String							awayTeamName				= null;
		
		try{
			seasonList 				= this.dao.seasonList();
			obj 					= new JSONObject();
			seasonJSONArray 		= new JSONArray();
			matchJSONArray	 		= new JSONArray();
			
			if(seasonList!=null && seasonList.size() > 0){

				for(int i=0;i<seasonList.size();i++){
					season 					= seasonList.get(i);
					matchList				= this.dao.matchList(season);
					matchJSONArray			= new JSONArray();
					
					this.form.getMatchMap().put(season, matchList);
					
					if(flag==false){
						if(matchList!=null && matchList.size() > 0){
							matchId 		= matchList.get(0).getMatchId();
							awayTeamName	=  matchList.get(0).getAwayTeamNameTH();
							flag			= true;
							
							this.form.setAwayTeamName(awayTeamName);
							
							//gen header
							this.form.setHeaderTicketTypeList(this.dao.genHeaderTicketType(matchId));
							
							//set detail
							this.form.setDetailOfMatchList(this.dao.detailOfMatch(matchId));
						}
						
					}
					
					/*Begin ก็บ json object season*/
					objSeason 		= new JSONObject();
					objSeason.put("season", 			season);
					seasonJSONArray.add(objSeason);
					/*End เก็บ json object season*/
					
					/*Begin เก็บ json object match*/
					for(int j=0;j<matchList.size();j++){
						objMatch 		= new JSONObject();
						matchDetail		= matchList.get(j);
						
						objMatch.put("matchId", 			matchDetail.getMatchId());
						objMatch.put("awayTeamName", 		matchDetail.getAwayTeamNameTH());
						
						matchJSONArray.add(objMatch);
					}
					
					obj.put(season, 			matchJSONArray);
					/*End เก็บ json object season*/
					
					this.form.setJsonString(obj.toString());
					
				}
				obj.put("seasonList", 			seasonJSONArray);
				
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
	
	private void lp_changeMatch() throws EnjoyException{
		logger.info("[lp_changeMatch][Begin]");
		
		String							matchId					= null;
		JSONObject 						obj 					= null;
		JSONObject 						objDetailMatch 			= null;
		JSONObject 						objTicketType 			= null;
		JSONArray 						detailMatchJSONArray 	= null;
		JSONArray 						ticketTypeJSONArray 	= null;
		List<DisplayMachDetailBean> 	headerTicketTypeList	= null;
		List<DisplayMachDetailBean> 	detailMatchList			= null;
		DisplayMachDetailBean			headerTicketType		= null;
		DisplayMachDetailBean			detailMatch				= null;
		
		try{
			matchId 				= EnjoyUtils.nullToStr(this.request.getParameter("matchId"));
			obj 					= new JSONObject();
			ticketTypeJSONArray 	= new JSONArray();
			detailMatchJSONArray 	= new JSONArray();
			
			if(!matchId.equals("")){
				
				headerTicketTypeList 	= this.dao.genHeaderTicketType(matchId);
				detailMatchList			= this.dao.detailOfMatch(matchId);
				
				for(int i=0;i<headerTicketTypeList.size();i++){
					headerTicketType 	= headerTicketTypeList.get(i);
					objTicketType		= new JSONObject();
					
					objTicketType.put("matchId", 			headerTicketType.getMatchId());
					objTicketType.put("bookingTypeName", 	headerTicketType.getBookingTypeName());
					
					ticketTypeJSONArray.add(objTicketType);
				}
				
				obj.put("ticketTypeList", ticketTypeJSONArray);
				
				
				for(int i=0;i<detailMatchList.size();i++){
					detailMatch		 	= detailMatchList.get(i);
					objDetailMatch		= new JSONObject();
					
					objDetailMatch.put("matchId", 			detailMatch.getMatchId());
					objDetailMatch.put("fieldZoneName", 	detailMatch.getFieldZoneName());
					objDetailMatch.put("bookingTypeId", 	detailMatch.getBookingTypeId());
					objDetailMatch.put("bookingTypeName", 	detailMatch.getBookingTypeName());
					objDetailMatch.put("totalSeating", 		detailMatch.getTotalSeating());
					objDetailMatch.put("tatalMoney", 		detailMatch.getTatalMoney());
					
					detailMatchJSONArray.add(objDetailMatch);
				}
				
				obj.put("detailMatchList", detailMatchJSONArray);
				obj.put("status", 			"SUCCESS");
				
				
			}else{
				obj.put("status", 			"ERROR");
				obj.put("errMsg", 			"กรุณาเลือก Match");
			}
			
			
		}catch(Exception e){
			obj.put("status", 			"ERROR");
			obj.put("errMsg", 			e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("onLoad :: " + e.getMessage());
		}finally{
			this.enjoyUtil.writeMSG(obj.toString());
			
			matchId					= null;
			objDetailMatch 			= null;
			objTicketType 			= null;
			detailMatchJSONArray 	= null;
			ticketTypeJSONArray 	= null;
			headerTicketTypeList	= null;
			detailMatchList			= null;
			headerTicketType		= null;
			detailMatch				= null;
			
			logger.info("[lp_changeMatch][End]");
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
