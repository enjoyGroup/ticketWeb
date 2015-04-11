package th.go.ticket.app.enjoy.bean;

/**
 * @author acer
 *
 */
public class SeatingDetailBean {
	private int fieldZoneId;

	private String fieldZoneName;

	private int rows;

	private int seating;

	private int totalSeating;
	  
	private int bookingTypeId;

	private String bookingTypeName;
	
	private double bookingPrices;
 
	public SeatingDetailBean(){ 
		 
	}

	public int getFieldZoneId() {
		return fieldZoneId;
	}

	public void setFieldZoneId(int fieldZoneId) {
		this.fieldZoneId = fieldZoneId;
	}

	public String getFieldZoneName() {
		return fieldZoneName;
	}

	public void setFieldZoneName(String fieldZoneName) {
		this.fieldZoneName = fieldZoneName;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getSeating() {
		return seating;
	}

	public void setSeating(int seating) {
		this.seating = seating;
	}

	public int getTotalSeating() {
		return totalSeating;
	}

	public void setTotalSeating(int totalSeating) {
		this.totalSeating = totalSeating;
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

	public double getBookingPrices() {
		return bookingPrices;
	}

	public void setBookingPrices(double bookingPrices) {
		this.bookingPrices = bookingPrices;
	}

	@Override
	public String toString() {
		return "SeatingDetailBean [fieldZoneId=" + fieldZoneId
				+ ", fieldZoneName=" + fieldZoneName + ", rows=" + rows
				+ ", seating=" + seating + ", totalSeating=" + totalSeating
				+ ", bookingTypeId=" + bookingTypeId + ", bookingTypeName="
				+ bookingTypeName + ", bookingPrices=" + bookingPrices + "]";
	}
	
	
 
}
