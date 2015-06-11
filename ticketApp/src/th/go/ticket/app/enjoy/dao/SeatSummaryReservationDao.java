package th.go.ticket.app.enjoy.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StringType;

import th.go.ticket.app.enjoy.bean.SeatReservationBean;
import th.go.ticket.app.enjoy.bean.SeatSummaryReservationBean;
import th.go.ticket.app.enjoy.exception.EnjoyException;
import th.go.ticket.app.enjoy.utils.EnjoyLogger;
import th.go.ticket.app.enjoy.utils.EnjoyUtils;
import th.go.ticket.app.enjoy.utils.HibernateUtil;

public class SeatSummaryReservationDao {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(SeatSummaryReservationDao.class);
	
//	public static void main(String[] args) {
//		
//		SeatReservationBean 		seatReservationBean = new SeatReservationBean();
//		SessionFactory 				sessionFactory		= HibernateUtil.getSessionFactory();
//		Session 					session				= sessionFactory.openSession();
//		
//		try {
//			EnjoyLogger.initial(false);
//			
//			session.beginTransaction();
//			
//			seatReservationBean.setTicketId("0000000000000");
//			seatReservationBean.setSeatingNo("A1");
//			seatReservationBean.setMatchId("1");
//			seatReservationBean.setFieldZoneId("1");
//			seatReservationBean.setBookingTypeId("1");
//			seatReservationBean.setUserUniqueId("1");
//			seatReservationBean.setTicketStatus("P");
//			
//			insertTicketorder(session, seatReservationBean);
//			
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
	
	public List<SeatSummaryReservationBean> getSumDetailReservationList(SeatSummaryReservationBean bean) throws EnjoyException{
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
			
			hql				= "select a.ticketId, a.seatingNo, b.bookingPrices, c.bookingTypeName, d.fieldZoneNameTicket"
								+ " from ticketorder a,  fieldzonedetail b, bookingtype c, fieldzonemaster d"
								+ " where b.fieldZoneId        	= a.fieldZoneId"
									+ " and b.bookingTypeId  	= a.bookingTypeId"
									+ " and c.bookingTypeId   	= a.bookingTypeId"
									+ " and d.fieldZoneId  		= a.fieldZoneId"
									+ " and a.ticketId    		in (" + bean.getTicketId() + ")"
//									+ " and a.fieldZoneId    	= " + bean.getFieldZoneId()
//									+ " and a.userUniqueId    	= " + bean.getUserUniqueId()
//									+ " and a.ticketStatus	  	= 'P'"
								+ " order by a.seatingNo, a.ticketId asc";
			query			= session.createSQLQuery(hql);
			
			query.addScalar("ticketId"				, new StringType());
			query.addScalar("seatingNo"				, new StringType());
			query.addScalar("bookingPrices"			, new StringType());
			query.addScalar("bookingTypeName"		, new StringType());
			query.addScalar("fieldZoneNameTicket"	, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getSumDetailReservationList] list :: " + list.size());
			for(Object[] row : list){
				returnObj 		= new SeatSummaryReservationBean();
				
				logger.info("[getSumDetailReservationList] ticketId 			:: " + row[0].toString());
				logger.info("[getSumDetailReservationList] seatingNo 			:: " + row[1].toString());
				logger.info("[getSumDetailReservationList] bookingPrices		:: " + row[2].toString());
				logger.info("[getSumDetailReservationList] bookingTypeName		:: " + row[3].toString());
				logger.info("[getSumDetailReservationList] fieldZoneName		:: " + row[4].toString());
				
				returnObj.setTicketId			(row[0].toString());
				returnObj.setSeatingNo			(row[1].toString()==null||row[1].toString().equals("")?"-":row[1].toString());
				returnObj.setBookingPrices		(row[2].toString());
				returnObj.setBookingTypeName	(row[3].toString());
				returnObj.setFieldZoneName		(row[4].toString());
				
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
	
	public SeatSummaryReservationBean getHeaderTicketDetail(SeatSummaryReservationBean bean) throws EnjoyException{
		logger.info("[getHeaderTicketDetail][Begin]");
		
		SeatSummaryReservationBean		returnObj							= null;
		SessionFactory 					sessionFactory						= null;
		Session 						session								= null;
		String							hql									= null;
		List<Object[]>			 		list								= null;
		SQLQuery 						query 								= null;
		
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			
			hql				= "select awayTeamNameTH , awayTeamNameEN, DATE_FORMAT( matchDate, '%d/%m/%Y') matchDate , matchTime"
								+ " from eventmatch"
								+ " where matchId = '" + bean.getMatchId() + "'"
									+ " and season = '" + bean.getSeason() + "'";
			query			= session.createSQLQuery(hql);
			
			query.addScalar("awayTeamNameTH"			, new StringType());
			query.addScalar("awayTeamNameEN"			, new StringType());
			query.addScalar("matchDate"					, new StringType());
			query.addScalar("matchTime"					, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getheaderTicketDetail] list :: " + list.size());
			for(Object[] row : list){
				returnObj = new SeatSummaryReservationBean();
				
				logger.info("[getHeaderTicketDetail] awayTeamNameTH 		:: " + row[0].toString());
				logger.info("[getHeaderTicketDetail] awayTeamNameEN		 	:: " + row[1].toString());
				logger.info("[getHeaderTicketDetail] matchDate		 		:: " + row[2].toString());
				logger.info("[getHeaderTicketDetail] matchTime		 		:: " + row[3].toString());
				
				returnObj.setAwayTeamNameTH		(row[0].toString());
				returnObj.setAwayTeamNameEN		(row[1].toString());
				returnObj.setMatchDate			(row[2].toString());
				returnObj.setMatchTime			(EnjoyUtils.formatTime(row[3].toString()));
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
			logger.info("[getHeaderTicketDetail][End]");
		}
		
		return returnObj;
	}
	
//	public void updateStatusPendingToActive(Session session, SeatSummaryReservationBean seatSummaryReservationBean) throws EnjoyException{
//		logger.info("[updateStatusPendingToActive][Begin]");
//		
//		String							hql									= null;
//		Query 							query 								= null;
//		int 							result								= 0;
//		
//		try{
//			hql				= "update Ticketorder t set t.ticketStatus = 'A'"
//							+ " where t.ticketId	 				= :ticketId"
//								+ " and t.ticketStatus 			= 'P'";
//			
//			query = session.createQuery(hql);
//			
//			logger.info("[updateStatusPendingToActive] TicketId :: " + seatSummaryReservationBean.getTicketId());
//			
//			query.setParameter("ticketId"		, seatSummaryReservationBean.getTicketId());
//			
//			result = query.executeUpdate();			
//		}catch(Exception e){
//			e.printStackTrace();
//			logger.info(e.getMessage());
//			throw new EnjoyException("เกิดข้อผิดพลาดในการอัพเดทข้อมูล");
//		}finally{
//			
//			hql									= null;
//			query 								= null;
//			logger.info("[updateStatusPendingToActive][End]");
//		}
//	}
	
}
