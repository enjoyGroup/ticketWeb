package th.go.ticket.app.enjoy.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StringType;

import th.go.ticket.app.enjoy.bean.SeatZoneBean;
import th.go.ticket.app.enjoy.exception.EnjoyException;
import th.go.ticket.app.enjoy.utils.EnjoyLogger;
import th.go.ticket.app.enjoy.utils.EnjoyUtils;
import th.go.ticket.app.enjoy.utils.HibernateUtil;

public class SeatZoneDao {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(SeatZoneDao.class);
	
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
	
	public List<SeatZoneBean> seasonList() throws EnjoyException{
		logger.info("[seasonList][Begin]");
		
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
			
			hql				= "SELECT b.matchId, b.season, b.awayTeamNameTH"
								+ " FROM eventmatch b"
								+ " where"
//									+ "STR_TO_DATE(b.matchDate, '%Y%m%d') >= STR_TO_DATE('25570101', '%Y%m%d')"
									+ " STR_TO_DATE(b.matchDate, '%Y%m%d') >= STR_TO_DATE('" + EnjoyUtils.currDateThai() + "', '%Y%m%d')"
								+ " order by b.season desc, b.matchDate desc, b.matchId desc";
			
			logger.info("[seasonList] hql :: " + hql);
			
			query			= session.createSQLQuery(hql);
			
			query.addScalar("matchId"			, new StringType());
			query.addScalar("season"			, new StringType());
			query.addScalar("awayTeamNameTH"	, new StringType());
			
			list		 	= query.list();
			
			logger.info("[seasonList] list :: " + list.size());
			for(Object[] row : list){
				returnObj = new SeatZoneBean();
				
				logger.info("[seasonList] matchId 			:: " + row[0].toString());
				logger.info("[seasonList] season		 	:: " + row[1].toString());
				logger.info("[seasonList] awayTeamNameTH 	:: " + row[2].toString());
				
				returnObj.setMatchId		(row[0].toString());
				returnObj.setSeason			(row[1].toString());
				returnObj.setAwayTeamNameTH	(row[2].toString());
				
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
			logger.info("[seasonList][End]");
		}
		
		return returnList;
	}
	
	public List<SeatZoneBean> getFieldZoneMaster() throws EnjoyException{
		logger.info("[getFieldZoneMaster][Begin]");
		
		List<SeatZoneBean> 				returnList 							= null;
		SeatZoneBean					returnObj							= null;
		SessionFactory 					sessionFactory						= null;
		Session 						session								= null;
		String							hql									= null;
		List<Object[]>			 		list								= null;
		SQLQuery 						query 								= null;
		int								startSeat							= 0;
		int								endSeat								= 0;
		String							ticketName							= null;
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			returnList		= new ArrayList<SeatZoneBean>();
			
			hql				= "SELECT fieldZoneId, fieldZoneName, fieldZoneNameTicket, startSeatingNo, seating"
								+ " FROM fieldzonemaster"
								+ " order by fieldZoneId asc";
			query			= session.createSQLQuery(hql);
			
			query.addScalar("fieldZoneId"			, new StringType());
			query.addScalar("fieldZoneName"			, new StringType());
			query.addScalar("fieldZoneNameTicket"	, new StringType());
			query.addScalar("startSeatingNo"		, new StringType());
			query.addScalar("seating"				, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getFieldZoneMaster] list :: " + list.size());
			for(Object[] row : list){
				returnObj = new SeatZoneBean();
				
				logger.info("[getFieldZoneMaster] fieldZoneId 			:: " + row[0].toString());
				logger.info("[getFieldZoneMaster] fieldZoneName		 	:: " + row[1].toString());
				logger.info("[getFieldZoneMaster] fieldZoneNameTicket	:: " + row[2].toString());
				logger.info("[getFieldZoneMaster] startSeatingNo		:: " + row[3].toString());
				logger.info("[getFieldZoneMaster] seating				:: " + row[4].toString());
				
				startSeat	= Integer.parseInt(row[3].toString());
				ticketName  = row[1].toString();
				if (startSeat > 0) {
					endSeat		= (startSeat + Integer.parseInt(row[4].toString())) + 1;
					ticketName  = ticketName + "(ที่นั่ง " + String.valueOf(startSeat) + "-" + String.valueOf(endSeat) + ")";
				}
				returnObj.setFieldZoneId			(row[0].toString());
				returnObj.setFieldZoneName			(ticketName);
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
	
	
}
