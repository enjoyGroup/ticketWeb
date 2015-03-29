package th.go.ticket.app.enjoy.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StringType;

import th.go.ticket.app.enjoy.bean.DetailRevenueOfYearBean;
import th.go.ticket.app.enjoy.exception.EnjoyException;
import th.go.ticket.app.enjoy.utils.EnjoyLogger;
import th.go.ticket.app.enjoy.utils.HibernateUtil;

public class DetailRevenueOfYearDao {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(DetailRevenueOfYearDao.class);
	
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
	
	public List<DetailRevenueOfYearBean> detailRevenueByYear(String season) throws EnjoyException{
		logger.info("[detailRevenueByYear][Begin]");
		
		List<DetailRevenueOfYearBean> 	returnList 							= null;
		SessionFactory 					sessionFactory						= null;
		Session 						session								= null;
		String							hql									= null;
		List<Object[]>			 		list								= null;
		SQLQuery 						query 								= null;
		DetailRevenueOfYearBean			returnObj							= null;
		
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			returnList		= new ArrayList<DetailRevenueOfYearBean>();
			
			hql				= "SELECT 	 a.matchId"
									+ ", c.awayTeamNameTH"
									+ ", c.awayTeamNameEN"
									+ ", count(a.matchId) as totalSeating "
									+ ", SUM(b.bookingPrices) as bookingPrices"
									+ " FROM ticketorder a, fieldzonedetail b, eventmatch c"
									+ " WHERE a.fieldZoneId 	= b.fieldZoneId"
									+ " 	and a.bookingTypeId = b.bookingTypeId"
									+ " 	and a.matchId 		= c.matchId"
									+ " 	and c.season  		= '" + season + "'"
									+ "		and a.ticketStatus 	<> 'R'"
									+ " GROUP BY a.matchId, c.awayTeamNameTH, c.awayTeamNameEN"
									+ " ORDER BY a.matchId";
			query			= session.createSQLQuery(hql);
			
			query.addScalar("matchId"			, new StringType());
			query.addScalar("awayTeamNameTH"	, new StringType());
			query.addScalar("awayTeamNameEN"	, new StringType());
			query.addScalar("totalSeating"		, new StringType());
			query.addScalar("bookingPrices"		, new StringType());
			
			list		 	= query.list();
			
			for(Object[] row : list){
				returnObj = new DetailRevenueOfYearBean();
				
				logger.info("[detailRevenueByYear] matchId 			:: " + row[0].toString());
				logger.info("[detailRevenueByYear] awayTeamNameTH 	:: " + row[1].toString());
				logger.info("[detailRevenueByYear] awayTeamNameEN 	:: " + row[2].toString());
				logger.info("[detailRevenueByYear] totalSeating 	:: " + row[3].toString());
				logger.info("[detailRevenueByYear] bookingPrices 	:: " + row[4].toString());
				
				returnObj.setMatchId		(row[0].toString());
				returnObj.setAwayTeamNameTH	(row[1].toString());
				returnObj.setAwayTeamNameEN	(row[2].toString());
				returnObj.setTotalSeating	(row[3].toString());
				returnObj.setBookingPrices	(row[4].toString());
				
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
			logger.info("[detailRevenueByYear][End]");
		}
		
		return returnList;
	}
	
	
}
