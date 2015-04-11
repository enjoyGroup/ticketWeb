package th.go.ticket.app.enjoy.bean;

public class SeatReservationBean {
	
	private String fieldZoneId;
	private String fieldZoneName;
	private String rows;
	private String seating;
	private String totalSeating;
	private String bookingTypeId;
	private String bookingTypeName;
	private String bookingPrices;
	private String ticketId;
	private String seatingNo;
	
	public SeatReservationBean(){
		this.fieldZoneId 			= "";
		this.fieldZoneName 			= "";
		this.rows 					= "";
		this.seating 				= "";
		this.totalSeating 			= "";
		this.bookingTypeId 			= "";
		this.bookingTypeName 		= "";
		this.bookingPrices 			= "";
		this.ticketId 				= "";
		this.seatingNo 				= "";
	}

	public String getFieldZoneId() {
		return fieldZoneId;
	}

	public void setFieldZoneId(String fieldZoneId) {
		this.fieldZoneId = fieldZoneId;
	}

	public String getFieldZoneName() {
		return fieldZoneName;
	}

	public void setFieldZoneName(String fieldZoneName) {
		this.fieldZoneName = fieldZoneName;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public String getSeating() {
		return seating;
	}

	public void setSeating(String seating) {
		this.seating = seating;
	}

	public String getTotalSeating() {
		return totalSeating;
	}

	public void setTotalSeating(String totalSeating) {
		this.totalSeating = totalSeating;
	}

	public String getBookingTypeId() {
		return bookingTypeId;
	}

	public void setBookingTypeId(String bookingTypeId) {
		this.bookingTypeId = bookingTypeId;
	}

	public String getBookingTypeName() {
		return bookingTypeName;
	}

	public void setBookingTypeName(String bookingTypeName) {
		this.bookingTypeName = bookingTypeName;
	}

	public String getBookingPrices() {
		return bookingPrices;
	}

	public void setBookingPrices(String bookingPrices) {
		this.bookingPrices = bookingPrices;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getSeatingNo() {
		return seatingNo;
	}

	public void setSeatingNo(String seatingNo) {
		this.seatingNo = seatingNo;
	}

	
}
