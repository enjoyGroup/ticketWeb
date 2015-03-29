package th.go.ticket.app.enjoy.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import th.go.ticket.app.enjoy.bean.UserDetailsBean;
import th.go.ticket.app.enjoy.model.Userdetail;
import th.go.ticket.app.enjoy.utils.EnjoyEncryptDecrypt;
import th.go.ticket.app.enjoy.utils.EnjoyLogger;
import th.go.ticket.app.enjoy.utils.HibernateUtil;

public class UserDetailsDao {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(UserDetailsDao.class);
	
	public UserDetailsBean userSelect(String userId, String pass){
		logger.info("[UserDetailsDao][userSelect][Begin]");
		
		UserDetailsBean 	userDetailsBean = null;
		SessionFactory 		sessionFactory	= null;
		Session 			session			= null;
		List<Userdetail> 	userdetailList	= null;
		Userdetail 			userdetail		= null;
		String				hql				= null;
		DateFormat 			dateFormat		= null;
        Date 				date			= null;
        String				passWord		= null;
		
		try{
			passWord		= EnjoyEncryptDecrypt.enCryption(userId, pass);
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			hql				= "from Userdetail where userId = '" + userId + "' and userPassword = '" + passWord + "'";
			userdetailList 	= session.createQuery(hql).list();
			dateFormat 		= new SimpleDateFormat("dd/MM/yyyy");
		    date 	   		= new Date();
			
		    logger.info(passWord);
			
			for(int i=0;i<userdetailList.size();i++){
				userdetail 		= userdetailList.get(i);
				userDetailsBean	= new UserDetailsBean();
				
				logger.info("[UserDetailsDao][userSelect] userdetail.getUserUniqueId() 	:: " + userdetail.getUserUniqueId());
				logger.info("[UserDetailsDao][userSelect] userdetail.getUserId() 		:: " + userdetail.getUserId());
				logger.info("[UserDetailsDao][userSelect] userdetail.getUserName() 		:: " + userdetail.getUserName());
				logger.info("[UserDetailsDao][userSelect] userdetail.getUserSurname() 	:: " + userdetail.getUserSurname());
				logger.info("[UserDetailsDao][userSelect] userdetail.getUserPrivilege() :: " + userdetail.getUserPrivilege());
				logger.info("[UserDetailsDao][userSelect] userdetail.getUserStatus() 	:: " + userdetail.getUserStatus());
				
				userDetailsBean.setUserUniqueId(userdetail.getUserUniqueId());
				userDetailsBean.setUserId(userdetail.getUserId());
				userDetailsBean.setUserName(userdetail.getUserName());
				userDetailsBean.setUserSurname(userdetail.getUserSurname());
				userDetailsBean.setUserPrivilege(userdetail.getUserPrivilege());
				userDetailsBean.setUserStatus(userdetail.getUserStatus());
				userDetailsBean.setCurrentDate(dateFormat.format(date));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
			logger.info("[UserDetailsDao][userSelect][End]");
		}
		
		return userDetailsBean;
	}
}
