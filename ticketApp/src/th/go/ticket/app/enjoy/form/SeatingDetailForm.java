package th.go.ticket.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;
 
 


import th.go.ticket.app.enjoy.bean.BookingTypeBean;
import th.go.ticket.app.enjoy.bean.FieldzonemasterBean;
import th.go.ticket.app.enjoy.bean.SeatingDetailBean;

public class SeatingDetailForm {
	
	List<SeatingDetailBean> 		seatingDetailBeans; 
	List<FieldzonemasterBean>       fieldzonemasterBeans;
	String                          fieldzonemasterName;
	SeatingDetailBean               seatingDetailBean;
	BookingTypeBean                 bookingTypeBean;
	String                          userLevel; 
	
	public SeatingDetailForm(){
		this.seatingDetailBeans     = new ArrayList<SeatingDetailBean>(); 
		this.fieldzonemasterBeans   = new ArrayList<FieldzonemasterBean>();
        this.seatingDetailBean      = new SeatingDetailBean(); 
        this.bookingTypeBean        = new BookingTypeBean();
        this.userLevel              = "";
        
	}

	public List<SeatingDetailBean> getSeatingDetailBeans() {
		return seatingDetailBeans;
	}

	public void setSeatingDetailBeans(List<SeatingDetailBean> seatingDetailBeans) {
		this.seatingDetailBeans = seatingDetailBeans;
	}
	   
	public List<FieldzonemasterBean> getFieldzonemasterBeans() {
		return fieldzonemasterBeans;
	}

	public void setFieldzonemasterBeans(
			List<FieldzonemasterBean> fieldzonemasterBeans) {
		this.fieldzonemasterBeans = fieldzonemasterBeans;
	}

	public String getFieldzonemasterName() {
		return fieldzonemasterName;
	}

	public void setFieldzonemasterName(String fieldzonemasterName) {
		this.fieldzonemasterName = fieldzonemasterName;
	}

	
	public SeatingDetailBean getSeatingDetailBean() {
		return seatingDetailBean;
	}

	public void setSeatingDetailBean(SeatingDetailBean seatingDetailBean) {
		this.seatingDetailBean = seatingDetailBean;
	}
	  
	public BookingTypeBean getBookingTypeBean() {
		return bookingTypeBean;
	}

	public void setBookingTypeBean(BookingTypeBean bookingTypeBean) {
		this.bookingTypeBean = bookingTypeBean;
	}
	
	

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	@Override
	public String toString() {
		return "SeatingDetailForm [seatingDetailBeans=" + seatingDetailBeans
				+ ", fieldzonemasterBeans=" + fieldzonemasterBeans
				+ ", fieldzonemasterName=" + fieldzonemasterName
				+ ", seatingDetailBean=" + seatingDetailBean
				+ ", bookingTypeBean=" + bookingTypeBean + "]";
	}

	 
	  
}
