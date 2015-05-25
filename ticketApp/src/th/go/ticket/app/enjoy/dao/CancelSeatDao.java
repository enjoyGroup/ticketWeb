package th.go.ticket.app.enjoy.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StringType;

import th.go.ticket.app.enjoy.bean.CancelSeatBean;
import th.go.ticket.app.enjoy.bean.SeatSummaryReservationBean;
import th.go.ticket.app.enjoy.bean.SeatZoneBean;
import th.go.ticket.app.enjoy.exception.EnjoyException;
import th.go.ticket.app.enjoy.utils.EnjoyLogger;
import th.go.ticket.app.enjoy.utils.HibernateUtil;

public class CancelSeatDao {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(CancelSeatDao.class);
	
//	public static void main(String[] args) {
//		
//		SessionFactory 				sessionFactory		= HibernateUtil.getSessionFactory();
//		Session 					session				= sessionFactory.openSession();
//		SeatSummaryReservationBean 	bean 				= null;
//		
//		try {
//			EnjoyLogger.initial(false);
//			
//			session.beginTransaction();	
//			
//			bean 				= new SeatSummaryReservationBean();
//			bean.setMatchId("1");
//			bean.setSeason("2557");
//			bean.setFieldZoneId("1");
//			bean.setSeatingNoBegin("L-10");
//			bean.setSeatingNoEnd("L-10");
//			getSumDetailReservationList(bean);
//			
////			cancelTicketByTicketId(session, "6");
//			session.getTransaction().commit();
//			
//			
//		} catch (EnjoyException e) {
//			session.getTransaction().rollback();
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}finally{
//			session.flush();
//			session.clear();
//			session.close();
//		}
//	}
	
	public List<CancelSeatBean> getFieldZoneMaster() throws EnjoyException{
		logger.info("[getFieldZoneMaster][Begin]");
		
		List<CancelSeatBean> 			returnList 							= null;
		CancelSeatBean					returnObj							= null;
		SessionFactory 					sessionFactory						= null;
		Session 						session								= null;
		String							hql									= null;
		List<Object[]>			 		list								= null;
		SQLQuery 						query 								= null;
		
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			returnList		= new ArrayList<CancelSeatBean>();
			
			hql				= "SELECT fieldZoneId, fieldZoneName, fieldZoneNameTicket"
								+ " FROM fieldzonemaster"
								+ " order by fieldZoneId asc";
			query			= session.createSQLQuery(hql);
			
			query.addScalar("fieldZoneId"			, new StringType());
			query.addScalar("fieldZoneName"			, new StringType());
//			query.addScalar("fieldZoneNameTicket"	, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getFieldZoneMaster] list :: " + list.size());
			for(Object[] row : list){
				returnObj = new CancelSeatBean();
				
				logger.info("[getFieldZoneMaster] fieldZoneId 			:: " + row[0].toString());
				logger.info("[getFieldZoneMaster] fieldZoneName		 	:: " + row[1].toString());
//				logger.info("[getFieldZoneMaster] fieldZoneNameTicket	:: " + row[2].toString());
				
				returnObj.setFieldZoneId			(row[0].toString());
				returnObj.setFieldZoneName			(row[1].toString());
//				returnObj.setFieldZoneNameTicket	(row[2].toString()==null || row[2].toString().equals("")?row[1].toString():row[2].toString());
				
				returnList.add(returnObj);
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
			logger.info("[getFieldZoneMaster][End]");
		}
		
		return returnList;
	}
	
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
			
			hql				= "SELECT b.season FROM eventmatch b GROUP BY b.season order by b.season desc";
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
	
	public List<CancelSeatBean> getTeamList(String season) throws EnjoyException{
		logger.info("[getTeamList][Begin]");
		
		List<CancelSeatBean> 			returnList 							= null;
		CancelSeatBean					returnObj							= null;
		SessionFactory 					sessionFactory						= null;
		Session 						session								= null;
		String							hql									= null;
		List<Object[]>			 		list								= null;
		SQLQuery 						query 								= null;
		
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			returnList		= new ArrayList<CancelSeatBean>();
			
			hql				= "SELECT b.matchId, b.awayTeamNameTH, b.awayTeamNameEN FROM eventmatch b "
									+ " where b.season  = " + season
							  		+ " order by b.awayTeamNameTH, b.matchId desc";
			query			= session.createSQLQuery(hql);
			
			query.addScalar("matchId"			, new StringType());
			query.addScalar("awayTeamNameTH"	, new StringType());
			query.addScalar("awayTeamNameEN"	, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getTeamList] list :: " + list.size());
			for(Object[] row : list){
				returnObj = new CancelSeatBean();
				
				logger.info("[getTeamList] matchId 			:: " + row[0].toString());
				logger.info("[getTeamList] awayTeamNameTH	:: " + row[1].toString());
				logger.info("[getTeamList] awayTeamNameEN	:: " + row[2].toString());
				
				returnObj.setMatchId			(row[0].toString());
				returnObj.setAwayTeamNameTH		(row[1].toString());
				returnObj.setAwayTeamNameEN		(row[2].toString());
				
				returnList.add(returnObj);
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
			logger.info("[getTeamList][End]");
		}
		
		return returnList;
	}
		
	public List<CancelSeatBean> getSumDetailReservationList(CancelSeatBean cancelSeatBean) throws EnjoyException{
		logger.info("[getSumDetailReservationList][Begin]");
		
		List<CancelSeatBean> 				returnList 							= null;
		CancelSeatBean						returnObj							= null;
		SessionFactory 						sessionFactory						= null;
		Session 							session								= null;
		String								hql									= null;
		List<Object[]>			 			list								= null;
		SQLQuery 							query 								= null;
		
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			returnList		= new ArrayList<CancelSeatBean>();
			
			hql				= "select a.ticketId, a.seatingNo, b.bookingPrices, c.bookingTypeName, d.fieldZoneName, a.season, e.awayTeamNameTH "
								+ " from ticketorder a,  fieldzonedetail b, bookingtype c, fieldzonemaster d,eventmatch e"
								+ " where b.fieldZoneId     = a.fieldZoneId"
								+ " and b.bookingTypeId  	= a.bookingTypeId"
								+ " and c.bookingTypeId   	= a.bookingTypeId"
								+ " and d.fieldZoneId  		= a.fieldZoneId"
								+ " and e.matchId  			= a.matchId"
								+ " and e.season	  		= a.season"
								+ " and a.ticketStatus      = 'A'";
			if(!cancelSeatBean.getSeason().equals("")){
				hql += " and a.season = '" + cancelSeatBean.getSeason() + "'";
			}
			if(!cancelSeatBean.getMatchId().equals("")){
				hql += " and a.matchId = '" + cancelSeatBean.getMatchId() + "'";
			}
			if(!cancelSeatBean.getFieldZoneId().equals("")){
				hql += " and a.fieldZoneId = '" + cancelSeatBean.getFieldZoneId() + "'";
			}
			if(!cancelSeatBean.getTicketId().equals("")){
				hql += " and a.ticketId = '" + cancelSeatBean.getTicketId() + "'";
			}
			if(!cancelSeatBean.getSeatingNoBegin().equals("")){
				hql += " and a.seatingNo >= '" + cancelSeatBean.getSeatingNoBegin() + "'";
			}
			if(!cancelSeatBean.getSeatingNoEnd().equals("")){
				hql += " and a.seatingNo <= '" + cancelSeatBean.getSeatingNoEnd() + "'";
			}
			hql += " order by a.season, e.awayTeamNameTH, a.seatingNo, a.ticketId asc";
			
			query			= session.createSQLQuery(hql);
			
			query.addScalar("ticketId"			, new StringType());
			query.addScalar("seatingNo"			, new StringType());
			query.addScalar("bookingPrices"		, new StringType());
			query.addScalar("bookingTypeName"	, new StringType());
			query.addScalar("fieldZoneName"		, new StringType());
			query.addScalar("season"			, new StringType());
			query.addScalar("awayTeamNameTH"	, new StringType());
			
			list		 	= query.list();
			
			logger.info("[hql :: " + hql);
			logger.info("[getSumDetailReservationList] list :: " + list.size());
			for(Object[] row : list){
				returnObj 		= new CancelSeatBean();
				
				logger.info("[getSumDetailReservationList] ticketId 			:: " + row[0].toString());
				logger.info("[getSumDetailReservationList] seatingNo 			:: " + row[1].toString());
				logger.info("[getSumDetailReservationList] bookingPrices		:: " + row[2].toString());
				logger.info("[getSumDetailReservationList] bookingTypeName		:: " + row[3].toString());
				logger.info("[getSumDetailReservationList] fieldZoneName		:: " + row[4].toString());
				logger.info("[getSumDetailReservationList] season				:: " + row[5].toString());
				logger.info("[getSumDetailReservationList] awayTeamNameTH		:: " + row[6].toString());
				
				returnObj.setTicketId			(row[0].toString());
				returnObj.setSeatingNo			(row[1].toString()==null||row[1].toString().equals("")?"-":row[1].toString());
				returnObj.setBookingPrices		(row[2].toString());
				returnObj.setBookingTypeName	(row[3].toString());
				returnObj.setFieldZoneName		(row[4].toString());
				returnObj.setSeason				(row[5].toString());
				returnObj.setAwayTeamNameTH		(row[6].toString());
				
				returnList.add(returnObj);
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
			logger.info("[getSumDetailReservationList][End]");
		}
		
		return returnList;
	}
	
	public void cancelTicketByTicketId(Session session, String ticketIdList) throws EnjoyException{
		logger.info("[cancelTicketByTicketId][Begin]");
		
		String		hql				= null;
		Query 		query 			= null;
		String[]	arrTicketIdList	= null;
		int 		result			= 0;
		
		
		try{
			arrTicketIdList = ticketIdList.split(",");

			hql				= "update Ticketorder a set a.ticketStatus = 'R'"
								+ " where a.ticketId in (:ticketId) ";
			query = session.createQuery(hql);
			query.setParameterList("ticketId" , arrTicketIdList);
						
			result = query.executeUpdate();			
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการอัพเดทข้อมูล");
		}finally{
			
			hql									= null;
			query 								= null;
			logger.info("[cancelTicketByTicketId][End]");
		}
	}
}
