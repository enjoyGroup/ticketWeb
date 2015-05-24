package th.go.ticket.web.enjoy.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import th.go.ticket.app.enjoy.bean.SummaryRevenueOfYearBean;
import th.go.ticket.app.enjoy.bean.UserDetailsBean;
import th.go.ticket.app.enjoy.dao.SummaryRevenueOfYearDao;
import th.go.ticket.app.enjoy.exception.EnjoyException;
import th.go.ticket.app.enjoy.form.SummaryRevenueOfYearForm;
import th.go.ticket.app.enjoy.main.Constants;
import th.go.ticket.app.enjoy.utils.EnjoyLogger;
import th.go.ticket.app.enjoy.utils.EnjoyUtils;
import th.go.ticket.web.enjoy.common.EnjoyStandardSvc;
import th.go.ticket.web.enjoy.utils.EnjoyUtil;

public class SummaryRevenueOfYearServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(SummaryRevenueOfYearServlet.class);
	
    private static final String FORM_NAME = "summaryRevenueOfYearForm";
    
    private EnjoyUtil               	easUtil                     = null;
    private HttpServletRequest          request                     = null;
    private HttpServletResponse         response                    = null;
    private HttpSession                 session                     = null;
    private UserDetailsBean             userBean                    = null;
    private SummaryRevenueOfYearDao		dao							= null;
    private SummaryRevenueOfYearForm	form						= null;
    
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
             this.form               	= (SummaryRevenueOfYearForm)session.getAttribute(FORM_NAME);
             this.dao					= new SummaryRevenueOfYearDao();
 			
             logger.info("[execute][Begin] : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new SummaryRevenueOfYearForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
 				request.setAttribute("target", Constants.PAGE_URL +"/SummaryRevenueOfYearScn.jsp");
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
		
		List<SummaryRevenueOfYearBean> 	resultList 			= null;
		JSONObject 						obj 				= null;
		JSONObject 						objDataFlow 		= null;
		JSONArray 						dataFlowJSONArray 	= null;
		SummaryRevenueOfYearBean		bean				= null;
		SummaryRevenueOfYearBean		beanTest				= null;
		
		try{
			obj 					= new JSONObject();
			dataFlowJSONArray 		= new JSONArray();
			
			resultList = this.dao.summaryRevenueOfYear();
			
//			beanTest = new SummaryRevenueOfYearBean();
//			
//			beanTest.setSeason("2558");
//			beanTest.setBookingPrice("8500");
//			
//			resultList.add(beanTest);
			
			
			for(int i=0;i<resultList.size();i++){
				bean 			= resultList.get(i);
				objDataFlow 	= new JSONObject();
				
				objDataFlow.put("season", 			bean.getSeason());
				objDataFlow.put("bookingPrice", 	bean.getBookingPrice());
				
				bean.setBookingPrice(EnjoyUtils.convertFloatToDisplay(bean.getBookingPrice(), 2));
				
				dataFlowJSONArray.add(objDataFlow);
			}
			
			obj.put("dataFlow", 		dataFlowJSONArray);
			
			System.out.println(obj.toString());
			
			this.form.setDataFlow(obj.toString());
			
			this.form.setResultList(resultList);
			
		}catch(Exception e){
			throw new EnjoyException("onLoad :: " + e.getMessage());
		}finally{
			logger.info("[onLoad][End]");
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
