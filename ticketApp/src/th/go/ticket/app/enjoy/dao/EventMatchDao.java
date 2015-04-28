package th.go.ticket.app.enjoy.dao;

import java.util.ArrayList;
import java.util.List;
 
 
 






import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory; 
import org.hibernate.type.IntegerType; 
  






import th.go.ticket.app.enjoy.bean.EventMatchBean;  
import th.go.ticket.app.enjoy.exception.EnjoyException; 
import th.go.ticket.app.enjoy.model.Eventmatch;
import th.go.ticket.app.enjoy.utils.EnjoyLogger;
import th.go.ticket.app.enjoy.utils.EnjoyUtils;
import th.go.ticket.app.enjoy.utils.HibernateUtil;

public class EventMatchDao {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(EventMatchDao.class);
 
	
	@SuppressWarnings("unchecked")
	public List<String> seasonList() throws EnjoyException{
		logger.info("[seasonList][Begin]");
		
		List<String> 					returnList 							= null;
		SessionFactory 					sessionFactory						= null;
		Session 						session								= null;
		String							hql									= null;
		List<Integer>			 		list								= null;
		Query 						    query 								= null;
		String							season								= null;
		
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			returnList		= new ArrayList<String>();
		     
			 hql        = "select season from Eventmatch GROUP BY season order by  season desc" ;  
			 query 		= session.createQuery(hql);
			 list 		= (List<Integer>)query.list();
			
			logger.info("[seasonList] list :: " + list.size());
			for(int i=0;i<list.size();i++){
				season 	= String.valueOf(list.get(i));
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
	 
	@SuppressWarnings("unchecked")
	public List<EventMatchBean> detailEventMatchByYear(String season) throws EnjoyException{
		logger.info("[detailEventMatchByYear][Begin] and season = " + season);
		
		List<EventMatchBean> 	returnList 							= null;
		SessionFactory 			sessionFactory						= null;
		Session 				session								= null;
		String					hql									= null; 
		EventMatchBean		    returnObj							= null;
		List<Eventmatch>        eventlist                           = null;
		Query 					query 								= null;
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			returnList		= new ArrayList<EventMatchBean>();
            int seasonInt   = Integer.valueOf(season);
  
            hql				= "FROM Eventmatch WHERE season  = :season " + "and activeFlag = true" + " order by matchId" ; 
            eventlist		= session.createQuery(hql).setParameter("season", seasonInt).list();
 
			logger.info("[detailEventMatchByYear][Begin] list = " + eventlist.size());
			for(Eventmatch row : eventlist){
				returnObj = new EventMatchBean(); 
//				logger.info("[detailEventMatchByYear] matchId 			:: " + row.getMatchId());
				logger.info("[detailEventMatchByYear] awayTeamNameTH 	:: " + row.getAwayTeamNameTH());
				logger.info("[detailEventMatchByYear] awayTeamNameEN 	:: " + row.getAwayTeamNameEN());
				logger.info("[detailEventMatchByYear] matchDate 	    :: " + EnjoyUtils.dateFormat(row.getMatchDate(), "YYYYMMDD", "dd/MM/yyyy"));
				logger.info("[detailEventMatchByYear] matchTime 	    :: " + row.getMatchTime());
				
//				returnObj.setMatchId(String.valueOf(row.getMatchId()));
				returnObj.setAwayTeamNameTH(row.getAwayTeamNameTH());
				returnObj.setAwayTeamNameEN(row.getAwayTeamNameEN());
				returnObj.setMatchDate(EnjoyUtils.dateFormat(row.getMatchDate(), "YYYYMMDD", "dd/MM/yyyy"));
				returnObj.setMatchTime(row.getMatchTime());
//				returnObj.setMatchTime(EnjoyUtils.timeFormat(row.getMatchTime()));
//				returnObj.setSeason(String.valueOf(row.getSeason()));
				returnList.add(returnObj);
				
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
			eventlist                           = null;
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
				throw new EnjoyException("เกิดข้อผิดพลาด");
			}finally{  
				sqlQuery 							= null;
				sql                                 = null;
				list                                = null;  
				logger.info("[countOrderFromMatch][End]");
			}
			
		return count;
	}
 
	 
	public void insertEventMatch(Session session,EventMatchBean eventMatchBean) throws EnjoyException{
		logger.info("[addEventMatchList][Begin]");
		  
		String							sql				  = null; 
		SQLQuery 						query 			  = null; 
		int                             result            = 0;
		Eventmatch                      evMatchDB         = null;
		
		try{
			evMatchDB = new Eventmatch();
//			evMatchDB.setActiveFlag(true);
			evMatchDB.setAwayTeamNameEN(eventMatchBean.getAwayTeamNameEN());
			evMatchDB.setAwayTeamNameTH(eventMatchBean.getAwayTeamNameTH());
			evMatchDB.setMatchDate(eventMatchBean.getMatchDate());
			evMatchDB.setMatchTime(eventMatchBean.getMatchTime()); 
//			evMatchDB.setSeason(Integer.valueOf(eventMatchBean.getSeason()));
			session.persist(evMatchDB);
  
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการบันทึกข้อมูล"); 
		}finally{  
			query 								= null;
			sql                                 = null;
			result                              = 0;
			evMatchDB       					= null;
			logger.info("[addEventMatchList][End]");
		}
		 
	}

	 
	public void deleteEventMatch(Session session,int matchId) throws EnjoyException{
		logger.info("[deleteMatch][Begin]");
		  
		String							hql									= null; 
		Query 						    query 								= null; 
		 Eventmatch                     matchDB                             = null;
		
		try{  
			 System.out.print("[deleteMatch]input to delete :"+matchId);
			 hql        = "FROM Eventmatch WHERE matchId = :matchId";  
			 query      = session.createQuery(hql).setParameter("matchId", matchId);  
			 matchDB = (Eventmatch)query.uniqueResult();
//			 System.out.println("matchDB.getMatchId() :: "+matchDB.getMatchId());
			 session.delete(matchDB);  
			
		}catch(Exception e){ 
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการลบข้อมูล"); 
		}finally{   
			query 								= null;
			hql                                 = null;
			matchDB								= null;
			logger.info("[deleteMatch][End]");
		} 
	}
	
	public void updateEventMatch(Session session,EventMatchBean eventMatchBean) throws EnjoyException{
		logger.info("[addEventMatchList][Begin]");
		  
		String							sql									= null; 
		SQLQuery 						query 								= null;  
		int                             result                              = 0;
		Eventmatch                      evMatchDB                           = null;
		
		try{
			evMatchDB = new Eventmatch();
//			evMatchDB.setActiveFlag(eventMatchBean.isActive());
			evMatchDB.setAwayTeamNameEN(eventMatchBean.getAwayTeamNameEN());
			evMatchDB.setAwayTeamNameTH(eventMatchBean.getAwayTeamNameTH());
			evMatchDB.setMatchDate(eventMatchBean.getMatchDate());
			evMatchDB.setMatchTime(eventMatchBean.getMatchTime());
//			evMatchDB.setMatchId(Integer.valueOf(eventMatchBean.getMatchId()));
//			evMatchDB.setSeason(Integer.valueOf(eventMatchBean.getSeason()));
			session.saveOrUpdate(evMatchDB);  

		}catch(Exception e){ 
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการแก้ไขข้อมูล"); 
		}finally{  
			query 								= null;
			sql                                 = null; 
			evMatchDB                           = null;
			logger.info("[addEventMatchList][End]");
		}
		 
	}
	 
 
	
}
