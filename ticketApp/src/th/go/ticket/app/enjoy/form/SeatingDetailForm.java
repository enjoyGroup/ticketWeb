package th.go.ticket.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;
 
 



import th.go.ticket.app.enjoy.bean.BookingTypeBean;
import th.go.ticket.app.enjoy.bean.FieldZoneDetailBean;
import th.go.ticket.app.enjoy.bean.FieldzonemasterBean;
import th.go.ticket.app.enjoy.bean.SeatingDetailBean;

public class SeatingDetailForm {  
	List<FieldzonemasterBean>         zoneMasterList;
	FieldzonemasterBean               fieldzonemasterBean;
	List<FieldZoneDetailBean>         fieldZoneDetailBeans;
	String                            fieldzonemasterName;
	int 							  fieldZoneId;
	SeatingDetailBean                 seatingDetailBean;
	BookingTypeBean                   bookingTypeBean;
	String                            userLevel; 

	
	public SeatingDetailForm(){ 
		this.zoneMasterList         = new ArrayList<FieldzonemasterBean>();
        this.seatingDetailBean      = new SeatingDetailBean(); 
        this.fieldZoneDetailBeans   = new ArrayList<FieldZoneDetailBean>();
        this.bookingTypeBean        = new BookingTypeBean();
        this.fieldzonemasterBean    = new FieldzonemasterBean();
        this.userLevel              = "";
        this.fieldZoneId            = 0;
        
	}


	public List<FieldzonemasterBean> getZoneMasterList() {
		return zoneMasterList;
	}


	public void setZoneMasterList(List<FieldzonemasterBean> zoneMasterList) {
		this.zoneMasterList = zoneMasterList;
	}


	public FieldzonemasterBean getFieldzonemasterBean() {
		return fieldzonemasterBean;
	}


	public void setFieldzonemasterBean(FieldzonemasterBean fieldzonemasterBean) {
		this.fieldzonemasterBean = fieldzonemasterBean;
	}




	public List<FieldZoneDetailBean> getFieldZoneDetailBeans() {
		return fieldZoneDetailBeans;
	}


	public void setFieldZoneDetailBeans(List<FieldZoneDetailBean> fieldZoneDetailBeans) {
		this.fieldZoneDetailBeans = fieldZoneDetailBeans;
	}


	public String getFieldzonemasterName() {
		return fieldzonemasterName;
	}


	public void setFieldzonemasterName(String fieldzonemasterName) {
		this.fieldzonemasterName = fieldzonemasterName;
	}


	public int getFieldZoneId() {
		return fieldZoneId;
	}


	public void setFieldZoneId(int fieldZoneId) {
		this.fieldZoneId = fieldZoneId;
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
		return "SeatingDetailForm [zoneMasterList=" + zoneMasterList
				+ ", fieldzonemasterBean=" + fieldzonemasterBean
				+ ", fieldZoneDetailBeans=" + fieldZoneDetailBeans
				+ ", fieldzonemasterName=" + fieldzonemasterName
				+ ", fieldZoneId=" + fieldZoneId + ", seatingDetailBean="
				+ seatingDetailBean + ", bookingTypeBean=" + bookingTypeBean
				+ ", userLevel=" + userLevel + "]";
	}
 
	 
	  
}
