package th.go.ticket.app.enjoy.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

import th.go.ticket.app.enjoy.bean.SeatReservationBean;
import th.go.ticket.app.enjoy.exception.EnjoyException;
import th.go.ticket.app.enjoy.model.Genseqticketid;
import th.go.ticket.app.enjoy.model.Ticketorder;
import th.go.ticket.app.enjoy.utils.EnjoyLogger;
import th.go.ticket.app.enjoy.utils.EnjoyUtils;
import th.go.ticket.app.enjoy.utils.HibernateUtil;

public class SeatReservationDao {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(SeatReservationDao.class);
	private static final String CHILD_TYPE	= "1";
	private static final String MAN_TYPE	= "2";
	private static final String OLD_TYPE	= "3";
	private static final String VIP_TYPE	= "4";
	
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
			
			hql				= "SELECT fieldZoneId, fieldZoneName, rows, seating, totalSeating, typeRowName, rowName, startSeatingNo"
								+ " FROM fieldzonemaster"
								+ " where fieldZoneId = '" + fieldZoneId + "'";
			query			= session.createSQLQuery(hql);
			
			query.addScalar("fieldZoneId"			, new StringType());
			query.addScalar("fieldZoneName"			, new StringType());
			query.addScalar("rows"					, new StringType());
			query.addScalar("seating"				, new StringType());
			query.addScalar("totalSeating"			, new StringType());
			query.addScalar("typeRowName"			, new StringType());
			query.addScalar("rowName"				, new StringType());
			query.addScalar("startSeatingNo"		, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getSeatForThisZone] list :: " + list.size());
			for(Object[] row : list){
				returnObj = new SeatReservationBean();
				
				logger.info("[getSeatForThisZone] fieldZoneId 			:: " + row[0].toString());
				logger.info("[getSeatForThisZone] fieldZoneName		 	:: " + row[1].toString());
				logger.info("[getSeatForThisZone] rows		 			:: " + row[2].toString());
				logger.info("[getSeatForThisZone] seating		 		:: " + row[3].toString());
				logger.info("[getSeatForThisZone] totalSeating		 	:: " + row[4].toString());
				logger.info("[getSeatForThisZone] typeRowName		 	:: " + row[5].toString());
				logger.info("[getSeatForThisZone] rowName			 	:: " + row[6].toString());
				logger.info("[getSeatForThisZone] startSeatingNo		:: " + row[7].toString());
				
				returnObj.setFieldZoneId		(row[0].toString());
				returnObj.setFieldZoneName		(row[1].toString());
				returnObj.setRows				(row[2].toString());
				returnObj.setSeating			(row[3].toString());
				returnObj.setTotalSeating		(row[4].toString());
				returnObj.setTypeRowName		(row[5].toString());
				returnObj.setRowName			(row[6].toString());
				returnObj.setStartSeatingNo		(row[7].toString());
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
	
	public SeatReservationBean getHeaderTicketReservation(String matchId, String fieldZoneId) throws EnjoyException{
		logger.info("[getHeaderTicketReservation][Begin]");
		
		SeatReservationBean				returnObj							= null;
		SessionFactory 					sessionFactory						= null;
		Session 						session								= null;
		String							hql									= null;
		List<Object[]>			 		list								= null;
		SQLQuery 						query 								= null;
		
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			
			hql				= "select b.fieldZoneName, b.fieldZoneNameTicket, a.awayTeamNameTH, a.awayTeamNameEN, a.season,  b.fieldZoneName"
								+ " from eventmatch a, fieldzonemaster b"
								+ " where a.matchId 		= '" + matchId + "'"
									+ " and b.fieldZoneId 	= '" + fieldZoneId + "'";
			query			= session.createSQLQuery(hql);
			
			query.addScalar("fieldZoneName"			, new StringType());
			query.addScalar("fieldZoneNameTicket"	, new StringType());
			query.addScalar("awayTeamNameTH"		, new StringType());
			query.addScalar("awayTeamNameEN"		, new StringType());
			query.addScalar("season"				, new StringType());
			query.addScalar("fieldZoneName"			, new StringType());
				
			list		 	= query.list();
			
			logger.info("[getHeaderTicketReservation] list :: " + list.size());
			for(Object[] row : list){
				returnObj = new SeatReservationBean();
				
				logger.info("[getHeaderTicketReservation] fieldZoneName 		:: " + row[0].toString());
				logger.info("[getHeaderTicketReservation] fieldZoneNameTicket	:: " + row[1].toString());
				logger.info("[getHeaderTicketReservation] awayTeamNameTH		:: " + row[2].toString());
				logger.info("[getHeaderTicketReservation] awayTeamNameEN		:: " + row[3].toString());
				logger.info("[getHeaderTicketReservation] season				:: " + row[4].toString());
				logger.info("[getHeaderTicketReservation] fieldZoneName			:: " + row[5].toString());
				
				returnObj.setFieldZoneName			(row[0].toString());
				returnObj.setFieldZoneNameTicket	(row[1].toString()==null||row[1].toString().equals("")?row[5].toString():row[1].toString());
				returnObj.setAwayTeamNameTH			(row[2].toString());
				returnObj.setAwayTeamNameEN			(row[3].toString());
				returnObj.setSeason					(row[4].toString());
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
			logger.info("[getHeaderTicketReservation][End]");
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
	
	public List<SeatReservationBean> getSeatBookingList(String matchId, String fieldZoneId, String season) throws EnjoyException{
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
			
			hql				= "select ticketId, seatingNo, userUniqueId, ticketStatus, bookingTypeId"
								+ " FROM ticketorder"
								+ " where matchId 			= '" + matchId + "'"
									+ " and fieldZoneId		= '" + fieldZoneId + "'"
									+ " and season			= '" + season + "'"
									+ " and ticketStatus 	<> 'R'";
			query			= session.createSQLQuery(hql);
			
			query.addScalar("ticketId"			, new StringType());
			query.addScalar("seatingNo"			, new StringType());
			query.addScalar("userUniqueId"		, new StringType());
			query.addScalar("ticketStatus"		, new StringType());
			query.addScalar("bookingTypeId"		, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getSeatBookingList] list :: " + list.size());
			for(Object[] row : list){
				returnObj = new SeatReservationBean();
				
				logger.info("[getSeatBookingList] ticketId 				:: " + row[0].toString());
				logger.info("[getSeatBookingList] seatingNo				:: " + row[1].toString());
				logger.info("[getSeatBookingList] ticketUserUniqueId	:: " + row[2].toString());
				logger.info("[getSeatBookingList] ticketStatus			:: " + row[3].toString());
				logger.info("[getSeatBookingList] bookingTypeId			:: " + row[4].toString());
				
				returnObj.setTicketId			(row[0].toString());
				returnObj.setSeatingNo			(row[1].toString());
				returnObj.setTicketUserUniqueId	(row[2].toString());
				returnObj.setTicketStatus		(row[3].toString());
				returnObj.setSeatBookingTypeId	(row[4].toString());
				
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
	
	public void insertTicketorder(Session session, SeatReservationBean seatReservationBean) throws EnjoyException{
		logger.info("[insertNewUser][Begin]");
		
		Ticketorder						ticketorderlDb						= null;
		
		try{
			
			ticketorderlDb 	= new Ticketorder();
			
			ticketorderlDb.setTicketId(seatReservationBean.getTicketId());
			ticketorderlDb.setSeatingNo(seatReservationBean.getSeatingNo());
			ticketorderlDb.setMatchId(Integer.parseInt(seatReservationBean.getMatchId()));
			ticketorderlDb.setSeason(Integer.parseInt(seatReservationBean.getSeason()));
			ticketorderlDb.setFieldZoneId(Integer.parseInt(seatReservationBean.getFieldZoneId()));
			ticketorderlDb.setBookingTypeId(Integer.parseInt(seatReservationBean.getBookingTypeId()));
			ticketorderlDb.setUserUniqueId(Integer.parseInt(seatReservationBean.getUserUniqueId()));
			ticketorderlDb.setTicketStatus(seatReservationBean.getTicketStatus());
			ticketorderlDb.setSaleDate(EnjoyUtils.currDateThai());
			
			session.saveOrUpdate(ticketorderlDb);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการบันทึกข้อมูล");
		}finally{
			ticketorderlDb 	= null;
			logger.info("[insertNewUser][End]");
		}
	}
	
	//ยกเลิกที่นั่งโดยตัวผู้ใช้ที่จองที่นั่งนี้เอง
	public void cancelReservationByUser(Session session, SeatReservationBean seatReservationBean) throws EnjoyException{
		logger.info("[cancelReservationByUser][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		int 							result								= 0;
		
		try{
			hql				= "delete Ticketorder t"
							+ " where t.seatingNo	 			= :seatingNo"
								+ " and t.userUniqueId 			= :userUniqueId"
								+ " and t.ticketStatus			= :ticketStatus";
			
			query = session.createQuery(hql);
			query.setParameter("seatingNo"		, seatReservationBean.getSeatingNo());
			query.setParameter("userUniqueId"	, Integer.parseInt(seatReservationBean.getUserUniqueId()));
			query.setParameter("ticketStatus"	, seatReservationBean.getTicketStatus());
			
			result = query.executeUpdate();			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการลบข้อมูล");
		}finally{
			
			hql									= null;
			query 								= null;
			logger.info("[cancelReservationByUser][End]");
		}
	}
	
	//ลบการจองของผู้ใช้นี้ทั้งหมด
	public void clearReservationForUser(Session session, SeatReservationBean seatReservationBean) throws EnjoyException{
		logger.info("[cancelReservationByUser][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		int 							result								= 0;
		
		try{
			hql				= "delete Ticketorder t"
							+ " where t.id.userUniqueId 		= :userUniqueId"
								+ " and t.ticketStatus			= 'P'";
			
			query = session.createQuery(hql);
			query.setParameter("userUniqueId"	, Integer.parseInt(seatReservationBean.getUserUniqueId()));
			
			result = query.executeUpdate();			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการลบข้อมูล");
		}finally{
			
			hql									= null;
			query 								= null;
			logger.info("[cancelReservationByUser][End]");
		}
	}
	
	//Check SeatNo before save
	public int checkSeatNo(SeatReservationBean seatReservationBean) throws EnjoyException{
		logger.info("[checkSeatNo][Begin]");
		
		String							hql									= null;
		List<Integer>			 		list								= null;
		SQLQuery 						query 								= null;
		int 							result								= 1;
		SessionFactory 					sessionFactory						= null;
		Session 						session								= null;
		
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			
			hql				= "select count(*) cou from ticketorder"
								+ " where seatingNo 	= '" + seatReservationBean.getSeatingNo() + "'"
									+ " and matchId 	= '" + seatReservationBean.getMatchId() + "'"
									+ " and fieldZoneId = '" + seatReservationBean.getFieldZoneId() + "'"
									+ " and ticketStatus <> 'R'"
									+ " and saleDate	= '" + EnjoyUtils.currDateThai() + "'";
			
			
			query			= session.createSQLQuery(hql);
			
			query.addScalar("cou"			, new IntegerType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				result = list.get(0);
			}
			
			logger.info("[checkSeatNo] result 			:: " + result);
			
			
			
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			
			hql									= null;
			list								= null;
			query 								= null;
			logger.info("[checkSeatNo][End]");
		}
		
		return result;
	}
	
	public void updateStatusPendingToActive(Session session, int userUniqueId) throws EnjoyException{
		logger.info("[updateStatusPendingToActive][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		int 							result								= 0;
		
		try{
			hql				= "update Ticketorder t set t.ticketStatus = 'A'"
							+ " where t.ticketStatus	 		= 'P'"
								+ " and t.userUniqueId 			= :userUniqueId"
								+ " and t.saleDate				= :saleDate";
			
			query = session.createQuery(hql);
			
			query.setParameter("userUniqueId"	, userUniqueId);
			query.setParameter("saleDate"		, EnjoyUtils.currDateThai());
			
			result = query.executeUpdate();			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการอัพเดทข้อมูล");
		}finally{
			
			hql									= null;
			query 								= null;
			logger.info("[updateStatusPendingToActive][End]");
		}
	}
	
	public List<String> getAllTicketIdIsPending(int userUniqueId) throws EnjoyException{
		logger.info("[getAllTicketIdIsPending][Begin]");
		
		String							hql									= null;
		List<String> 					returnList 							= null;
		List<String>			 		list								= null;
		SQLQuery 						query 								= null;
		SessionFactory 					sessionFactory						= null;
		Session 						session								= null;
		
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			returnList		= new ArrayList<String>();
			
			logger.info("[getAllTicketIdIsPending] userUniqueId :: " + userUniqueId);
			
			hql				= "select t.ticketId from ticketorder t"
								+ " where t.ticketStatus	 		= 'P'"
									+ " and t.userUniqueId 			= " + userUniqueId
									+ " and t.saleDate				= '" + EnjoyUtils.currDateThai() + "'";
			
			
			query			= session.createSQLQuery(hql);
			
			query.addScalar("ticketId"			, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getAllTicketIdIsPending] list :: " + list.size());
			for(String ticketId : list){
				
				logger.info("[getAllTicketIdIsPending] ticketId 				:: " + ticketId);
				
				returnList.add(ticketId);
			}
			
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			
			hql									= null;
			list								= null;
			query 								= null;
			logger.info("[getAllTicketIdIsPending][End]");
		}
		
		return returnList;
	}
	
	public Integer getBookingPrices(String fieldZoneId, String bookingTypeId) throws EnjoyException{
		logger.info("[getBookingPrices][Begin]");
		
		String							hql									= null;
		List<Integer>			 		list								= null;
		SQLQuery 						query 								= null;
		Integer 						result								= null;
		SessionFactory 					sessionFactory						= null;
		Session 						session								= null;
		
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			
			hql				= "select bookingPrices from fieldzonedetail"
								+ " where fieldZoneId 		= '" + fieldZoneId + "'"
									+ " and bookingTypeId 	= '" + bookingTypeId + "'";
			
			
			query			= session.createSQLQuery(hql);
			
			query.addScalar("bookingPrices"			, new IntegerType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				result = list.get(0);
			}
			
			logger.info("[getBookingPrices] result 			:: " + result);
			
			
			
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			
			hql									= null;
			list								= null;
			query 								= null;
			logger.info("[getBookingPrices][End]");
		}
		
		return result;
	}
	
	public Integer getTicketSeq(String season, String matchId) throws EnjoyException{
		logger.info("[getTicketSeq][Begin]");
		
		String							hql									= null;
		List<Integer>			 		list								= null;
		SQLQuery 						query 								= null;
		Integer 						result								= null;
		SessionFactory 					sessionFactory						= null;
		Session 						session								= null;
		
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			
			hql				= "select ticketSeq from genseqticketid"
								+ " where season 		= '" + season + "'"
									+ " and matchId 	= '" + matchId + "'";
			
			
			query			= session.createSQLQuery(hql);
			
			query.addScalar("ticketSeq"			, new IntegerType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				result = list.get(0);
			}
			
			logger.info("[getTicketSeq] result 			:: " + result);
			
			
			
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			
			hql									= null;
			list								= null;
			query 								= null;
			logger.info("[getBookingPrices][End]");
		}
		
		return result;
	}
	
	public void insertGenseqticketid(Session session, Genseqticketid genseqticketid) throws EnjoyException{
		logger.info("[insertGenseqticketid][Begin]");
		
		try{
			
			session.saveOrUpdate(genseqticketid);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการบันทึกข้อมูล");
		}finally{
			logger.info("[insertGenseqticketid][End]");
		}
	}
	
	public void updateGenseqticketid(Session session, int season, int matchId, int ticketSeq) throws EnjoyException{
		logger.info("[updateStatusPendingToActive][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		int 							result								= 0;
		
		try{
			hql				= "update Genseqticketid t set t.ticketSeq = :ticketSeq"
							+ " where"
								+ " t.id.season 			= :season"
								+ " and t.id.matchId		= :matchId";
			
			query = session.createQuery(hql);
			
			query.setParameter("ticketSeq"	, ticketSeq);
			query.setParameter("season"		, season);
			query.setParameter("matchId"	, matchId);
			
			result = query.executeUpdate();			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการอัพเดทข้อมูล");
		}finally{
			
			hql									= null;
			query 								= null;
			logger.info("[updateStatusPendingToActive][End]");
		}
	}
	
}
