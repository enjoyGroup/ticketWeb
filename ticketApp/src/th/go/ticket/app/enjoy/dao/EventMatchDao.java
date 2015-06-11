package th.go.ticket.app.enjoy.dao;

import java.util.ArrayList;
import java.util.List;
 
 
 














import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory; 
import org.hibernate.Transaction;
import org.hibernate.type.IntegerType; 
  







import org.hibernate.type.StringType;

import th.go.ticket.app.enjoy.bean.DetailRevenueOfYearBean;
import th.go.ticket.app.enjoy.bean.EventMatchBean;  
import th.go.ticket.app.enjoy.exception.EnjoyException; 
import th.go.ticket.app.enjoy.model.Eventmatch;
import th.go.ticket.app.enjoy.model.EventmatchPK;
import th.go.ticket.app.enjoy.utils.EnjoyLogger;
import th.go.ticket.app.enjoy.utils.EnjoyUtils;
import th.go.ticket.app.enjoy.utils.HibernateUtil;

public class EventMatchDao {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(EventMatchDao.class);
 
	 
	public List<String> seasonList() throws EnjoyException{
		logger.info("[seasonList][Begin]");
		
		List<String> 					returnList 							= null;
		SessionFactory 					sessionFactory						= null;
		Session 						session								= null;
		String							hql									= null;
		List<String>			 		list								= null;
		SQLQuery 						query 								= null;
		String							season								= null;
		
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			returnList		= new ArrayList<String>();
			
			hql				= "SELECT b.season FROM eventmatch b GROUP BY b.season order by  b.season desc";
			query			= session.createSQLQuery(hql);
			
			query.addScalar("season"			, new StringType());
			
			list		 	= query.list();
			
			logger.info("[seasonList] list :: " + list.size());
			for(int i=0;i<list.size();i++){
				season 	= list.get(i);
				logger.info("[seasonList] season :: " + season);
				
				returnList.add(season);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			session.close(); 
			sessionFactory						= null;
			session								= null;
			hql									= null;
			list								= null;
			query 								= null;
			logger.info("[seasonList][End]");
		}
		
		return returnList;
	}
	 
 
	public List<EventMatchBean> detailEventMatchByYear(String season) throws EnjoyException{
		logger.info("[detailEventMatchByYear][Begin] and season = " + season);
		
		List<EventMatchBean> 	returnList 							= null;
		SessionFactory 			sessionFactory						= null;
		Session 				session								= null;
		String					hql									= null; 
		EventMatchBean		    returnObj							= null;
		List<Object[]>			list								= null;
		SQLQuery 				query 								= null;
		int                     genId                               = 1;
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			returnList		= new ArrayList<EventMatchBean>();
            int seasonInt   = Integer.valueOf(season);
  
            hql				= "select * from Eventmatch WHERE season  = "+ season + " and activeFlag = 'A'" + " order by matchId" ; 
            query			= session.createSQLQuery(hql);
            System.out.println("[detailEventMatchByYear][Begin] hql = " + hql);
			query.addScalar("matchId"			, new StringType());
			query.addScalar("season"	        , new StringType());
			query.addScalar("awayTeamNameTH"	, new StringType());
			query.addScalar("awayTeamNameEN"	, new StringType());
			query.addScalar("matchDate"		    , new StringType());
			query.addScalar("matchTime"		    , new StringType());
			
			list		 	= query.list();

			System.out.println("[detailEventMatchByYear][Begin] list = " + list.size());
			logger.info("[detailEventMatchByYear][Begin] list = " + list.size());
			
			for(Object[] row : list){
				returnObj = new EventMatchBean();
				
				logger.info("[detailEventMatchByYear] matchId 			:: " + row[0].toString());
				logger.info("[detailEventMatchByYear] season 			:: " + row[1].toString());
				logger.info("[detailEventMatchByYear] awayTeamNameTH 	:: " + row[2].toString());
				logger.info("[detailEventMatchByYear] awayTeamNameEN 	:: " + row[3].toString());
				logger.info("[detailEventMatchByYear] matchDate 	    :: " + row[4].toString());
				logger.info("[detailEventMatchByYear] matchTime 	    :: " + row[5].toString());
			
				
				returnObj.setMatchId(String.valueOf(row[0].toString()));
				returnObj.setSeason(String.valueOf(row[1].toString()));
				returnObj.setAwayTeamNameTH(row[2].toString());
				returnObj.setAwayTeamNameEN(row[3].toString());
				returnObj.setMatchDate(EnjoyUtils.dateToThaiDisplay(row[4].toString()));
				returnObj.setMatchTime(EnjoyUtils.timeToDisplay(row[5].toString()));  
				returnObj.setMatchDateId("matchDateId"+genId);
				returnList.add(returnObj);
				genId++;
					
				
			}
 
 
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			session.close(); 
			sessionFactory						= null;
			session								= null;
			hql									= null; 
			query 								= null;
			returnObj							= null;
			list                                = null;
			logger.info("[detailEventMatchByYear][End]");
		}
		
		return returnList;
	}
	 
	@SuppressWarnings("unchecked")
	public int countOrderFromMatch(Session session,int matchId) throws EnjoyException{
			logger.info("[countOrderFromMatch][Begin]"); 
			String							sql									= null;   
			SQLQuery 						sqlQuery 							= null;   
			List<Integer>                   list                                = null;
			Integer                         count 							    = null;
			
			try{ 
				sql             = "SELECT COUNT(*) as count from Ticketorder WHERE matchId = "+ matchId;
				sqlQuery		= session.createSQLQuery(sql); 
				sqlQuery.addScalar("count"	, new IntegerType());
				list            =  sqlQuery.list(); 
				System.out.println("list.get(0): "+list.get(0));
				if(list !=null && list.size() > 0){
					count = list.get(0);
					logger.info("countOrderFromMatch order of count ::  "+ count); 
				}
			
			}catch(Exception e){
				e.printStackTrace();
				logger.info(e.getMessage());
				throw new EnjoyException("เน€เธ�โ�ฌเน€เธ�๏ฟฝเน€เธ�เธ”เน€เธ�โ€�เน€เธ�๏ฟฝเน€เธ�๏ฟฝเน€เธ�เธ�เน€เธ�๏ฟฝเน€เธ�เธ”เน€เธ�โ€�เน€เธ�๏ฟฝเน€เธ�เธ…เน€เธ�เธ’เน€เธ�โ€�");
			}finally{  
				sqlQuery 							= null;
				sql                                 = null;
				list                                = null;  
				logger.info("[countOrderFromMatch][End]");
			}
			
		return count;
	}
  
	public void insertEventMatch(Session session,EventMatchBean eventMatchBean) throws EnjoyException{
		logger.info("[insertEventMatch][Begin]");
		   
		Eventmatch                      evMatchDB         = null; 
		EventmatchPK                    evMatchPK         = null;    
		
		try{    
			evMatchPK = new EventmatchPK(); 
			evMatchPK.setMatchId(Integer.valueOf(eventMatchBean.getMatchId()));
			evMatchPK.setSeason(Integer.valueOf(eventMatchBean.getSeason()));
			
			evMatchDB = new Eventmatch();
			evMatchDB.setId(evMatchPK);
			evMatchDB.setActiveFlag("A");
			evMatchDB.setAwayTeamNameEN(eventMatchBean.getAwayTeamNameEN());
			evMatchDB.setAwayTeamNameTH(eventMatchBean.getAwayTeamNameTH());
			evMatchDB.setMatchDate(eventMatchBean.getMatchDate());
			evMatchDB.setMatchTime(eventMatchBean.getMatchTime());  
			session.save(evMatchDB);  
			session.flush(); 
			session.clear(); 
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage()); 
			throw new EnjoyException(e.getMessage()); 
		}finally{     
			evMatchDB       					= null;
			evMatchPK                           = null;   
			logger.info("[insertEventMatch][End]");
		}
		 
	}
 
	public void deleteEventMatch(Session session,int matchId,int season) throws EnjoyException{
		logger.info("[deleteMatch][Begin]");
		  
		String							hql									= null; 
		Query 						    query 								= null; 
		Eventmatch                      matchDB                             = null; 
		
		try{   
			  hql	    = "update Eventmatch m set m.activeFlag = 'C'"
					    + " where matchId = :matchId" 
					    + " and season = :season" ;
	 
			 query      = session.createQuery(hql);
			 query.setInteger("matchId", matchId);  
			 query.setInteger("season", season);
			 System.out.println(query.executeUpdate());  
		}catch(Exception e){ 
			e.printStackTrace();
			logger.info(e.getMessage()); 
			throw new EnjoyException(e.getMessage());
		}finally{  
			query 								= null;
			hql                                 = null;
			matchDB								= null;
			logger.info("[deleteMatch][End]");
		} 
	}
	
	public void updateEventMatch(Session session ,EventMatchBean eventMatchBean) throws EnjoyException{
		logger.info("[updateEventMatch][Begin]");
		  
		String							hql									= null; 
		Query 						    query 								= null;  
		int                             result                              = 0;
		Eventmatch                      evMatchDB                           = null;  
		EventmatchPK                    evMatchPK                           = null;
		try{ 
			hql        = "FROM Eventmatch WHERE matchId = :matchId" + " and season = :season";  
			query      = session.createQuery(hql).setParameter("matchId", Integer.valueOf(eventMatchBean.getMatchId())).setParameter("season", Integer.valueOf(eventMatchBean.getSeason()));  
			evMatchDB    = (Eventmatch)query.uniqueResult();
			System.out.println("matchDB.getMatchId() :: "+eventMatchBean.getMatchId());
			evMatchPK = new EventmatchPK();
			evMatchPK.setMatchId(Integer.valueOf(eventMatchBean.getMatchId()));
			evMatchPK.setSeason(Integer.valueOf(eventMatchBean.getSeason()));
			evMatchDB.setId(evMatchPK);  
			evMatchDB.setActiveFlag(eventMatchBean.getActive());
			evMatchDB.setAwayTeamNameEN(eventMatchBean.getAwayTeamNameEN());
			evMatchDB.setAwayTeamNameTH(eventMatchBean.getAwayTeamNameTH());
			evMatchDB.setMatchDate(eventMatchBean.getMatchDate());
			evMatchDB.setMatchTime(eventMatchBean.getMatchTime()); 
			session.merge(evMatchDB);  
			session.flush(); 
			session.clear(); 
		}catch(Exception e){ 
			e.printStackTrace();
			logger.info(e.getMessage()); 
			throw new EnjoyException(e.getMessage());
		}finally{    
			query 								= null;
			hql                                 = null; 
			evMatchDB                           = null;
			evMatchPK                           = null;
			logger.info("[updateEventMatch][End]");
		}
		 
	}
	
	public int selectMaxMatchId(String season) throws EnjoyException{
		logger.info("[countOrderFromMatch][Begin]"); 
		String							sql									= null;   
		SQLQuery 						sqlQuery 							= null;   
		List<Integer>                   list                                = null;
		Integer                         matchId 							= null;
		Session 						session								= null;
		SessionFactory 		   			sessionFactory		    			= null;
		try{ 
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			session.beginTransaction();
			sql             = "SELECT MAX(matchId) as matchId from Eventmatch WHERE season = "+ season;
			sqlQuery		= session.createSQLQuery(sql); 
			sqlQuery.addScalar("matchId"	, new IntegerType());
			list            =  sqlQuery.list(); 
			System.out.println("list.get(0): "+list.get(0));
			if(list !=null && list.size() > 0){
				matchId = list.get(0);
				logger.info("selectMaxMatchId order of matchId ::  "+ matchId); 
			}
			session.clear();
			session.close();
		}catch(Exception e){
			session.getTransaction().rollback();
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{  
			sqlQuery 							= null;
			sql                                 = null;
			list                                = null;
			session                             = null;
			sessionFactory                      = null;
			logger.info("[countOrderFromMatch][End]");
		}
		
		return matchId;
	}
	
	public int validateMatchUpdate(EventMatchBean eventMatchBean) throws EnjoyException{
		logger.info("[validateMatch][Begin]"); 
		String							sql									= null;   
		SQLQuery 						sqlQuery 							= null;   
		List<Integer>                   list                                = null;
		Integer                         count 							    = null;
		SessionFactory 		   sessionFactory		    = null;
		Session 			   session				    = null;
		try{ 
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			sql             = "SELECT COUNT(*) as count from Eventmatch  WHERE matchId != "+ eventMatchBean.getMatchId()
					          + " and awayTeamNameTH = '" +eventMatchBean.getAwayTeamNameTH()+"'";
			sqlQuery		= session.createSQLQuery(sql); 
			sqlQuery.addScalar("count"	, new IntegerType());
			list            =  sqlQuery.list(); 
			System.out.println("list.get(0): "+list.get(0));
			if(list !=null && list.size() > 0){
				count = list.get(0);
				logger.info("validateMatch order of count ::  "+ count);  
			}
		
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("เน€เธ�เธดเธ”เธ�เน�เธญเธ�เธดเธ”เธ�เธฅเธฒเธ”เน�เธ�เธ�เธฒเธฃเธ�เธฑเธ�เธ—เธถเธ�เธ�เน�เธญเธกเธนเธฅ");
		}finally{  
			sqlQuery 							= null;
			sql                                 = null;
			list                                = null;  
			logger.info("[validateMatch][End]");
		}
		
	return count;
}
	
	public int validateMatch(EventMatchBean eventMatchBean) throws EnjoyException{
		logger.info("[validateMatch][Begin]"); 
		String							sql									= null;   
		SQLQuery 						sqlQuery 							= null;   
		List<Integer>                   list                                = null;
		Integer                         count 							    = null;
		SessionFactory 		   sessionFactory		    = null;
		Session 			   session				    = null;
		try{ 
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			sql             = "SELECT COUNT(*) as count from Eventmatch WHERE awayTeamNameTH = '" +eventMatchBean.getAwayTeamNameTH() +"'" 
							+ " and season = " + eventMatchBean.getSeason() ;
			sqlQuery		= session.createSQLQuery(sql); 
			sqlQuery.addScalar("count"	, new IntegerType());
			list            =  sqlQuery.list(); 
			System.out.println("list.get(0): "+list.get(0));
			if(list !=null && list.size() > 0){
				count = list.get(0);
				logger.info("validateMatch order of count ::  "+ count); 
				 
			}
		
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Match is duplicate");
		}finally{  
			sqlQuery 							= null;
			sql                                 = null;
			list                                = null;  
			logger.info("[validateMatch][End]");
		}
		
	return count;
}
	 
	public void saveEventMatch(List<EventMatchBean> eventMatchBeanList,String getDelList )throws EnjoyException{
		SessionFactory 		   sessionFactory		    = null;
		Session 			   session				    = null;
		Transaction            tx                       = null;
		try{ 
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			tx                          = session.beginTransaction();
			
			if(!"none".equals(getDelList)){   
				List<String> deleteList = EnjoyUtils.getListFromArr(getDelList);
				System.out.print("deleteList : size ::"+deleteList.size());
				int matchDel ;
				if(deleteList.size()>0){
					for(String del :deleteList){
						System.out.print("del"+deleteList.size());
						if(del != ""){ 
							matchDel = Integer.valueOf(del); 
							System.out.print("delete : id is ::" +matchDel); 
							this.deleteEventMatch(session,matchDel,Integer.valueOf(eventMatchBeanList.get(0).getSeason())); 
						}
					}
					
				} 
			}
			
			int count = 0;
					
			for(EventMatchBean eventMatchBean:eventMatchBeanList){
				logger.info("eventMatchBean :: "+eventMatchBean.toString());
				if(eventMatchBean.getStatus().equals("N")){   
					this.insertEventMatch(session,eventMatchBean);  
//					count = this.validateMatch(session,eventMatchBean);
//					if(count > 0){
//						throw new EnjoyException("Match is duplicate");
//					}else{
//						this.insertEventMatch(session,eventMatchBean);  
//					}
				}else if(eventMatchBean.getStatus().equals("U")){ 
					this.updateEventMatch(session,eventMatchBean);  
//					count = this.validateMatchUpdate(session,eventMatchBean);
//					if(count > 0){
//						throw new EnjoyException("Match is duplicate");
//				 	}else{
//				 		this.updateEventMatch(session,eventMatchBean);  
//				 	}
				}
			}
			
			tx.commit();
			session.close();
		}catch(Exception e){
			session.getTransaction().rollback();
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{    
			session                             = null;
			sessionFactory                      = null;
			tx                                  = null;
			logger.info("[saveEventMatch][End]");
		}
	}
	
	public void saveNewEventMatch(List<EventMatchBean> eventMatchBeanList)throws EnjoyException{
		SessionFactory 		   sessionFactory		    = null;
		Session 			   session				    = null;
		Transaction            tx                       = null;
		try{ 
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			tx                          = session.beginTransaction();
			 
			for(EventMatchBean eventMatchBean:eventMatchBeanList){
				logger.info("eventMatchBean :: "+eventMatchBean.toString());
				this.insertEventMatch(session,eventMatchBean);    
			}
			
			tx.commit();
			session.close();
		}catch(Exception e){
			session.getTransaction().rollback();
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{    
			session                             = null;
			sessionFactory                      = null;
			tx                                  = null;
			logger.info("[saveEventMatch][End]");
		}
	}
 
	  
	
}
