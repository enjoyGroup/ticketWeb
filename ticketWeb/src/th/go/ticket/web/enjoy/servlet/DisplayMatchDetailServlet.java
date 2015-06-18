package th.go.ticket.web.enjoy.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import th.go.ticket.app.enjoy.bean.DisplayMatchDetailBean;
import th.go.ticket.app.enjoy.bean.UserDetailsBean;
import th.go.ticket.app.enjoy.dao.DisplayMatchDetailDao;
import th.go.ticket.app.enjoy.exception.EnjoyException;
import th.go.ticket.app.enjoy.form.DisplayMacthForm;
import th.go.ticket.app.enjoy.form.DisplayMatchDetailForm;
import th.go.ticket.app.enjoy.main.Constants;
import th.go.ticket.app.enjoy.utils.EnjoyLogger;
import th.go.ticket.app.enjoy.utils.EnjoyUtils;
import th.go.ticket.web.enjoy.common.EnjoyStandardSvc;
import th.go.ticket.web.enjoy.utils.EnjoyUtil;

public class DisplayMatchDetailServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(DisplayMatchDetailServlet.class);
	
    private static final String FORM_NAME = "displayMatchDetailForm";
    
    private EnjoyUtil               	enjoyUtil                   = null;
    private HttpServletRequest          request                     = null;
    private HttpServletResponse         response                    = null;
    private HttpSession                 session                     = null;
    private UserDetailsBean             userBean                    = null;
    private DisplayMatchDetailDao		dao							= null;
    private DisplayMatchDetailForm		form						= null;
    
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
             this.form               	= (DisplayMatchDetailForm)session.getAttribute(FORM_NAME);
             this.dao					= new DisplayMatchDetailDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new DisplayMatchDetailForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
 				request.setAttribute("target", Constants.PAGE_URL +"/DisplayMatchDetailScn.jsp");
 			}else if(pageAction.equals("onGetData")){
 				this.onPageGetData();
 				request.setAttribute("target", Constants.PAGE_URL +"/DisplayMatchDetailScn.jsp");
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
		DisplayMacthForm				displayMacthForm			= null;
		
		try{
			
			displayMacthForm               	= (DisplayMacthForm) session.getAttribute("displayMacthForm");
			
			if(displayMacthForm!=null){
				matchId 				= EnjoyUtil.nullToStr(displayMacthForm.getMatchId());
				season					= EnjoyUtil.nullToStr(displayMacthForm.getSeason());
				awayTeamName			= EnjoyUtil.nullToStr(displayMacthForm.getAwayTeamName());
				
				this.onGetData(matchId, season, awayTeamName);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new EnjoyException("onLoad :: " + e.getMessage());
		}finally{
			seasonList		= null;
			season			= null;
			
			logger.info("[onLoad][End]");
		}
		
	}
	
	private void onPageGetData() throws EnjoyException{
		
		logger.info("[onPageGetData][Begin]");
		
		String							matchId						= null;
		String							season						= null;
		String							awayTeamName				= null;
		
		try{
			matchId 				= EnjoyUtil.nullToStr(request.getParameter("matchId"));
			season					= EnjoyUtil.nullToStr(request.getParameter("season"));
			awayTeamName			= EnjoyUtil.nullToStr(request.getParameter("awayTeamName"));
			
			this.onGetData(matchId, season, awayTeamName);
			
			
		}catch(Exception e){
			logger.info(e.getMessage());
		}finally{
			
			matchId						= null;
			season						= null;
			awayTeamName				= null;
			
			logger.info("[onPageGetData][End]");
		}
	}
	
	private void onGetData(String matchId, String season, String awayTeamName) throws EnjoyException{
		logger.info("[onGetData][Begin]");
		
		JSONObject 						obj 						= null;
		JSONObject 						objMatch 					= null;
		JSONArray 						matchJSONArray 				= null;
		List<DisplayMatchDetailBean> 	detailOfMatchList 			= null;
		String							fieldZoneNameTmp			= "";
		List<String> 					fieldZoneNameList			= null;
		Map								detailOfMatchMap			= null;
		List<DisplayMatchDetailBean> 	headerTicketTypeList 		= null;
		Map								detailTicketMap				= null;
		String							bookingTypeIdTmp			= "";
		Map 							sumTatalMoneyMap 			= null;
		Double							tatalMoney					= 0.00;
		Double							tatalMoneyTmp				= 0.00;
		
		try{
			
			logger.info("[onGetData] matchId 		:: " + matchId);
			logger.info("[onGetData] season 		:: " + season);
			logger.info("[onGetData] awayTeamName 	:: " + awayTeamName);
			
			obj 					= new JSONObject();
			matchJSONArray	 		= new JSONArray();
//			fieldZoneNameList		= this.form.getFieldZoneNameList();
//			detailOfMatchMap		= this.form.getDetailOfMatchMap();
//			sumTatalMoneyMap		= this.form.getSumTatalMoneyMap();
			fieldZoneNameList		= new ArrayList<String>();
			detailOfMatchMap		= new Hashtable();
			sumTatalMoneyMap		= new Hashtable();
			headerTicketTypeList	= this.dao.genHeaderTicketType(matchId, season);//gen header
			matchJSONArray			= new JSONArray();
			
			this.form.setAwayTeamName("ปี : " + season + " แข่งขันกับ " + awayTeamName);
			
			//set header
			this.form.setHeaderTicketTypeList(headerTicketTypeList);
			
//			logger.info("[onLoad] Header.size :: " + this.form.getHeaderTicketTypeList().size());
			
			/*Begin set detail*/
			detailOfMatchList = this.dao.detailOfMatch(matchId, season);
			
			for(DisplayMatchDetailBean beanDetail:detailOfMatchList){
				
				fieldZoneNameTmp 		= beanDetail.getFieldZoneName();
				bookingTypeIdTmp		= beanDetail.getBookingTypeId();
				
				if(detailOfMatchMap.containsKey(fieldZoneNameTmp)){
					detailTicketMap = (Map) detailOfMatchMap.get(fieldZoneNameTmp);
					tatalMoney		= (Double) sumTatalMoneyMap.get(fieldZoneNameTmp);
					tatalMoneyTmp	= tatalMoney + EnjoyUtils.parseDouble(beanDetail.getTatalMoney());
					
					if(!detailTicketMap.containsKey(bookingTypeIdTmp)){
						detailTicketMap.put(bookingTypeIdTmp, beanDetail);
					}
					
					detailOfMatchMap.put(fieldZoneNameTmp, detailTicketMap);
					sumTatalMoneyMap.put(fieldZoneNameTmp, tatalMoneyTmp);
					
				}else{
					detailTicketMap	= new Hashtable();
					
					detailTicketMap.put(bookingTypeIdTmp, beanDetail);
					
					detailOfMatchMap.put(fieldZoneNameTmp, detailTicketMap);
					sumTatalMoneyMap.put(fieldZoneNameTmp, EnjoyUtils.parseDouble(beanDetail.getTatalMoney()));
					fieldZoneNameList.add(fieldZoneNameTmp);
				}
//				logger.info("[onLoad] detailTicketMap :: " + detailTicketMap);
			}
			/*End set detail*/
			
			/*Begin เก็บ json object match*/
			for(String fieldZoneName:fieldZoneNameList){
				objMatch 		= new JSONObject();
				tatalMoney		= (Double) sumTatalMoneyMap.get(fieldZoneName);
				
				objMatch.put("fieldZoneName", 		fieldZoneName);
				objMatch.put("tatalMoney", 		tatalMoney);
				
				matchJSONArray.add(objMatch);
			}
			
			obj.put("fieldZoneNameList", 			matchJSONArray);
			/*End เก็บ json object season*/
			
			this.form.setFieldZoneNameList(fieldZoneNameList);
			this.form.setDetailOfMatchMap(detailOfMatchMap);
			this.form.setSumTatalMoneyMap(sumTatalMoneyMap);
			
			this.form.setJsonString(obj.toString());
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			throw new EnjoyException("onGetData :: " + e.getMessage());
		}finally{
			season			= null;
			
			logger.info("[onGetData][End]");
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
