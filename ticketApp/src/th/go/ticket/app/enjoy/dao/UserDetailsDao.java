package th.go.ticket.app.enjoy.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.IntegerType;

import th.go.ticket.app.enjoy.bean.RefuserstatusBean;
import th.go.ticket.app.enjoy.bean.UserDetailsBean;
import th.go.ticket.app.enjoy.exception.EnjoyException;
import th.go.ticket.app.enjoy.model.Refuserstatus;
import th.go.ticket.app.enjoy.model.Userdetail;
import th.go.ticket.app.enjoy.model.Userprivilege;
import th.go.ticket.app.enjoy.utils.EnjoyEncryptDecrypt;
import th.go.ticket.app.enjoy.utils.EnjoyLogger;
import th.go.ticket.app.enjoy.utils.HibernateUtil;

public class UserDetailsDao {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(UserDetailsDao.class);
	
//	public static void main(String[] args) {
//		
//		SessionFactory 		sessionFactory	= null;
//		Session 			session			= null;
//		UserDetailsBean		userDetailsBean = null;
//		
//		try {
//			EnjoyLogger.initial(false);
//			sessionFactory 				= HibernateUtil.getSessionFactory();
//			session 					= sessionFactory.openSession();
//			userDetailsBean				= new UserDetailsBean();
//			
//			session.beginTransaction();
//			
//			updateUserDetail(session, userDetailsBean);
//			
//			session.getTransaction().commit();
//			
//			session.flush();
//			
//		} catch (EnjoyException e) {
//			// TODO Auto-generated catch block
//			session.getTransaction().rollback();
//			e.printStackTrace();
//		}finally{
//			session.close();
//		}
//	}
	
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
		DateFormat 			dateFormat		= null;
        Date 				date			= null;
		
		
		try{
		    passWord		= EnjoyEncryptDecrypt.enCryption(userId, pass);
logger.info("pass ==> " + passWord);
		    sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			hql				= "from Userdetail where userId = '" + userId + "'";
//			hql				= "from Userdetail where userId = '" + userId + "' and userPassword = '" + passWord + "'";
			userdetailList 	= session.createQuery(hql).list();
			maxRecord       = userdetailList.size();
			dateFormat 		= new SimpleDateFormat("dd/MM/yyyy");
		    date 	   		= new Date();
			
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
				logger.debug("[UserDetailsDao][userSelect] userdetail.getUserEmail() 			:: " + userdetail.getUserEmail());
				
				userDetailsBean.setUserUniqueId			(userdetail.getUserUniqueId());
				userDetailsBean.setUserId				(userdetail.getUserId());
				userDetailsBean.setUserName				(userdetail.getUserName());
				userDetailsBean.setUserSurname			(userdetail.getUserSurname());
				userDetailsBean.setUserPrivilege		(userdetail.getUserPrivilege());
				userDetailsBean.setUserLevel			(userdetail.getUserPrivilege());
				userDetailsBean.setUserStatus			(userdetail.getUserStatus());
				userDetailsBean.setFlagChangePassword	(userdetail.getFlagChangePassword());
				userDetailsBean.setCurrentDate			(dateFormat.format(date));
				userDetailsBean.setUserEmail			(userdetail.getUserEmail());
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
	
	/*ดึงสถานะมาอยู่ใน Combo*/
	public List<RefuserstatusBean> getRefuserstatusCombo(Session session) throws EnjoyException{
		logger.info("[getRefuserstatusCombo][Begin]");
		
		List<Refuserstatus> 		refuserstatusList		= null;
		String						hql						= null;
		RefuserstatusBean			bean					= null;
		List<RefuserstatusBean> 	refuserstatusBeanList	= null;
		
		try{
			
			hql						= "from Refuserstatus";
			refuserstatusList 		= session.createQuery(hql).list();
			refuserstatusBeanList	= new ArrayList<RefuserstatusBean>();
			
			for(Refuserstatus refuserstatus:refuserstatusList){
				bean = new RefuserstatusBean();
				
				bean.setUserStatusCode(String.valueOf(refuserstatus.getUserStatusCode()));
				bean.setUserStatusName(refuserstatus.getUserStatusName());
				
				refuserstatusBeanList.add(bean);
			}
			
			
		}catch(Exception e){
			logger.info("[getRefuserstatusCombo] " + e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการดึงสถานะมาอยู่ใน Combo");
		}finally{
			hql						= null;
			logger.info("[getRefuserstatusCombo][End]");
		}
		
		return refuserstatusBeanList;
		
	}
	
	
	/*สิทธิในการใช้งานในระบบ*/
	public List<Userprivilege> getUserprivilege(Session session) throws EnjoyException{
		logger.info("[getUserprivilege][Begin]");
		
		List<Userprivilege> 	refuserstatusList		= null;
		String					hql						= null;
		
		try{
			
			hql					= "from Userprivilege";
			refuserstatusList 	= session.createQuery(hql).list();
			
		}catch(Exception e){
			logger.info("[getUserprivilege] " + e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการดึงสิทธิในการใช้งานในระบบ");
		}finally{
			hql						= null;
			logger.info("[getUserprivilege][End]");
		}
		
		return refuserstatusList;
		
	}
	
	/*ดึงรายละเอียดของผู้ใช้*/
	public Userdetail getUserdetail(Session session, int userUniqueId) throws EnjoyException{
		logger.info("[getUserdetail][Begin]");
		
		List<Userdetail> 	refuserstatusList		= null;
		String				hql						= null;
		Userdetail			userdetail				= null;
		
		try{
			
			hql					= "from Userprivilege userUniqueId = " + userUniqueId;
			refuserstatusList 	= session.createQuery(hql).list();
			
			if(refuserstatusList!=null && refuserstatusList.size() > 0){
				userdetail = refuserstatusList.get(0);
			}
			
		}catch(Exception e){
			logger.info("[getUserdetail] " + e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการดึงรายละเอียดผู้ใช้");
		}finally{
			hql						= null;
			logger.info("[getUserdetail][End]");
		}
		
		return userdetail;
		
	}
	
	public int checkDupUserId(Session session, String userId) throws EnjoyException{
		logger.info("[checkDupUserId][Begin]");
		
		List<String> 					returnList 							= null;
		String							hql									= null;
		List<Integer>			 		list								= null;
		SQLQuery 						query 								= null;
		int 							result								= 0;
		
		
		try{
			returnList		= new ArrayList<String>();
			
			hql				= "Select count(*) cou from userdetails where userId = '" + userId + "'";
			query			= session.createSQLQuery(hql);
			
			query.addScalar("cou"			, new IntegerType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				result = list.get(0);
			}
			
			logger.info("[checkDupUserId] result 			:: " + result);
			
			
			
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			
			hql									= null;
			list								= null;
			query 								= null;
			logger.info("[checkDupUserId][End]");
		}
		
		return result;
	}
	
	public void insertNewUser(Session session, UserDetailsBean userDetailsBean) throws EnjoyException{
		logger.info("[insertNewUser][Begin]");
		
		Userdetail						userdetailDb						= null;
		
		try{
			
			userdetailDb = new Userdetail();
			
			userdetailDb.setUserId(userDetailsBean.getUserId());
			userdetailDb.setUserPassword(userDetailsBean.getPwd());
			userdetailDb.setUserName(userDetailsBean.getUserName());
			userdetailDb.setUserSurname(userDetailsBean.getUserSurname());
			userdetailDb.setUserEmail(userDetailsBean.getUserEmail());
			userdetailDb.setUserPrivilege(userDetailsBean.getUserPrivilege());
			userdetailDb.setUserLevel(userDetailsBean.getUserLevel());
			userdetailDb.setUserStatus(userDetailsBean.getUserStatus());
			userdetailDb.setFlagChangePassword(userDetailsBean.getFlagChangePassword());
			
			
			session.saveOrUpdate(userdetailDb);
			
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการบันทึกข้อมูล");
		}finally{
			
			userdetailDb = null;
			logger.info("[insertNewUser][End]");
		}
	}
	
	public void updateUserDetail(Session session, UserDetailsBean userDetailsBean) throws EnjoyException{
		logger.info("[updateUserDetail][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		int 							result								= 0;
		
		
		try{
			hql				= "update  Userdetail set userId 			= :userId"
												+ ", userName			= :userName"
												+ ", userSurname		= :userSurname"
												+ ", userEmail			= :userEmail"
												+ ", userPrivilege		= :userPrivilege"
												+ ", userLevel			= :userLevel"
												+ ", userStatus			= :userStatus"
												+ ", flagChangePassword = :flagChangePassword"
										+ " where userUniqueId = :userUniqueId";
			
			query = session.createQuery(hql);
			query.setParameter("userId"				, userDetailsBean.getUserId());
			query.setParameter("userName"			, userDetailsBean.getUserName());
			query.setParameter("userSurname"		, userDetailsBean.getUserSurname());
			query.setParameter("userEmail"			, userDetailsBean.getUserEmail());
			query.setParameter("userPrivilege"		, userDetailsBean.getUserPrivilege());
			query.setParameter("userLevel"			, userDetailsBean.getUserLevel());
			query.setParameter("userStatus"			, userDetailsBean.getUserStatus());
			query.setParameter("flagChangePassword"	, userDetailsBean.getFlagChangePassword());
			query.setParameter("userUniqueId"		, userDetailsBean.getUserUniqueId());
			
			result = query.executeUpdate();
			
			
			
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการอัพเดทข้อมูล");
		}finally{
			
			hql									= null;
			query 								= null;
			logger.info("[updateUserDetail][End]");
		}
	}
	
	public int lastId(Session session) throws EnjoyException{
		logger.info("[lastId][Begin]");
		
		List<String> 					returnList 							= null;
		String							hql									= null;
		List<Integer>			 		list								= null;
		SQLQuery 						query 								= null;
		int 							result								= 0;
		
		
		try{
			returnList		= new ArrayList<String>();
			
			hql				= "Select max(userUniqueId) lastId from userdetails";
			query			= session.createSQLQuery(hql);
			
			query.addScalar("lastId"			, new IntegerType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				result = list.get(0);
			}
			
			logger.info("[lastId] result 			:: " + result);
			
			
			
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			
			hql									= null;
			list								= null;
			query 								= null;
			logger.info("[lastId][End]");
		}
		
		return result;
	}
	
	
}
