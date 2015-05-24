package th.go.ticket.app.enjoy.dao;
 
   
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
   
import th.go.ticket.app.enjoy.bean.FieldZoneDetailBean;
import th.go.ticket.app.enjoy.bean.FieldzonemasterBean;
import th.go.ticket.app.enjoy.bean.SeatingDetailBean;
import th.go.ticket.app.enjoy.exception.EnjoyException;  
import th.go.ticket.app.enjoy.model.Fieldzonedetail;
import th.go.ticket.app.enjoy.model.FieldzonedetailPK;
import th.go.ticket.app.enjoy.model.Fieldzonemaster;
import th.go.ticket.app.enjoy.utils.EnjoyLogger;
import th.go.ticket.app.enjoy.utils.EnjoyUtils;
import th.go.ticket.app.enjoy.utils.HibernateUtil;

public class SeatingDetailDao {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(SeatingDetailDao.class);
  
	@SuppressWarnings("unchecked")
	public List<FieldzonemasterBean> getZoneList() throws EnjoyException{
		logger.info("[zoneList][Begin]");
		
		List<FieldzonemasterBean> 		returnList 							= null;
		FieldzonemasterBean             fieldzonemasterBean                 = null;
		SessionFactory 					sessionFactory						= null;
		Session 						session								= null;
		String							hql									= null;
		List<Object[]>	 				list								= null;
		SQLQuery 						query 								= null;
		String							zone								= null;
		int								zoneiD								= 0;
		FieldzonemasterBean             returnObj                           = null;
		
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			session.getTransaction().begin();
			returnList		= new ArrayList<FieldzonemasterBean>();
 
			 hql        = "SELECT fieldZoneId,fieldZoneName from fieldzonemaster order by fieldZoneName asc" ;  
			 query  	= session.createSQLQuery(hql); 
			 
			 query.addScalar("fieldZoneId"	    , new IntegerType());  
			 query.addScalar("fieldZoneName"	, new StringType());  
		
			 list		 	= query.list();
			
			 logger.info("[zoneList] list :: " + list.size());
 
			
			for(Object[] row : list){
				returnObj = new FieldzonemasterBean();
				
				logger.info("[getZoneList] fieldZoneId 		:: " + row[0].toString());
				logger.info("[getZoneList] fieldZoneName 	:: " + row[1].toString());
				
				returnObj.setFieldZoneId(Integer.valueOf(row[0].toString()));
				returnObj.setFieldZoneName(row[1].toString()); 
				returnList.add(returnObj);
			}
			
			
			logger.info("[zoneList] returnList :: " + returnList.size()); 
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
			logger.info("[zoneList][End]");
		}
		
		return returnList;
	}
	
	public List<SeatingDetailBean> getDetailSeatingByZoneName(int zoneId) throws EnjoyException {
		logger.info("[getDetailSeatingByZoneName][Begin]");
		List<SeatingDetailBean> 		returnList 							= null; 
		SessionFactory 					sessionFactory						= null;
		Session 						session								= null;
		String							sql									= null;
		List<Object[]>	 				list								= null;
		SeatingDetailBean               returnObj                           = null;
		SQLQuery 						query 								= null;
		String							zone								= null;
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			session.getTransaction().begin();  
			returnList		= new ArrayList<SeatingDetailBean>();
			
			sql				= "SELECT 	a.fieldZoneId,a.typeRowName,a.rowName"
									+ ", a.fieldZoneName, a.rows, a.seating, a.totalSeating" 
									+ ", a.fieldZoneNameTicket, a.startSeatingNo "
									+ " FROM fieldzonemaster a, fieldzonedetail  b, bookingtype  c"
									+ " WHERE a.fieldZoneId  = b.fieldZoneId "
									+ " and b.bookingTypeId = c.bookingTypeId " 
									+ " and a.fieldZoneId =  '" + zoneId + "'" 
									+ " ORDER BY a.fieldZoneId";
			query			= session.createSQLQuery(sql);
			
			query.addScalar("fieldZoneId"		, new IntegerType());
			query.addScalar("typeRowName"		, new IntegerType());
			query.addScalar("rowName"		    , new StringType());
			query.addScalar("fieldZoneName"	    , new StringType());
			query.addScalar("rows"				, new IntegerType());
			query.addScalar("seating"			, new IntegerType());
			query.addScalar("totalSeating"		, new IntegerType()); 
			query.addScalar("fieldZoneNameTicket" , new StringType());
			query.addScalar("startSeatingNo"	  , new IntegerType());
			
			
			list		 	= query.list();
			
			for(Object[] row : list){
				returnObj = new SeatingDetailBean();
				
				logger.info("[DetailSeatingByZoneName] fieldZoneId 		:: " + row[0].toString());
				logger.info("[DetailSeatingByZoneName] typeRowName 		:: " + row[1].toString());
				logger.info("[DetailSeatingByZoneName] rowName 		    :: " + row[2].toString());
				logger.info("[DetailSeatingByZoneName] fieldZoneName 	:: " + row[3].toString());
				logger.info("[DetailSeatingByZoneName] rows 			:: " + row[4].toString());
				logger.info("[DetailSeatingByZoneName] seating 			:: " + row[5].toString());
				logger.info("[DetailSeatingByZoneName] totalSeating 	:: " + row[6].toString()); 
				logger.info("[DetailSeatingByZoneName] fieldZoneNameTicket 	:: " + row[7].toString());
				logger.info("[DetailSeatingByZoneName] startSeatingNo 	:: " + row[8].toString());
				
				returnObj.setFieldZoneId(Integer.valueOf(row[0].toString()));
				returnObj.setTypeRowName(Integer.valueOf(row[1].toString()));
				returnObj.setNameRow(row[2].toString());
				returnObj.setFieldZoneName(row[3].toString());
				returnObj.setRows(Integer.valueOf(row[4].toString()));
				returnObj.setSeating(Integer.valueOf(row[5].toString()));
				returnObj.setTotalSeating(Integer.valueOf(row[6].toString())); 
				returnObj.setFieldZoneNameTicket(row[7].toString());
				returnObj.setStartSeatingNo(Integer.valueOf(row[8].toString())); 
			}
			
			returnObj.setFieldZoneDetailBeans(this.getDetailSeatingByZoneId(zoneId));
			returnList.add(returnObj);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			session.close(); 
			sessionFactory						= null;
			session								= null;
			sql									= null;
			list								= null;
			query 								= null;
			logger.info("[getDetailSeatingByZoneName][End]");
		}
		
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public List<FieldZoneDetailBean>  getDetailSeatingByZoneId(int zoneId) throws EnjoyException {
		logger.info("[getDetailSeatingByZoneId][Begin]");
		List<SeatingDetailBean> 		returnList 							= null; 
		SessionFactory 					sessionFactory						= null;
		Session 						session								= null;
		String							sql									= null;
		List<Object[]>	 				list								= null;
		SeatingDetailBean               returnObj                           = null;
		SQLQuery 						query 								= null;
		String							zone								= null;
		List<FieldZoneDetailBean>       fieldZoneDetailBeans                = null;
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			session.getTransaction().begin();  
			returnList		= new ArrayList<SeatingDetailBean>();
			
			sql				= "SELECT  b.bookingTypeId, c.bookingTypeName, b.bookingPrices ,b.seq" 
									+ " FROM fieldzonedetail  b, bookingtype  c"
									+ " WHERE b.bookingTypeId = c.bookingTypeId " 
									+ " and b.fieldZoneId =  '" + zoneId + "'" 
									+ " ORDER BY b.fieldZoneId";
			query			= session.createSQLQuery(sql);
		 
			query.addScalar("bookingTypeId"		, new IntegerType());
			query.addScalar("bookingTypeName"	, new StringType());
			query.addScalar("bookingPrices"		, new DoubleType()); 
			query.addScalar("seq"		        , new IntegerType());
			  
			list		 	= query.list();

			returnObj = new SeatingDetailBean();
			fieldZoneDetailBeans = new ArrayList<>();
			FieldZoneDetailBean bean = null;
			for(Object[] row : list){
				 
				logger.info("[DetailSeatingByZoneName] bookingTypeId 	:: " + row[0].toString());
				logger.info("[DetailSeatingByZoneName] bookingTypeName 	:: " + row[1].toString());
				logger.info("[DetailSeatingByZoneName] bookingPrices 	:: " + row[2].toString());
				logger.info("[DetailSeatingByZoneName] seq 	:: " + row[3].toString());
				  
				bean = new FieldZoneDetailBean();
				bean.setBookingTypeId(Integer.valueOf(row[0].toString()));
				bean.setBookingTypeName(row[1].toString());
				bean.setBookingPrices(Double.valueOf(row[2].toString()));
				bean.setSeq(Integer.valueOf(row[3].toString()));
				bean.setFieldZoneId(zoneId);
				fieldZoneDetailBeans.add(bean);
			}
			 
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			session.close(); 
			sessionFactory						= null;
			session								= null;
			sql									= null;
			list								= null;
			query 								= null;
			logger.info("[getDetailSeatingByZoneId][End]");
		}
		
		return fieldZoneDetailBeans;
	}
	
	public List<String> getBookNameList(String bookName)throws EnjoyException {
		System.out.println("[MotorDetailDao][branchNameList][Begin]");		
		String 							sql			 		= null;
		ResultSet 						rs 					= null;
		List<Object[]>                  list                = null;
        List<String> 			        returnList 			= null;
        String                          returnObj           = null;
        SessionFactory 					sessionFactory		= null;
		Session 						session				= null;
		SQLQuery                        query               = null;
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			session.getTransaction().begin();  
			returnList		= new ArrayList<String>();
			sql 		    = " Select bookingTypeId, bookingTypeName from bookingtype where bookingTypeName LIKE ('" + bookName + "%') ORDER BY bookingTypeName ASC LIMIT 10 ";			
			query  	        = session.createSQLQuery(sql); 
			 
			 query.addScalar("bookingTypeId"	 , new IntegerType());  
			 query.addScalar("bookingTypeName"	, new StringType());  
		
			 list		 	= query.list();
			
			 logger.info("[seasonList] list :: " + list.size());
 
			
			for(Object[] row : list){ 
				returnList.add(row[1].toString());
			}
		    
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close(); 
			sessionFactory						= null;
			session								= null;
			sql									= null;
			list								= null;
			query 								= null;
			System.out.println("[MotorDetailDao][brandNameList][End]");
		}
		
		return returnList;
	}
	
	public void insertSeatingMaster(Session session,SeatingDetailBean seatingDetailBean) throws EnjoyException{
		logger.info("[insertSeatingMaster][Begin]");
		  
		String							sql				  		= null; 
		SQLQuery 						query 			  		= null; 
		int                             result            		= 0;
		Fieldzonemaster                 fieldzonemasterDB       = null; 
		int 							zoneId 					= 0;
		try{ 
			fieldzonemasterDB = new Fieldzonemaster(); 
			fieldzonemasterDB.setFieldZoneName(seatingDetailBean.getFieldZoneName());
			fieldzonemasterDB.setRows(seatingDetailBean.getRows());
			fieldzonemasterDB.setSeating(seatingDetailBean.getSeating());
			fieldzonemasterDB.setTotalSeating(seatingDetailBean.getTotalSeating());
			fieldzonemasterDB.setTypeRowName(seatingDetailBean.getTypeRowName());
			fieldzonemasterDB.setRowName(seatingDetailBean.getNameRow());
			fieldzonemasterDB.setFieldZoneNameTicket(seatingDetailBean.getFieldZoneNameTicket());
			fieldzonemasterDB.setStartSeatingNo(seatingDetailBean.getStartSeatingNo());
			session.save(fieldzonemasterDB);   
			session.flush(); 
			session.clear(); 
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage()); 
			throw new EnjoyException("เกิดข้อผิดพลาดในการ บันทึกข้อมูล"); 
		}finally{   
			query 								= null;
			sql                                 = null;
			result                              = 0;
			fieldzonemasterDB       			= null;
			logger.info("[insertSeatingMaster][End]");
		}
		 
	}
	
	public int findFieldZoneMasterId(String fieldZoneName) throws EnjoyException{
		logger.info("[insertSeatingDetail][Begin]");
		  
		String							sql				  		= null; 
		SQLQuery 						query 			  		= null;  
		SessionFactory 		   			sessionFactory	  		= null;
		Session 			   			session  		  		= null;
		int                             returnId                = 0; 
		List<Integer>                   list                    = null;
		try{ 
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			session.getTransaction().begin();
			 
			 sql        = "SELECT fieldZoneId from Fieldzonemaster where fieldZoneName ='" + fieldZoneName +"'" ;  
			 query  	= session.createSQLQuery(sql); 
			 
			 query.addScalar("fieldZoneId"	, new IntegerType());   
		
			list            =  query.list(); 
			System.out.println("list.get(0): "+list.get(0));
			if(list !=null && list.size() > 0){
				returnId = list.get(0); 
			}
			
			System.out.print("returnId:"+returnId); 
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			session.getTransaction().rollback(); 
			throw new EnjoyException("เกิดข้อผิดพลาดในการค้นหาข้อมูล"); 
		}finally{    
			query 								= null;
			sql                                 = null;
			list                                = null; 
			logger.info("[insertSeatingDetail][End]");
		}
		
		return returnId;
		 
	}
	
	public void insertNewSeatingDetail(Session 	session ,FieldZoneDetailBean fielZoneDetailBean) throws EnjoyException{
		logger.info("[insertNewSeatingDetail][Begin]");
		  
		String							hql				  		= null; 
		Query   						query 			  		= null; 
		int                             result            		= 0;
		Fieldzonedetail                 fieldzoneDB             = null;  
		Fieldzonemaster                 fieldzonemasterDB       = null; 
		FieldzonedetailPK               fieldzonedetailPK       = null;
		try{   
			fieldzonedetailPK = new FieldzonedetailPK();
			fieldzonedetailPK.setSeq(fielZoneDetailBean.getSeq());
			fieldzonedetailPK.setFieldZoneId(fielZoneDetailBean.getFieldZoneId());
			fieldzoneDB 	  = new Fieldzonedetail();  
			fieldzoneDB.setId(fieldzonedetailPK);  
			fieldzoneDB.setBookingTypeId(fielZoneDetailBean.getBookingTypeId());
			fieldzoneDB.setBookingPrices(fielZoneDetailBean.getBookingPrices()); 
			session.save(fieldzoneDB); 
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());  
			throw new EnjoyException("เกิดข้อผิดพลาดในการ บันทึกข้อมูล"); 
		}finally{ 
			fieldzonedetailPK                   = null;
			query 								= null;
			hql                                 = null;
			result                              = 0;
			fieldzoneDB       			        = null;
			logger.info("[insertNewSeatingDetail][End]");
		}
		 
	}
	
	public int findBookTypeId(String bookTypeName) throws EnjoyException{
		logger.info("[findBookTypeId][Begin]");
		  
		String							sql				  		= null; 
		SQLQuery 						query 			  		= null;  
		SessionFactory 		   			sessionFactory	  		= null;
		Session 			   			session  		  		= null;
		int                             returnId                = 0; 
		List<Integer>                   list                    = null;
		try{ 
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			session.getTransaction().begin();
			 
			 sql        = "SELECT bookingTypeId from Bookingtype where bookingTypeName ='" + bookTypeName +"'" ;  
			 query  	= session.createSQLQuery(sql); 
			 
			 query.addScalar("bookingTypeId"	, new IntegerType());   
		
			list            =  query.list(); 
			System.out.println("findBookTypeId list.get(0): "+list.get(0));
			if(list !=null && list.size() > 0){
				returnId = list.get(0); 
			}
			
			System.out.print("returnId:"+returnId); 
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			session.getTransaction().rollback(); 
			throw new EnjoyException("เกิดข้อผิดพลาดในการดึงข้อมูล"); 
		}finally{    
			query 								= null;
			sql                                 = null;
			list                                = null; 
			logger.info("[findBookTypeId][End]");
		}
		
		return returnId;
		 
	}
	
	public void insertSeatingDetail(Session session,FieldZoneDetailBean seatingDetailBean) throws EnjoyException{
		logger.info("[insertSeatingDetail][Begin]");
		  
		String							sql				  		= null; 
		SQLQuery 						query 			  		= null; 
		int                             result            		= 0;
		Fieldzonedetail                 fieldzoneDB             = null;   
		FieldzonedetailPK               fieldzonedetailPK       = null;
		try{  
			
			fieldzonedetailPK = new FieldzonedetailPK();
			fieldzonedetailPK.setSeq(seatingDetailBean.getSeq());
			fieldzonedetailPK.setFieldZoneId(seatingDetailBean.getFieldZoneId()); 
			
			fieldzoneDB 	  = new Fieldzonedetail();   
			fieldzoneDB.setId(fieldzonedetailPK);
			fieldzoneDB.setBookingTypeId(seatingDetailBean.getBookingTypeId());
			fieldzoneDB.setBookingPrices(seatingDetailBean.getBookingPrices());
		 
			session.persist(fieldzoneDB);  
			  
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			session.getTransaction().rollback(); 
			throw new EnjoyException("เกิดข้อผิดพลาดในการ บันทึกข้อมูล"); 
		}finally{   
			query 								= null;
			sql                                 = null;
			result                              = 0;
			fieldzoneDB       			        = null;
			fieldzonedetailPK                   = null;
			logger.info("[insertSeatingDetail][End]");
		}
		 
	}
	
	public void deleteSeatingDetail(Session session,int fieldZoneId,int seq) throws EnjoyException{
		logger.info("[deleteSeatingDetail][Begin]");
		  
		String							hql									= null; 
		Query 						    query 								= null;   
		try{ 
			 System.out.print("[deleteSeatingDetail]input to delete :"+fieldZoneId);
			 hql        = "DELETE from Fieldzonedetail where fieldZoneId = :fieldZoneId" + 
			              " and seq = :seq";  
			 query      = session.createQuery(hql);
			 query.setInteger("fieldZoneId", fieldZoneId);  
			 query.setInteger("seq", seq);  
			 System.out.println(query.executeUpdate()); 
		 
		}catch(Exception e){ 
			e.printStackTrace();
			logger.info(e.getMessage()); 
			throw new EnjoyException("เกิดข้อผิดพลาดในการ ลบข้อมูล"); 
		}finally{  
			query 								= null;
			hql                                 = null; 
			logger.info("[deleteSeatingDetail][End]");
		} 
	}
	
	public void updateSeatingMaster(Session session,SeatingDetailBean seatingDetailBean) throws EnjoyException{
		logger.info("[updateSeatingDetail][Begin]"+seatingDetailBean.toString());
		  
		String							hql									= null; 
		Query 						    query 								= null;  
		int                             result                              = 0;
		Fieldzonemaster                 fieldzonemasterDB                   = null; 
		try{ 
			hql        					= "FROM Fieldzonemaster WHERE fieldZoneId  = :fieldZoneId ";  
			query      					= session.createQuery(hql).setParameter("fieldZoneId", seatingDetailBean.getFieldZoneId());  
			fieldzonemasterDB    		= (Fieldzonemaster)query.uniqueResult();
			System.out.println("matchDB.getFieldZoneId() :: "+seatingDetailBean.getFieldZoneId());
		 
			fieldzonemasterDB.setFieldZoneId(seatingDetailBean.getFieldZoneId());
			fieldzonemasterDB.setFieldZoneName(seatingDetailBean.getFieldZoneName());
			fieldzonemasterDB.setRows(seatingDetailBean.getRows());
			fieldzonemasterDB.setSeating(seatingDetailBean.getSeating());
			fieldzonemasterDB.setTotalSeating(seatingDetailBean.getTotalSeating());
			fieldzonemasterDB.setTypeRowName(seatingDetailBean.getTypeRowName());
			fieldzonemasterDB.setRowName(seatingDetailBean.getNameRow());
			fieldzonemasterDB.setFieldZoneNameTicket(seatingDetailBean.getFieldZoneNameTicket());
			fieldzonemasterDB.setStartSeatingNo(seatingDetailBean.getStartSeatingNo());
			session.merge(fieldzonemasterDB);  
			session.flush(); 
			session.clear(); 
		}catch(Exception e){ 
			e.printStackTrace();
			logger.info(e.getMessage()); 
			throw new EnjoyException("เกิดข้อผิดพลาดในการ แก้ไขข้อมูล"); 
		}finally{ 
			query 								= null;
			hql                                 = null; 
			fieldzonemasterDB                   = null;
			logger.info("[updateSeatingDetail][End]");
		}
		 
	}
	
	public void updateFieldZoneDetail(Session session,FieldZoneDetailBean bean) throws EnjoyException{
		logger.info("[updateFieldZoneDetail][Begin]"+bean.toString());
		  
		String							hql									= null; 
		Query 						    query 								= null;  
		int                             result                              = 0;
		Fieldzonedetail                 fieldzoneDB                         = null; 
		try{ 
			hql        					= "FROM Fieldzonedetail WHERE fieldZoneId  = :fieldZoneId and seq = :seq";  
			query      					= session.createQuery(hql).setParameter("fieldZoneId", bean.getFieldZoneId()).setParameter("seq", bean.getSeq()); ;  
			fieldzoneDB    		        = (Fieldzonedetail)query.uniqueResult();
			System.out.println("matchDB.getFieldZoneId() :: "+bean.getFieldZoneId());
			System.out.println("matchDB.getSeq() :: "+bean.getSeq());
			 
			fieldzoneDB.setBookingTypeId(bean.getBookingTypeId());
			fieldzoneDB.setBookingPrices(bean.getBookingPrices()); 
			session.saveOrUpdate(fieldzoneDB);  
			session.flush(); 
			session.clear(); 
		}catch(Exception e){ 
			e.printStackTrace();
			logger.info(e.getMessage()); 
			throw new EnjoyException("เกิดข้อผิดพลาดในการ แก้ไขข้อมูล"); 
		}finally{ 
			query 								= null;
			hql                                 = null; 
			fieldzoneDB                         = null;
			logger.info("[updateSeatingDetail][End]");
		}
		 
	}
	
	public void  insertFieldZoneDetail(Session session ,FieldZoneDetailBean bean)throws EnjoyException{
		System.out.println("fieldZoneDetailBean :"+bean.toString());	
		String							sql				  		= null; 
		SQLQuery 						query 			  		= null; 
		int                             result            		= 0;
		Fieldzonedetail                 fieldzoneDB             = null;
		FieldzonedetailPK               fieldzonedetailPK       = null;
		try{  
			fieldzonedetailPK = new FieldzonedetailPK();
			fieldzonedetailPK.setSeq(bean.getSeq());
			fieldzonedetailPK.setFieldZoneId(bean.getFieldZoneId());
			fieldzoneDB 	  = new Fieldzonedetail();  
			fieldzoneDB.setId(fieldzonedetailPK); 
			fieldzoneDB.setBookingTypeId(bean.getBookingTypeId());
			fieldzoneDB.setBookingPrices(bean.getBookingPrices());
			session.save(fieldzoneDB);  
			session.flush(); 
			session.clear(); 
			 
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage()); 
			throw new EnjoyException("เกิดข้อผิดพลาดในการ บันทึกข้อมูล"); 
		}finally{   
			query 								= null;
			sql                                 = null;
			result                              = 0;
			fieldzoneDB       			        = null;
			fieldzonedetailPK                   = null;
			logger.info("[insertFieldZoneDetail][End]");
		}
		
	}
	
    public void saveSeatingDetail(SeatingDetailBean seatingDetailBean,List<FieldZoneDetailBean> fieldZoneDetailBeans,String getDelList )throws EnjoyException{
    	SessionFactory 		   sessionFactory		    = null;
		Session 			   session				    = null;
		Transaction            tx                       = null;
		try{ 
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			tx                  = session.beginTransaction();
			
			if(seatingDetailBean != null){
				this.updateSeatingMaster(session,seatingDetailBean);
			}
			
			if(!"none".equals(getDelList)){   
				List<String> deleteList = EnjoyUtils.getListFromArr(getDelList);
				System.out.print("deleteList : size ::"+deleteList.size());
				int fieldZoneSeqDel ;
				if(deleteList != null){
					if(deleteList.size()>0){
						for(String del :deleteList){
							System.out.print("del"+deleteList.size());
							if(del != ""){ 
								fieldZoneSeqDel = Integer.valueOf(del); 
								System.out.print("delete : seq is ::" +fieldZoneSeqDel + " and fieldZoneId = " + fieldZoneDetailBeans.get(0).getFieldZoneId()); 
								this.deleteSeatingDetail(session,fieldZoneDetailBeans.get(0).getFieldZoneId(),fieldZoneSeqDel); 
							}
						}
						
					} 
				}
			}
			
			for(FieldZoneDetailBean fieldZoneDetailBean:fieldZoneDetailBeans){
			
				if(fieldZoneDetailBean.getStatusPage().equals("N") ){ 
					System.out.println("fieldZoneDetailBean :"+fieldZoneDetailBean.getStatusPage());	
					this.insertFieldZoneDetail(session,fieldZoneDetailBean);    
				}else if(fieldZoneDetailBean.getStatusPage().equals("U")){
					System.out.println("fieldZoneDetailBean :"+fieldZoneDetailBean.getStatusPage());	
					this.updateFieldZoneDetail(session,fieldZoneDetailBean);  
				}
			}
			
			tx.commit();
			session.close();
		}catch(Exception e){
			session.getTransaction().rollback();
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{    
			session                             = null;
			sessionFactory                      = null;
			tx                                  = null;
			logger.info("[saveSeatingDetail][End]");
		}
    }
    
    public void saveNewSeatingMaster(SeatingDetailBean seatingDetailBean )throws EnjoyException{
    	SessionFactory 		   sessionFactory		    = null;
		Session 			   session				    = null;
		Transaction            tx                       = null;
		try{ 
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			tx                  = session.beginTransaction();
			
			if(seatingDetailBean != null){ 
				this.insertSeatingMaster(session,seatingDetailBean);
			} 
			
			tx.commit();
			session.close();
		}catch(Exception e){
			session.getTransaction().rollback();
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{    
			session                             = null;
			sessionFactory                      = null;
			tx                                  = null;
			logger.info("[saveSeatingDetail][End]");
		}
    }
    
    public void saveNewSeatingDetail(List<FieldZoneDetailBean> fieldZoneDetailBeans )throws EnjoyException{
    	SessionFactory 		   sessionFactory		    = null;
		Session 			   session				    = null;
		Transaction            tx                       = null;
		try{ 
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			tx                  = session.beginTransaction(); 
			 
			for(FieldZoneDetailBean fieldZoneDetailBean:fieldZoneDetailBeans){  
				this.insertFieldZoneDetail(session,fieldZoneDetailBean);     
			}
			
			tx.commit();
			session.close();
		}catch(Exception e){
			session.getTransaction().rollback();
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{    
			session                             = null;
			sessionFactory                      = null;
			tx                                  = null;
			logger.info("[saveSeatingDetail][End]");
		}
    }

    public int selectMaxSeq(int fieldZoneId) throws EnjoyException{
		logger.info("[selectMaxSeq][Begin]"); 
		String							sql									= null;   
		SQLQuery 						sqlQuery 							= null;   
		List<Integer>                   list                                = null;
		Integer                         seqNo 							    = null;
		Session 						session								= null;
		SessionFactory 		   			sessionFactory		    			= null;
		try{ 
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			session.beginTransaction();
			sql             = "SELECT MAX(seq) as seqNo from fieldzonedetail WHERE fieldZoneId = "+ fieldZoneId;
			sqlQuery		= session.createSQLQuery(sql); 
			sqlQuery.addScalar("seqNo"	, new IntegerType());
			list            =  sqlQuery.list(); 
			System.out.println("list.get(0): "+list.get(0));
			if(list !=null && list.size() > 0){
				seqNo = list.get(0);
				logger.info("selectMaxSeq order of seqNo ::  "+ seqNo); 
			}
			session.clear();
			session.close();
		}catch(Exception e){
			session.getTransaction().rollback();
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{  
			sqlQuery 							= null;
			sql                                 = null;
			list                                = null;
			session                             = null;
			sessionFactory                      = null;
			logger.info("[selectMaxSeq][End]");
		}
		
		return seqNo;
	}
}
