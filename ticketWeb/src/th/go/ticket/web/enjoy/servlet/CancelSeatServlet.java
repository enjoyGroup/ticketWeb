package th.go.ticket.web.enjoy.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import th.go.ticket.app.enjoy.bean.UserDetailsBean;
import th.go.ticket.app.enjoy.dao.CancelSeatDao;
import th.go.ticket.app.enjoy.exception.EnjoyException;
import th.go.ticket.app.enjoy.form.CancelSeatForm;
import th.go.ticket.app.enjoy.form.UserDetailsMaintananceForm;
import th.go.ticket.app.enjoy.main.Constants;
import th.go.ticket.app.enjoy.model.Userprivilege;
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
             
 			if(this.form == null || pageAction.equals("new") || pageAction.equals("getUserDetail")) this.form = new CancelSeatForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
				request.setAttribute("target", Constants.PAGE_URL +"/CancelSeatScn.jsp");
// 			}else if(pageAction.equals("searchUserDetail")){
// 				this.onSearchUserDetail();
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
		
		SessionFactory 		sessionFactory	= null;
		Session 			session			= null;
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			
			this.form.setFieldZoneList(this.dao.getFieldZoneMaster());
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("setRefference is error");
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			logger.info("[setRefference][End]");
		}
	}
	
//	
//	private void onSearchUserDetail() throws EnjoyException{
//		logger.info("[onSearchUserDetail][Begin]");
//		
//		UserDetailsBean 			userdetailForm		= null;
//		Userprivilege				userprivilege		= null;
//		SessionFactory 				sessionFactory		= null;
//		Session 					session				= null;
//		List<UserDetailsBean> 		listUserDetailsBean = null;
//		List<Userprivilege> 		listUserprivilege   = null;
//		Hashtable<String, String> 	fUserprivilege		= null;
//
//		try{
//			listUserDetailsBean 		= new ArrayList<UserDetailsBean>();
//			listUserprivilege   		= new ArrayList<Userprivilege>();
//			fUserprivilege				= new Hashtable<String, String>();
//
//			sessionFactory 				= HibernateUtil.getSessionFactory();
//			session 					= sessionFactory.openSession();			
//			session.beginTransaction();
//			
//			userdetailForm				= new UserDetailsBean();
//			
//			userdetailForm.setUserName	(EnjoyUtils.nullToStr(this.request.getParameter("userName")));
//			userdetailForm.setUserId	(EnjoyUtils.nullToStr(this.request.getParameter("userId")));
//			userdetailForm.setUserStatus(EnjoyUtils.nullToStr(this.request.getParameter("userStatus")));
//			
//			logger.info("[onSearchUserDetail] userdetailForm.getUserName() 	 :: " + userdetailForm.getUserName());
//			logger.info("[onSearchUserDetail] userdetailForm.getUserId() 	 :: " + userdetailForm.getUserId());
//			logger.info("[onSearchUserDetail] userdetailForm.getUserStatus() :: " + userdetailForm.getUserStatus());
//			
//			listUserprivilege 			= this.dao.getUserprivilege(session);
//			for(int i=0;i<listUserprivilege.size();i++){
//				userprivilege			= listUserprivilege.get(i);
//				fUserprivilege.put(userprivilege.getPrivilegeCode() , userprivilege.getPrivilegeName());
//			}	
//			listUserDetailsBean	 		= this.dao.getListUserdetail(session, userdetailForm, fUserprivilege);
//
//			//logger.info("[onSearchUserDetail] listUserDetailsBean.size :: " + listUserDetailsBean.size());
//			
//			if(listUserDetailsBean.size() > 0){				
//				this.form.setUserDetailsBeanList(listUserDetailsBean);		
//				//logger.info("มีข้อมูลจะแสดงคือ :: " + this.form.getUserDetailsBeanList().size());
//			}else{
//				throw new EnjoyException("ไม่พบรายการตามเงื่อนไขที่ระบุ");
//			}			
//		}catch(EnjoyException e){
//			throw new EnjoyException(e.getMessage());
//		}catch(Exception e){
//			logger.info(e.getMessage());
//			throw new EnjoyException("onSearchUserDetail is error");
//		}finally{
//			session.close();
//			sessionFactory	= null;
//			session			= null;
//			
//			this.setRefference();
//			logger.info("[onSearchUserDetail][End]");
//		}
//		
//	}
	
}
