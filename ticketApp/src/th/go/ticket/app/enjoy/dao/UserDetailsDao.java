package th.go.ticket.app.enjoy.dao;

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
        String				passWord		= null;
		int					maxRecord		= 0;
		try{
		    passWord		= EnjoyEncryptDecrypt.enCryption(userId, pass);
logger.info("pass ==> " + passWord);
		    sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			hql				= "from Userdetail where userId = '" + userId + "'";
			userdetailList 	= session.createQuery(hql).list();
			maxRecord       = userdetailList.size();
			
			for(int i=0;i<maxRecord;i++){
				userdetail 		= userdetailList.get(i);
				userDetailsBean	= new UserDetailsBean();
				
				logger.debug("[UserDetailsDao][userSelect] userdetail.getUserUniqueId() 		:: " + userdetail.getUserUniqueId());
				logger.debug("[UserDetailsDao][userSelect] userdetail.getUserId() 				:: " + userdetail.getUserId());
				logger.debug("[UserDetailsDao][userSelect] userdetail.getUserName() 			:: " + userdetail.getUserName());
				logger.debug("[UserDetailsDao][userSelect] userdetail.getUserSurname() 			:: " + userdetail.getUserSurname());
				logger.debug("[UserDetailsDao][userSelect] userdetail.getUserPrivilege() 		:: " + userdetail.getUserPrivilege());
				logger.debug("[UserDetailsDao][userSelect] userdetail.getUserLevel() 			:: " + userdetail.getUserLevel());
				logger.debug("[UserDetailsDao][userSelect] userdetail.getUserStatus() 			:: " + userdetail.getUserStatus());
				logger.debug("[UserDetailsDao][userSelect] userdetail.getFlagChangePassword() 	:: " + userdetail.getFlagChangePassword());
				
				userDetailsBean.setUserUniqueId(userdetail.getUserUniqueId());
				userDetailsBean.setUserId(userdetail.getUserId());
				userDetailsBean.setUserName(userdetail.getUserName());
				userDetailsBean.setUserSurname(userdetail.getUserSurname());
				userDetailsBean.setUserPrivilege(userdetail.getUserPrivilege());
				userDetailsBean.setUserLevel(userdetail.getUserPrivilege());
				userDetailsBean.setUserStatus(userdetail.getUserStatus());
				userDetailsBean.setFlagChangePassword(userdetail.getFlagChangePassword());
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			userdetailList	= null;
			userdetail		= null;
			hql				= null;
	        passWord		= null;
			logger.info("[UserDetailsDao][userSelect][End]");
		}
		
		return userDetailsBean;
	}
}
