package th.go.ticket.app.enjoy.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StringType;

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
	
	public List<SeatZoneBean> getFieldZoneMaster() throws EnjoyException{
		logger.info("[getFieldZoneMaster][Begin]");
		
		List<SeatZoneBean> 				returnList 							= null;
		SeatZoneBean					returnObj							= null;
		SessionFactory 					sessionFactory						= null;
		Session 						session								= null;
		String							hql									= null;
		List<Object[]>			 		list								= null;
		SQLQuery 						query 								= null;
		
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			returnList		= new ArrayList<SeatZoneBean>();
			
			hql				= "SELECT fieldZoneId, fieldZoneName, fieldZoneNameTicket"
								+ " FROM fieldzonemaster"
								+ " order by fieldZoneId asc";
			query			= session.createSQLQuery(hql);
			
			query.addScalar("fieldZoneId"			, new StringType());
			query.addScalar("fieldZoneName"			, new StringType());
			query.addScalar("fieldZoneNameTicket"	, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getFieldZoneMaster] list :: " + list.size());
			for(Object[] row : list){
				returnObj = new SeatZoneBean();
				
				logger.info("[getFieldZoneMaster] fieldZoneId 			:: " + row[0].toString());
				logger.info("[getFieldZoneMaster] fieldZoneName		 	:: " + row[1].toString());
				logger.info("[getFieldZoneMaster] fieldZoneNameTicket	:: " + row[2].toString());
				
				returnObj.setFieldZoneId			(row[0].toString());
				returnObj.setFieldZoneName			(row[1].toString());
				returnObj.setFieldZoneNameTicket	(row[2].toString()==null || row[2].toString().equals("")?row[1].toString():row[2].toString());
				
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
		
	public List<SeatSummaryReservationBean> getSumDetailReservationList(SeatSummaryReservationBean seatSummaryReservationBean) throws EnjoyException{
		logger.info("[getSumDetailReservationList][Begin]");
		
		List<SeatSummaryReservationBean> 	returnList 							= null;
		SeatSummaryReservationBean			returnObj							= null;
		SessionFactory 						sessionFactory						= null;
		Session 							session								= null;
		String								hql									= null;
		List<Object[]>			 			list								= null;
		SQLQuery 							query 								= null;
		
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			returnList		= new ArrayList<SeatSummaryReservationBean>();
			
			hql				= "select a.ticketId, a.seatingNo, b.bookingPrices, c.bookingTypeName, d.fieldZoneName, a.season, e.awayTeamNameTH "
								+ " from ticketorder a,  fieldzonedetail b, bookingtype c, fieldzonemaster d,eventmatch e"
								+ " where b.fieldZoneId     = a.fieldZoneId"
								+ " and b.bookingTypeId  	= a.bookingTypeId"
								+ " and c.bookingTypeId   	= a.bookingTypeId"
								+ " and d.fieldZoneId  		= a.fieldZoneId"
								+ " and e.matchId  			= a.matchId"
								+ " and e.season	  		= a.season"
								+ " and a.ticketStatus      = 'A'";
			if(!seatSummaryReservationBean.getSeason().equals("")){
				hql += " and a.season = '" + seatSummaryReservationBean.getSeason() + "'";
			}
			if(!seatSummaryReservationBean.getMatchId().equals("")){
				hql += " and a.matchId = '" + seatSummaryReservationBean.getMatchId() + "'";
			}
			if(!seatSummaryReservationBean.getFieldZoneId().equals("")){
				hql += " and a.fieldZoneId = '" + seatSummaryReservationBean.getFieldZoneId() + "'";
			}
			if(!seatSummaryReservationBean.getTicketId().equals("")){
				hql += " and a.ticketId = '" + seatSummaryReservationBean.getTicketId() + "'";
			}
			if(!seatSummaryReservationBean.getSeatingNoBegin().equals("")){
				hql += " and a.seatingNo >= '" + seatSummaryReservationBean.getSeatingNoBegin() + "'";
			}
			if(!seatSummaryReservationBean.getSeatingNoEnd().equals("")){
				hql += " and a.seatingNo <= '" + seatSummaryReservationBean.getSeatingNoEnd() + "'";
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
				returnObj 		= new SeatSummaryReservationBean();
				
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
