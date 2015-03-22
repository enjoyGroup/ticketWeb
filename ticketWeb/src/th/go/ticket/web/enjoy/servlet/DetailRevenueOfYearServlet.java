package th.go.ticket.web.enjoy.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import th.go.ticket.app.enjoy.bean.DetailRevenueOfYearBean;
import th.go.ticket.app.enjoy.bean.UserDetailsBean;
import th.go.ticket.app.enjoy.dao.DetailRevenueOfYearDao;
import th.go.ticket.app.enjoy.exception.EnjoyException;
import th.go.ticket.app.enjoy.form.DetailRevenueOfYearForm;
import th.go.ticket.app.enjoy.main.Constants;
import th.go.ticket.app.enjoy.utils.EnjoyLogger;
import th.go.ticket.app.enjoy.utils.EnjoyUtils;
import th.go.ticket.web.enjoy.common.EnjoyStandardSvc;
import th.go.ticket.web.enjoy.utils.EnjoyUtil;

public class DetailRevenueOfYearServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(DetailRevenueOfYearServlet.class);
	
    private static final String FORM_NAME = "detailRevenueOfYearForm";
    
    private EnjoyUtil               	enjoyUtil                   = null;
    private HttpServletRequest          request                     = null;
    private HttpServletResponse         response                    = null;
    private HttpSession                 session                     = null;
    private UserDetailsBean             userBean                    = null;
    private DetailRevenueOfYearDao		dao							= null;
    private DetailRevenueOfYearForm		form						= null;
    
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
             this.form               	= (DetailRevenueOfYearForm)session.getAttribute(FORM_NAME);
             this.dao					= new DetailRevenueOfYearDao();
 			
             logger.info("[execute][Begin] : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new DetailRevenueOfYearForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
 				request.setAttribute("target", Constants.PAGE_URL +"/DetailRevenueOfYearScn.jsp");
 			}else if(pageAction.equals("changeSeason")){
 				this.lp_changeSeason();
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
		List<DetailRevenueOfYearBean> 	detailList			= null;
		DetailRevenueOfYearBean			detail				= null;
		JSONObject 						obj 				= null;
		JSONObject 						objDetail 			= null;
		JSONArray 						detailJSONArray 	= null;
		
		try{
			seasonList 				= this.dao.seasonList();
			obj 					= new JSONObject();
			detailJSONArray 		= new JSONArray();
			
			if(seasonList!=null && seasonList.size() > 0){
				season 		= seasonList.get(0);
				this.lp_setDetailList(season);
				
				detailList 	= this.form.getDetailList();
				
				for(int i=0;i<detailList.size();i++){
					detail 			= detailList.get(i);
					objDetail 		= new JSONObject();
					
					objDetail.put("awayTeamName", 			detail.getAwayTeamNameTH());
					objDetail.put("totalSeating", 			detail.getTotalSeating());
					objDetail.put("bookingPrices", 			detail.getBookingPrices());
					
					detailJSONArray.add(objDetail);
				}
				
				obj.put("detail", 			detailJSONArray);
				
				this.form.setDataFlow(obj.toString());
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
	
	private void lp_changeSeason() throws EnjoyException{
		logger.info("[lp_changeSeason][Begin]");
		
		String							season				= null;
		JSONObject 						obj 				= null;
		JSONObject 						objDetail 			= null;
		JSONObject 						objSeason 			= null;
		List<DetailRevenueOfYearBean> 	detailList			= null;
		DetailRevenueOfYearBean			detail				= null;
		JSONArray 						detailJSONArray 	= null;
		JSONArray 						seasonJSONArray 	= null;
		List							seasonList			= null;
		
		try{
			season 					= EnjoyUtils.nullToStr(this.request.getParameter("season"));
			obj 					= new JSONObject();
			detailJSONArray 		= new JSONArray();
			seasonJSONArray 		= new JSONArray();
			
			if(!season.equals("")){
				this.lp_setDetailList(season);
				
				detailList 	= this.form.getDetailList();
				seasonList	= this.form.getSeasonList();
				
				obj.put("status", 			"SUCCESS");
				obj.put("season", 			season);
				
				for(int i=0;i<detailList.size();i++){
					detail 			= detailList.get(i);
					objDetail 		= new JSONObject();
					
					objDetail.put("awayTeamName", 			detail.getAwayTeamNameTH());
					objDetail.put("totalSeating", 			detail.getTotalSeating());
					objDetail.put("bookingPrices", 			detail.getBookingPrices());
					
					detailJSONArray.add(objDetail);
				}
				
				for(int i=0;i<seasonList.size();i++){
					objSeason = new JSONObject();
					
					objSeason.put("season", 			seasonList.get(i));
					
					seasonJSONArray.add(objSeason);
				}
				
				obj.put("detail", 			detailJSONArray);
				obj.put("seasonList", 		seasonJSONArray);
			}else{
				obj.put("status", 			"ERROR");
				obj.put("errMsg", 			"ฤดูกาลต้องไม่เท่ากับค่าว่าง");
			}
			
			
		}catch(Exception e){
			obj.put("status", 			"ERROR");
			obj.put("errMsg", 			e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("onLoad :: " + e.getMessage());
		}finally{
			season			= null;
			
			this.enjoyUtil.writeMSG(obj.toString());
			logger.info("[lp_changeSeason][End]");
		}
		
	}
	
	private void lp_setDetailList(String season) throws EnjoyException{
		logger.info("[lp_setDetailList][Begin]");
		List<DetailRevenueOfYearBean> 	detailList		= null;
		
		try{
			detailList	= this.dao.detailRevenueByYear(season);
			
			this.form.setSeason(season);
			this.form.setDetailList(detailList);
			
		}catch(Exception e){
			e.printStackTrace();
			throw new EnjoyException("lp_setDetailList :: " + e.getMessage());
		}finally{
			detailList		= null;
			logger.info("[lp_setDetailList][End]");
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
