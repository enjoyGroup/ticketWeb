package th.go.ticket.app.enjoy.bean;

/**
 * @author acer
 *
 */
public class BookingTypeBean {
	private int bookingTypeId; 
	private String bookingTypeName;
	
	public BookingTypeBean(){  
	}

	public int getBookingTypeId() {
		return bookingTypeId;
	}

	public void setBookingTypeId(int bookingTypeId) {
		this.bookingTypeId = bookingTypeId;
	}

	public String getBookingTypeName() {
		return bookingTypeName;
	}

	public void setBookingTypeName(String bookingTypeName) {
		this.bookingTypeName = bookingTypeName;
	}

	@Override
	public String toString() {
		return "BookingTypeBean [bookingTypeId=" + bookingTypeId
				+ ", bookingTypeName=" + bookingTypeName + "]";
	}

	 

}
