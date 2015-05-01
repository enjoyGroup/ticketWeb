package th.go.ticket.web.enjoy.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import th.go.ticket.app.enjoy.bean.UserDetailsBean;
import th.go.ticket.app.enjoy.bean.UserPrivilegeBean;
import th.go.ticket.app.enjoy.dao.UserDetailsDao;
import th.go.ticket.app.enjoy.dao.UserPrivilegeDao;
import th.go.ticket.app.enjoy.exception.EnjoyException;
import th.go.ticket.app.enjoy.utils.EnjoyLogger;
import th.go.ticket.web.enjoy.common.EnjoyStandardSvc;
import th.go.ticket.web.enjoy.utils.EnjoyUtil;

 public class LoginServlet extends EnjoyStandardSvc {
	 
   static final long serialVersionUID = 1L;
   private static final EnjoyLogger logger = EnjoyLogger.getLogger(LoginServlet.class);

   @Override
   public void execute(HttpServletRequest request, HttpServletResponse response)throws Exception {
	   doProcess(request, response);
   }

	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		String 				userId 				= null;
        String 				passWord 			= null;
        HttpSession 		session 			= request.getSession(true);
        UserDetailsBean		userBean 			= null;
        UserDetailsDao	 	userDao 			= null;
        UserPrivilegeDao	userPrivilegeDao 	= null;
        EnjoyUtil           easUtil 			= null;
		JSONObject 			obj 				= null;
        				
        try{
			obj 				= new JSONObject();
        	easUtil 			= new EnjoyUtil(request, response);
        	userId 				= EnjoyUtil.nullToStr(request.getParameter("userId"));
        	passWord 			= EnjoyUtil.nullToStr(request.getParameter("passWord"));
        	userDao				= new UserDetailsDao();
        	userPrivilegeDao 	= new UserPrivilegeDao();
			obj 				= new JSONObject();
        	this.checkExpiryDate();
        	
        	logger.info("[execute] userId 	:: " + userId);
        	logger.info("[execute] passWord 	:: " + passWord);
        	
        	userBean = userDao.userSelect(userId, passWord);
        	
        	if(userBean==null){
    			obj.put(STATUS, 		ERROR);
    			obj.put(ERR_MSG, 		"ไม่สามารถเข้าสู่ระบบได้ กรุณาตรวจสอบ user/password ใหม่อีกครั้ง");
        		//easUtil.writeMSG("ไม่สามารถเข้าสู่ระบบได้ กรุณาตรวจสอบ user/password ใหม่อีกครั้ง");
        	}else{      		
        		userBean.setUserPrivilegeList((ArrayList<UserPrivilegeBean>) userPrivilegeDao.userPrivilegeListSelect(userBean.getUserPrivilege()));
        		session.setAttribute("userBean", userBean);
        		//easUtil.writeMSG("OK");
    			obj.put(STATUS, 		SUCCESS);
    			obj.put("FlagChange", 	userBean.getFlagChangePassword());
        	}       	
        }catch(EnjoyException e){
        	e.printStackTrace();
        	logger.info(e.getMessage());
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		e.getMessage());
        	//easUtil.writeMSG(e.getMessage());
        }catch(Exception e){
        	e.printStackTrace();
        	logger.info(e.getMessage());
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		e.getMessage());
        	//easUtil.writeMSG(e.getMessage());
        }finally{
        	easUtil.writeMSG(obj.toString());
    		userId 				= null;
            passWord 			= null;
            userBean 			= null;
            userDao 			= null;
            userPrivilegeDao 	= null;
            easUtil 			= null;
    		obj 				= null;
        	logger.info("[EnjoyLoginSvc][execute][End]");
        }
	}
	
	private void checkExpiryDate() throws EnjoyException, Exception{
		Date	  			currDate			= new Date();
		Date	  			expDate				= null;
		SimpleDateFormat    dt              	= null;
		Calendar 			currDateC 			= Calendar.getInstance(Locale.US);
		
		try{
			dt      	= new SimpleDateFormat("yyyyMMdd",Locale.US); 
			expDate     = dt.parse("20150130"); 
			
			currDateC.setTime(currDate);
			
//			if(currDateC.getTime().after(expDate)){
			if("1".equals("2")){
				logger.info("[checkExpiryDate] Expiry");
				throw new EnjoyException("เกิดข้อผิดพลาดในการเข้าสู่ระบบ");
			}
            
		}catch(EnjoyException e){
			throw e;
		}catch(Exception e){
			throw e;
		}
	}
	
	
}