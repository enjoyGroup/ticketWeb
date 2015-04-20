package th.go.ticket.app.enjoy.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StringType;

import th.go.ticket.app.enjoy.bean.SeatReservationBean;
import th.go.ticket.app.enjoy.bean.SeatZoneBean;
import th.go.ticket.app.enjoy.exception.EnjoyException;
import th.go.ticket.app.enjoy.utils.EnjoyLogger;
import th.go.ticket.app.enjoy.utils.HibernateUtil;

public class SeatReservationDao {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(SeatReservationDao.class);
	private static final String CHILD_TYPE	= "1";
	private static final String MAN_TYPE	= "2";
	private static final String OLD_TYPE	= "3";
	private static final String VIP_TYPE	= "4";
	
//	public static void main(String[] args) {
//		try {
//			EnjoyLogger.initial(true);
//			List<String> 					list 		= seasonList();
//			String 							season		= null;
//			List<DetailRevenueOfYearBean> 	detailList 	= null;
//			DetailRevenueOfYearBean			detail		= null;
//			
//			for(int i=0;i<list.size();i++){
//				season 		= list.get(i);
//				
//				logger.info("[main] season 			:: " + season);
//				
//				detailList = detailRevenueByYear(season);
//				
//				for(int j=0;j<detailList.size();j++){
//					detail = detailList.get(j);
//					
//					logger.info("[main] season 			:: " + season);
//					logger.info("[main] matchId 		:: " + detail.getMatchId());
//					logger.info("[main] awayTeamNameTH 	:: " + detail.getAwayTeamNameTH());
//					logger.info("[main] awayTeamNameEN 	:: " + detail.getAwayTeamNameEN());
//					logger.info("[main] totalSeating 	:: " + detail.getTotalSeating());
//					logger.info("[main] bookingPrices 	:: " + detail.getBookingPrices());
//					
//				}
//				
//			}
//			
//			
//		} catch (EnjoyException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	public SeatReservationBean getSeatForThisZone(String fieldZoneId) throws EnjoyException{
		logger.info("[getSeatForThisZone][Begin]");
		
		SeatReservationBean				returnObj							= null;
		SessionFactory 					sessionFactory						= null;
		Session 						session								= null;
		String							hql									= null;
		List<Object[]>			 		list								= null;
		SQLQuery 						query 								= null;
		
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			
			hql				= "SELECT fieldZoneId, fieldZoneName, rows, seating, totalSeating"
								+ " FROM fieldzonemaster"
								+ " where fieldZoneId = '" + fieldZoneId + "'";
			query			= session.createSQLQuery(hql);
			
			query.addScalar("fieldZoneId"			, new StringType());
			query.addScalar("fieldZoneName"			, new StringType());
			query.addScalar("rows"					, new StringType());
			query.addScalar("seating"				, new StringType());
			query.addScalar("totalSeating"			, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getSeatForThisZone] list :: " + list.size());
			for(Object[] row : list){
				returnObj = new SeatReservationBean();
				
				logger.info("[getSeatForThisZone] fieldZoneId 			:: " + row[0].toString());
				logger.info("[getSeatForThisZone] fieldZoneName		 	:: " + row[1].toString());
				logger.info("[getSeatForThisZone] rows		 			:: " + row[2].toString());
				logger.info("[getSeatForThisZone] seating		 		:: " + row[3].toString());
				logger.info("[getSeatForThisZone] totalSeating		 	:: " + row[4].toString());
				
				returnObj.setFieldZoneId		(row[0].toString());
				returnObj.setFieldZoneName		(row[1].toString());
				returnObj.setRows				(row[2].toString());
				returnObj.setSeating			(row[3].toString());
				returnObj.setTotalSeating		(row[4].toString());
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
			logger.info("[getSeatForThisZone][End]");
		}
		
		return returnObj;
	}
	
	public List<SeatReservationBean> getTicketTypeList(String fieldZoneId) throws EnjoyException{
		logger.info("[getTicketTypeList][Begin]");
		
		List<SeatReservationBean> 		returnList 							= null;
		SeatReservationBean				returnObj							= null;
		SessionFactory 					sessionFactory						= null;
		Session 						session								= null;
		String							hql									= null;
		List<Object[]>			 		list								= null;
		SQLQuery 						query 								= null;
		String							bookingTypeId						= null;
		String							bookingTypeImage					= null;
		
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			returnList		= new ArrayList<SeatReservationBean>();
			
			hql				= "select a.bookingTypeId, b.bookingTypeName, a.bookingPrices"
								+ " from fieldzonedetail a, bookingtype b"
								+ " where a.bookingTypeId = b.bookingTypeId"
									+ " and a.fieldZoneId  = '" + fieldZoneId + "'";
			query			= session.createSQLQuery(hql);
			
			query.addScalar("bookingTypeId"			, new StringType());
			query.addScalar("bookingTypeName"		, new StringType());
			query.addScalar("bookingPrices"			, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getTicketTypeList] list :: " + list.size());
			for(Object[] row : list){
				returnObj 		= new SeatReservationBean();
				bookingTypeId	= row[0].toString();
				
				logger.info("[getTicketTypeList] bookingTypeId 			:: " + bookingTypeId);
				logger.info("[getTicketTypeList] bookingTypeName		:: " + row[1].toString());
				logger.info("[getTicketTypeList] bookingPrices		 	:: " + row[2].toString());
				
				returnObj.setBookingTypeId		(bookingTypeId);
				returnObj.setBookingTypeName	(row[1].toString());
				returnObj.setBookingPrices		(row[2].toString());
				
				if(CHILD_TYPE.equals(bookingTypeId)){
					bookingTypeImage = "child.png";
				}else if(MAN_TYPE.equals(bookingTypeId)){
					bookingTypeImage = "man.png";
				}else if(OLD_TYPE.equals(bookingTypeId)){
					bookingTypeImage = "old.png";
				}else if(VIP_TYPE.equals(bookingTypeId)){
					bookingTypeImage = "vip.png";
				}else{
					bookingTypeImage = "other.png";
				}
				
				returnObj.setBookingTypeImage(bookingTypeImage);
				
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
			logger.info("[getTicketTypeList][End]");
		}
		
		return returnList;
	}
	
	public List<SeatReservationBean> getSeatBookingList(String matchId, String fieldZoneId) throws EnjoyException{
		logger.info("[getSeatBookingList][Begin]");
		
		List<SeatReservationBean> 		returnList 							= null;
		SeatReservationBean				returnObj							= null;
		SessionFactory 					sessionFactory						= null;
		Session 						session								= null;
		String							hql									= null;
		List<Object[]>			 		list								= null;
		SQLQuery 						query 								= null;
		
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			returnList		= new ArrayList<SeatReservationBean>();
			
			hql				= "select ticketId, seatingNo"
								+ " FROM ticketorder"
								+ " where matchId 			= '" + matchId + "'"
									+ " and fieldZoneId		= '" + fieldZoneId + "'"
									+ " and ticketStatus 	<> 'R'";
			query			= session.createSQLQuery(hql);
			
			query.addScalar("ticketId"		, new StringType());
			query.addScalar("seatingNo"		, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getSeatBookingList] list :: " + list.size());
			for(Object[] row : list){
				returnObj = new SeatReservationBean();
				
				logger.info("[getSeatBookingList] ticketId 		:: " + row[0].toString());
				logger.info("[getSeatBookingList] seatingNo		:: " + row[1].toString());
				
				returnObj.setTicketId		(row[0].toString());
				returnObj.setSeatingNo		(row[1].toString());
				
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
			logger.info("[getSeatBookingList][End]");
		}
		
		return returnList;
	}
	
	
}
