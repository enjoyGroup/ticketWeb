package th.go.ticket.app.enjoy.bean;

public class SummaryRevenueOfYearBean {
	
	private String season;
	private String bookingPrice;
	
	public SummaryRevenueOfYearBean(){
		
		this.season 		= "";
		this.bookingPrice 	= "";
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getBookingPrice() {
		return bookingPrice;
	}

	public void setBookingPrice(String bookingPrice) {
		this.bookingPrice = bookingPrice;
	}

}
