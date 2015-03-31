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
import th.go.ticket.app.enjoy.form.UserDetailsMaintananceForm;
import th.go.ticket.app.enjoy.main.Constants;
import th.go.ticket.app.enjoy.model.Userdetail;
import th.go.ticket.app.enjoy.utils.EnjoyLogger;
import th.go.ticket.app.enjoy.utils.HibernateUtil;
import th.go.ticket.web.enjoy.common.EnjoyStandardSvc;
import th.go.ticket.web.enjoy.utils.EnjoyUtil;

public class UserDetailsMaintananceServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(UserDetailsMaintananceServlet.class);
	
    private static final String FORM_NAME = "userDetailsMaintananceForm";
    
    private EnjoyUtil               	enjoyUtil                   = null;
    private HttpServletRequest          request                     = null;
    private HttpServletResponse         response                    = null;
    private HttpSession                 session                     = null;
    private UserDetailsBean             userBean                    = null;
    private UserDetailsDao				dao							= null;
    private UserDetailsMaintananceForm	form						= null;
    
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
             this.form               	= (UserDetailsMaintananceForm)session.getAttribute(FORM_NAME);
             this.dao					= new UserDetailsDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new UserDetailsMaintananceForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
 				request.setAttribute("target", Constants.PAGE_URL +"/UserDetailsMaintananceScn.jsp");
 			}else if(pageAction.equals("getUserDetail")){
 				this.onGetUserDetail();
 				request.setAttribute("target", Constants.PAGE_URL +"/UserDetailsMaintananceScn.jsp");
 			}else if(pageAction.equals("checkDupUserId")){
 				this.checkDupUserId();
 			}else if(pageAction.equals("save")){
 				this.onSave();
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
			
			this.form.getUserDetailsBean().setUserStatus("1");
			this.setRefference();
			
			this.form.setTitlePage("เพิ่มผู้ใช้งานระบบ");
			
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
			
			this.form.setRefuserstatusCombo(this.dao.getRefuserstatusCombo(session));
			this.form.setUserprivilegeList(this.dao.getUserprivilege(session));
			
			
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
	
	private void onGetUserDetail() throws EnjoyException{
		logger.info("[onGetUserDetail][Begin]");
		
		int 				userUniqueId 	= 0;
		Userdetail 			userdetailDb	= null;
		SessionFactory 		sessionFactory	= null;
		Session 			session			= null;
		UserDetailsBean		userDetailsBean	= null;
		
		try{
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			userUniqueId 				= EnjoyUtil.nullToStr(request.getParameter("userUniqueId")).equals("")?0:Integer.parseInt(request.getParameter("userUniqueId"));
			userdetailDb				= this.dao.getUserdetail(session, userUniqueId);
			userDetailsBean				= this.form.getUserDetailsBean();
			
			this.form.setTitlePage("แก้ไขผู้ใช้งานระบบ");
			this.form.setPageMode(UserDetailsMaintananceForm.EDIT);
			
			if(userdetailDb!=null){
				userDetailsBean.setUserUniqueId			(userdetailDb.getUserUniqueId());
				userDetailsBean.setUserId				(userdetailDb.getUserId());
				userDetailsBean.setUserName				(userdetailDb.getUserName());
				userDetailsBean.setUserSurname			(userdetailDb.getUserSurname());
				userDetailsBean.setUserPrivilege		(userdetailDb.getUserPrivilege());
				userDetailsBean.setUserLevel			(userdetailDb.getUserLevel());
				userDetailsBean.setUserStatus			(userdetailDb.getUserStatus());
				userDetailsBean.setFlagChangePassword	(userdetailDb.getFlagChangePassword());
				userDetailsBean.setUserEmail			(userdetailDb.getUserEmail());
				
			}else{
				throw new EnjoyException("เกิดข้อผิดพลาดในการดึงข้อมูลผู้ใช้งาน");
			}
			
			this.setRefference();
			
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("onGetUserDetail is error");
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			logger.info("[onGetUserDetail][End]");
		}
		
	}
	
	private void checkDupUserId() throws EnjoyException{
		logger.info("[checkDupUserId][Begin]");
		
		String 				userId 			= null;
		int 				cou				= 0;
		SessionFactory 		sessionFactory	= null;
		Session 			session			= null;
		JSONObject 			obj 			= null;
		
		try{
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			userId 						= EnjoyUtil.nullToStr(request.getParameter("userId"));
			cou							= this.dao.checkDupUserId(session, userId);
			obj 						= new JSONObject();
			
			obj.put(STATUS, 		SUCCESS);
			obj.put("COU", 			cou);
			
			
		}catch(EnjoyException e){
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		"checkDupUserId is error");
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			
			this.enjoyUtil.writeMSG(obj.toString());
			
			logger.info("[checkDupUserId][End]");
		}
		
	}
	
	
	private void onSave() throws EnjoyException{
		logger.info("[onSave][Begin]");
		
		String				pageMode			= null;
		int					userUniqueId		= 0;
		String				userName			= null;
		String				userSurname			= null;
		String 				userId 				= null;
		String				userEmail			= null;
		String				userStatus			= null;
		String				flagChangePassword 	= null;
		String				userPrivilege		= null;
		SessionFactory 		sessionFactory		= null;
		Session 			session				= null;
		JSONObject 			obj 				= null;
		UserDetailsBean 	userDetailsBean		= null;
		
		try{
			pageMode 					= EnjoyUtil.nullToStr(request.getParameter("pageMode"));
			userName 					= EnjoyUtil.nullToStr(request.getParameter("userName"));
			userSurname 				= EnjoyUtil.nullToStr(request.getParameter("userSurname"));
			userId 						= EnjoyUtil.nullToStr(request.getParameter("userId"));
			userEmail 					= EnjoyUtil.nullToStr(request.getParameter("userEmail"));
			userStatus 					= EnjoyUtil.nullToStr(request.getParameter("userStatus"));
			flagChangePassword 			= EnjoyUtil.chkBoxtoDb(request.getParameter("flagChangePassword"));
			userPrivilege 				= EnjoyUtil.nullToStr(request.getParameter("hidUserPrivilege"));
			userUniqueId 				= EnjoyUtil.paresInt(request.getParameter("userUniqueId"));
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			obj 						= new JSONObject();
			userDetailsBean				= new UserDetailsBean();
			
			logger.info("[onSave] pageMode 				:: " + pageMode);
			logger.info("[onSave] userName 				:: " + userName);
			logger.info("[onSave] userSurname 			:: " + userSurname);
			logger.info("[onSave] userId 				:: " + userId);
			logger.info("[onSave] userEmail 			:: " + userEmail);
			logger.info("[onSave] userStatus 			:: " + userStatus);
			logger.info("[onSave] flagChangePassword 	:: " + flagChangePassword);
			logger.info("[onSave] userPrivilege 		:: " + userPrivilege);
			logger.info("[onSave] userUniqueId 			:: " + userUniqueId);
			
			userDetailsBean.setUserName(userName);
			userDetailsBean.setUserSurname(userSurname);
			userDetailsBean.setUserId(userId);
			userDetailsBean.setUserEmail(userEmail);
			userDetailsBean.setUserStatus(userStatus);
			userDetailsBean.setFlagChangePassword(flagChangePassword);
			userDetailsBean.setUserPrivilege(userPrivilege);
			userDetailsBean.setUserUniqueId(userUniqueId);
			
			session.beginTransaction();
			
			if(pageMode.equals(UserDetailsMaintananceForm.NEW)){
				this.dao.insertNewUser(session, userDetailsBean);
				
				userUniqueId = this.dao.lastId(session);
			}else{
				this.dao.updateUserDetail(session, userDetailsBean);
			}
			
			obj.put(STATUS, 			SUCCESS);
			obj.put("userUniqueId", 	userUniqueId);
			
			session.getTransaction().commit();
			session.flush();
			
		}catch(EnjoyException e){
			session.getTransaction().rollback();
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		"onSave is error");
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			
			this.enjoyUtil.writeMSG(obj.toString());
			
			logger.info("[onSave][End]");
		}
	}
	
	
	
	
	
	
	
	
	
}
