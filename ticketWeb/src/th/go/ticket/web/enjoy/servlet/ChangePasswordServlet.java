package th.go.ticket.web.enjoy.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONObject;

import th.go.ticket.app.enjoy.bean.UserDetailsBean;
import th.go.ticket.app.enjoy.dao.UserDetailsDao;
import th.go.ticket.app.enjoy.exception.EnjoyException;
import th.go.ticket.app.enjoy.form.ChangePasswordForm;
import th.go.ticket.app.enjoy.main.Constants;
import th.go.ticket.app.enjoy.utils.EnjoyEncryptDecrypt;
import th.go.ticket.app.enjoy.utils.EnjoyLogger;
import th.go.ticket.app.enjoy.utils.EnjoyUtils;
import th.go.ticket.app.enjoy.utils.HibernateUtil;
import th.go.ticket.web.enjoy.common.EnjoyStandardSvc;
import th.go.ticket.web.enjoy.utils.EnjoyUtil;

public class ChangePasswordServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(ChangePasswordServlet.class);
	
    private static final String FORM_NAME = "changePasswordForm";
    
    private EnjoyUtil               	enjoyUtil                   = null;
    private HttpServletRequest          request                     = null;
    private HttpServletResponse         response                    = null;
    private HttpSession                 session                     = null;
    private UserDetailsBean             userBean                    = null;
    private UserDetailsDao				dao							= null;
    private ChangePasswordForm			form						= null;
    
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
             this.userBean           	= (UserDetailsBean)    session.getAttribute("userBean");
             this.form               	= (ChangePasswordForm) session.getAttribute(FORM_NAME);
             this.dao					= new UserDetailsDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new ChangePasswordForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
 				request.setAttribute("target", Constants.PAGE_URL +"/ChangePassScn.jsp");
 			}else if(pageAction.equals("save")){
 				this.saveUpdData();
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
		logger.info("[ChangePasswordServlet][onLoad][Begin]");
		
		try{			
			this.form.setOldUserPassword("");
			this.form.setNewUserPassword("");
			this.form.setConfirmUserPassword("");
//		}catch(EnjoyException e){
//			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("onLoad is error");
		}finally{			
			logger.info("[ChangePasswordServlet][onLoad][End]");
		}		
	}
	
	private void saveUpdData() throws Exception{
		logger.info("[ChangePasswordServlet][saveUpdRecord][Begin]");
		
		String				   oldUserPassword	    = null; 
		String				   newUserPassword	    = null; 
		SessionFactory 		   sessionFactory		= null;
		Session 			   session				= null;
		UserDetailsBean 	   userDetailsBean  	= null;
		JSONObject 			   obj 			    	= null;
		
		try{	 		
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			obj 				= new JSONObject();
			
			session.beginTransaction();
			
			oldUserPassword		= EnjoyUtils.nullToStr(this.request.getParameter("oldUserPassword"));
			newUserPassword		= EnjoyUtils.nullToStr(this.request.getParameter("newUserPassword"));
			userDetailsBean		= this.userBean;
			
			oldUserPassword		= EnjoyEncryptDecrypt.enCryption(userDetailsBean.getUserId(), oldUserPassword); // เอารหัสผ่านเดิมเข้ารหัส และนำไปเทียบว่าเท่ากับค่าเดิมหรือไม่
			newUserPassword		= EnjoyEncryptDecrypt.enCryption(userDetailsBean.getUserId(), newUserPassword); 
logger.info("oldUserPassword ==> " + oldUserPassword);
logger.info("userDetailsBean.getPwd() ==> " + userDetailsBean.getPwd());
			if (! oldUserPassword.equals(userDetailsBean.getPwd())) {
				userDetailsBean.setErrMsg("รหัสผ่านเดิมไม่ถูกต้อง");
				throw new EnjoyException(userDetailsBean.getErrMsg());				
			}
			
			userDetailsBean.setPwd(newUserPassword);
			this.dao.updateUserPassword(session, userDetailsBean);
			
			obj.put(STATUS, 			SUCCESS);
			
			session.getTransaction().commit();
			session.flush();			
		}catch(EnjoyException e){
			session.getTransaction().rollback();
			obj.put("status", 			"ERROR");
			obj.put("errMsg", 			e.getMessage());
			e.printStackTrace();
		}catch(Exception e){
			session.getTransaction().rollback();
			obj.put("status", 			"ERROR");
			obj.put("errMsg", 			"เกิดข้อผิดพลาดในการเปลี่ยนรหัสผ่าน");
			e.printStackTrace();
		}finally{ 
			session.close();
			sessionFactory		= null;
			session				= null;
			oldUserPassword	    = null; 
			newUserPassword	    = null; 
			userDetailsBean  	= null;
			
			this.enjoyUtil.writeMSG(obj.toString());

			logger.info("[ChangePasswordServlet][saveUpdRecord][End]");
		}
	}
}
