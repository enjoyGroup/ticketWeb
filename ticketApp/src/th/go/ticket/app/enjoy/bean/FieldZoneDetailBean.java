package th.go.ticket.app.enjoy.bean;

import java.math.BigDecimal;

import javax.persistence.Id;

 
 
public class FieldZoneDetailBean {
	  
	private int fieldZoneId; 
	private int seq; 
	private String bookingPrices; 
	private int bookingTypeId;
	private String bookingTypeName;
	private boolean status;
	private String statusPage;

	public FieldZoneDetailBean() {  
		this.seq = 1;   
	}

	public int getFieldZoneId() {
		return fieldZoneId;
	}

	public void setFieldZoneId(int fieldZoneId) {
		this.fieldZoneId = fieldZoneId;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}
 

	public String getBookingPrices() {
		return bookingPrices;
	}

	public void setBookingPrices(String bookingPrices) {
		this.bookingPrices = bookingPrices;
	}

	public int getBookingTypeId() {
		return bookingTypeId;
	}

	public void setBookingTypeId(int bookingTypeId) {
		this.bookingTypeId = bookingTypeId;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	 
	public String getBookingTypeName() {
		return bookingTypeName;
	}

	public void setBookingTypeName(String bookingTypeName) {
		this.bookingTypeName = bookingTypeName;
	}

	
	public String getStatusPage() {
		return statusPage;
	}

	public void setStatusPage(String statusPage) {
		this.statusPage = statusPage;
	}

	@Override
	public String toString() {
		return "FieldZoneDetailBean [fieldZoneId=" + fieldZoneId + ", seq="
				+ seq + ", bookingPrices=" + bookingPrices + ", bookingTypeId="
				+ bookingTypeId + ", bookingTypeName=" + bookingTypeName
				+ ", status=" + status + ", statusPage=" + statusPage + "]";
	}

  

}