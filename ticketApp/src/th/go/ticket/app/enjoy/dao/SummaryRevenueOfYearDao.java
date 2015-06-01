package th.go.ticket.app.enjoy.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.DoubleType;
import org.hibernate.type.StringType;

import th.go.ticket.app.enjoy.bean.SummaryRevenueOfYearBean;
import th.go.ticket.app.enjoy.exception.EnjoyException;
import th.go.ticket.app.enjoy.utils.EnjoyLogger;
import th.go.ticket.app.enjoy.utils.HibernateUtil;

public class SummaryRevenueOfYearDao {
private static final EnjoyLogger logger = EnjoyLogger.getLogger(SummaryRevenueOfYearDao.class);
	
//	public static void main(String[] args) {
//		try {
//			EnjoyLogger.initial(true);
//			List<SummaryRevenueOfYearBean> list = summaryRevenueOfYear();
//			SummaryRevenueOfYearBean bean	= null;
//			
//			for(int i=0;i<list.size();i++){
//				bean = list.get(i);
//				
//				logger.info("[main] season :: " + bean.getSeason());
//				logger.info("[main] sumBookingPrices :: " + bean.getBookingPrice());
//				
//			}
//			
//			
//		} catch (EnjoyException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	public List<SummaryRevenueOfYearBean> summaryRevenueOfYear() throws EnjoyException{
		logger.info("[summaryRevenueOfYear][Begin]");
		
		List<SummaryRevenueOfYearBean> 	returnList 							= null;
		SessionFactory 					sessionFactory						= null;
		Session 						session								= null;
		String							hql									= null;
		List<Object[]>			 		list								= null;
		SQLQuery 						query 								= null;
		SummaryRevenueOfYearBean		returnObj							= null;
		int								padSeason							= 0;
		String							season								= null;
		int								seasonTemp							= 0;
		
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			returnList		= new ArrayList<SummaryRevenueOfYearBean>();
			
			hql				= "SELECT c.season as season, sum(b.bookingPrices) as sumBookingPrices" 
									+ " FROM ticketorder a, fieldzonedetail b, eventmatch c"
									+ " WHERE a.fieldZoneId		= b.fieldZoneId"
										+ " and a.bookingTypeId	= b.bookingTypeId"
								 		+ " and a.matchId		= c.matchId"
								 		+ " and a.season 		= c.season"
								 		+ " and a.ticketStatus <> 'R'"
									+ " group by c.season"
									+ " order by a.season desc";
			
			logger.info("[summaryRevenueOfYear] hql :: " + hql);
			
			query			= session.createSQLQuery(hql);
			
			query.addScalar("season"			, new StringType());
			query.addScalar("sumBookingPrices"	, new DoubleType());
			
			list		 	= query.list();
			
			for(Object[] row : list){
				returnObj 	= new SummaryRevenueOfYearBean();
				season		= row[0].toString();
				
				logger.info("[summaryRevenueOfYear] season 				:: " + season);
				logger.info("[summaryRevenueOfYear] sumBookingPrices 	:: " + row[1].toString());
				
				returnObj.setSeason(season);
				returnObj.setBookingPrice(row[1].toString());
				
				returnList.add(returnObj);
				
			}
			
			logger.info("[summaryRevenueOfYear] list.size() :: " + list.size());
			
			if(list.size() < 5){
				padSeason 	= 5-list.size();
				seasonTemp	= Integer.parseInt(season);
				
				for(int i=0;i < padSeason;i++){
					returnObj = new SummaryRevenueOfYearBean();
					
					seasonTemp = seasonTemp - 1;
					
					returnObj.setSeason(String.valueOf(seasonTemp));
					returnObj.setBookingPrice("0");
					
					returnList.add(returnObj);
					
				}
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
			logger.info("[summaryRevenueOfYear][End]");
		}
		
		return returnList;
	}
	
}
