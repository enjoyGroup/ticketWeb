package th.go.ticket.app.enjoy.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StringType;

import th.go.ticket.app.enjoy.bean.DetailRevenueOfYearBean;
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
			
			hql				= "SELECT b.matchId, b.season, b.awayTeamNameTH "
								+ "FROM eventmatch b "
								+ "where "
									+ "STR_TO_DATE(b.matchDate, '%Y%m%d') >= STR_TO_DATE('" + EnjoyUtils.currDateThai() + "', '%Y%m%d')"
								+ " order by b.season, b.matchId asc";
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
		
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			returnList		= new ArrayList<SeatZoneBean>();
			
			hql				= "SELECT fieldZoneId, fieldZoneName"
								+ " FROM fieldzonemaster"
								+ " order by fieldZoneId asc";
			query			= session.createSQLQuery(hql);
			
			query.addScalar("fieldZoneId"			, new StringType());
			query.addScalar("fieldZoneName"			, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getFieldZoneMaster] list :: " + list.size());
			for(Object[] row : list){
				returnObj = new SeatZoneBean();
				
				logger.info("[getFieldZoneMaster] fieldZoneId 			:: " + row[0].toString());
				logger.info("[getFieldZoneMaster] fieldZoneName		 	:: " + row[1].toString());
				
				returnObj.setFieldZoneId		(row[0].toString());
				returnObj.setFieldZoneName		(row[1].toString());
				
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
