package th.go.ticket.app.enjoy.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StringType;

import th.go.ticket.app.enjoy.bean.DetailRevenueOfYearBean;
import th.go.ticket.app.enjoy.bean.DisplayMatchDetailBean;
import th.go.ticket.app.enjoy.exception.EnjoyException;
import th.go.ticket.app.enjoy.utils.EnjoyLogger;
import th.go.ticket.app.enjoy.utils.HibernateUtil;

public class DisplayMatchDetailDao {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(DisplayMatchDetailDao.class);
	
	public List<String> seasonList() throws EnjoyException{
		logger.info("[seasonList][Begin]");
		
		List<String> 	returnList 							= null;
		SessionFactory 					sessionFactory						= null;
		Session 						session								= null;
		String							hql									= null;
		List<String>			 		list								= null;
		SQLQuery 						query 								= null;
		
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			returnList		= new ArrayList<String>();
			
			hql				= "SELECT b.season FROM eventmatch b GROUP BY b.season order by  b.season desc";
			query			= session.createSQLQuery(hql);
			
			query.addScalar("season"			, new StringType());
			
			list		 	= query.list();
			
			for(int i=0;i<list.size();i++){
				logger.info("[seasonList] season 			:: " + list.get(i));
				
				returnList.add(list.get(i));
				
			}
			
			
			
		}catch(Exception e){
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
	
	public List<DisplayMatchDetailBean> matchList(String season) throws EnjoyException{
		logger.info("[matchList][Begin]");
		
		List<DisplayMatchDetailBean> 	returnList 							= null;
		SessionFactory 					sessionFactory						= null;
		Session 						session								= null;
		String							hql									= null;
		List<Object[]>			 		list								= null;
		SQLQuery 						query 								= null;
		DisplayMatchDetailBean			returnObj							= null;
		
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			returnList		= new ArrayList<DisplayMatchDetailBean>();
			
			hql				= "SELECT b.matchId, b.awayTeamNameTH FROM eventmatch b where b.season = '" + season + "' order by b.matchId desc";
			query			= session.createSQLQuery(hql);
			
			query.addScalar("matchId"			, new StringType());
			query.addScalar("awayTeamNameTH"	, new StringType());
			
			list		 	= query.list();
			
			for(Object[] row : list){
				returnObj = new DisplayMatchDetailBean();
				
				logger.info("[detailRevenueByYear] matchId 			:: " + row[0].toString());
				logger.info("[detailRevenueByYear] awayTeamNameTH 	:: " + row[1].toString());
				
				returnObj.setMatchId			(row[0].toString());
				returnObj.setAwayTeamNameTH		(row[1].toString());
				
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
			list								= null;
			query 								= null;
			returnObj							= null;
			logger.info("[matchList][End]");
		}
		
		return returnList;
	}
	
	public List<DisplayMatchDetailBean> genHeaderTicketType(String matchId) throws EnjoyException{
		logger.info("[genHeaderTicketType][Begin]");
		
		List<DisplayMatchDetailBean> 	returnList 							= null;
		SessionFactory 					sessionFactory						= null;
		Session 						session								= null;
		String							hql									= null;
		List<Object[]>			 		list								= null;
		SQLQuery 						query 								= null;
		DisplayMatchDetailBean			returnObj							= null;
		
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			returnList		= new ArrayList<DisplayMatchDetailBean>();
			
			hql				= "SELECT a.matchId, b.bookingTypeId, b.bookingTypeName"
								+ " FROM ticketorder a, bookingtype b, eventmatch s"
								+ " WHERE a.bookingTypeId 	= b.bookingTypeId"
									+ " and a.matchId 		= " + matchId
									+ " and s.matchId 		= a.matchId"
									+ " and a.ticketStatus 	<> 'R'"
								+ " GROUP BY a.matchId, b.bookingTypeName";
			query			= session.createSQLQuery(hql);
			
			query.addScalar("matchId"			, new StringType());
			query.addScalar("bookingTypeId"		, new StringType());
			query.addScalar("bookingTypeName"	, new StringType());
			
			list		 	= query.list();
			
			for(Object[] row : list){
				returnObj = new DisplayMatchDetailBean();
				
				logger.info("[detailRevenueByYear] matchId 			:: " + row[0].toString());
				logger.info("[detailRevenueByYear] bookingTypeId 	:: " + row[1].toString());
				logger.info("[detailRevenueByYear] bookingTypeName 	:: " + row[2].toString());
				
				returnObj.setMatchId			(row[0].toString());
				returnObj.setBookingTypeId		(row[1].toString());
				returnObj.setBookingTypeName	(row[2].toString());
				
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
			list								= null;
			query 								= null;
			returnObj							= null;
			logger.info("[genHeaderTicketType][End]");
		}
		
		return returnList;
	}
	
	public List<DisplayMatchDetailBean> detailOfMatch(String matchId) throws EnjoyException{
		logger.info("[detailOfMatch][Begin]");
		
		List<DisplayMatchDetailBean> 	returnList 							= null;
		SessionFactory 					sessionFactory						= null;
		Session 						session								= null;
		String							hql									= null;
		List<Object[]>			 		list								= null;
		SQLQuery 						query 								= null;
		DisplayMatchDetailBean			returnObj							= null;
		
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			returnList		= new ArrayList<DisplayMatchDetailBean>();
			
			hql				= "SELECT a.matchId"
								+ ", b.fieldZoneName"
								+ ", e.bookingTypeId"
								+ ", e.bookingTypeName"
								+ ", count(a.seatingNo) 	as totalSeating"
								+ ", sum(c.bookingPrices) 	as tatalMoney"
								+ " FROM ticketorder a, fieldzonemaster b, fieldzonedetail c, eventmatch d , bookingtype e"
								+ " WHERE a.fieldZoneId 	= b.fieldZoneId"
									+ " and a.fieldZoneId 	= c.fieldZoneId"
									+ " and a.bookingTypeId = c.bookingTypeId"
									+ " and a.matchId 		= d.matchId"
									+ " and a.bookingTypeId = e.bookingTypeId"
									+ " and a.matchId 		= '" + matchId + "'"
								+ " GROUP BY a.matchId, b.fieldZoneName, e.bookingTypeId, e.bookingTypeName"
								+ " ORDER BY a.matchId, b.fieldZoneName, e.bookingTypeId, e.bookingTypeName;";
			query			= session.createSQLQuery(hql);
			
			query.addScalar("matchId"			, new StringType());
			query.addScalar("fieldZoneName"		, new StringType());
			query.addScalar("bookingTypeId"		, new StringType());
			query.addScalar("bookingTypeName"	, new StringType());
			query.addScalar("totalSeating"		, new StringType());
			query.addScalar("tatalMoney"		, new StringType());
			
			list		 	= query.list();
			
			for(Object[] row : list){
				returnObj = new DisplayMatchDetailBean();
				
				logger.info("[detailOfMatch] matchId 			:: " + row[0].toString());
				logger.info("[detailOfMatch] fieldZoneName 		:: " + row[1].toString());
				logger.info("[detailOfMatch] bookingTypeId 		:: " + row[2].toString());
				logger.info("[detailOfMatch] bookingTypeName 	:: " + row[3].toString());
				logger.info("[detailOfMatch] totalSeating 		:: " + row[4].toString());
				logger.info("[detailOfMatch] tatalMoney 		:: " + row[5].toString());
				
				returnObj.setMatchId			(row[0].toString());
				returnObj.setFieldZoneName		(row[1].toString());
				returnObj.setBookingTypeId		(row[2].toString());
				returnObj.setBookingTypeName	(row[3].toString());
				returnObj.setTotalSeating		(row[4].toString());
				returnObj.setTatalMoney			(row[5].toString());
				
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
			list								= null;
			query 								= null;
			returnObj							= null;
			logger.info("[detailOfMatch][End]");
		}
		
		return returnList;
	}

}
