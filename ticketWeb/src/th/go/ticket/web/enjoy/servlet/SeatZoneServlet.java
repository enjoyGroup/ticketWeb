package th.go.ticket.web.enjoy.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import th.go.ticket.app.enjoy.bean.SeatZoneBean;
import th.go.ticket.app.enjoy.bean.UserDetailsBean;
import th.go.ticket.app.enjoy.dao.SeatZoneDao;
import th.go.ticket.app.enjoy.exception.EnjoyException;
import th.go.ticket.app.enjoy.form.SeatZoneForm;
import th.go.ticket.app.enjoy.main.Constants;
import th.go.ticket.app.enjoy.utils.EnjoyLogger;
import th.go.ticket.web.enjoy.common.EnjoyStandardSvc;
import th.go.ticket.web.enjoy.utils.EnjoyUtil;

public class SeatZoneServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(SeatZoneServlet.class);
	
    private static final String FORM_NAME = "seatZoneForm";
    
    private EnjoyUtil               	easUtil                     = null;
    private HttpServletRequest          request                     = null;
    private HttpServletResponse         response                    = null;
    private HttpSession                 session                     = null;
    private UserDetailsBean             userBean                    = null;
    private SeatZoneDao					dao							= null;
    private SeatZoneForm				form						= null;
    
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
             this.form               	= (SeatZoneForm)session.getAttribute(FORM_NAME);
             this.dao					= new SeatZoneDao();
 			
             logger.info("[execute][Begin] : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new SeatZoneForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
 				request.setAttribute("target", Constants.PAGE_URL +"/SeatZoneScn.jsp");
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
		
		List<SeatZoneBean> 				resultList 			= null;
		List<SeatZoneBean> 				list 				= null;
		String							season				= null;
		Map								matchMap			= null;
		boolean							defaultMatch		= true;
		
		try{
			resultList 	= this.dao.seasonList();
			matchMap	= this.form.getMatchMap();
			
			for(SeatZoneBean bean:resultList){
				season = bean.getSeason();
				if(matchMap.containsKey(season)){
					list = (List<SeatZoneBean>) matchMap.get(season);
					
					list.add(bean);
					matchMap.put(season, list);
					
				}else{
					list = new ArrayList<SeatZoneBean>();
					
					list.add(bean);
					matchMap.put(season, list);
				}
				
				if(defaultMatch){
					this.form.setMatchId(bean.getMatchId());
					
					defaultMatch = false;
				}
				
				
			}
			
			this.form.setFieldZoneList(this.dao.getFieldZoneMaster());
			
		}catch(Exception e){
			throw new EnjoyException("onLoad :: " + e.getMessage());
		}finally{
			logger.info("[onLoad][End]");
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
