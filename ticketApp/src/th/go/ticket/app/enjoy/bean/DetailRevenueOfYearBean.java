package th.go.ticket.app.enjoy.bean;

public class DetailRevenueOfYearBean {
	
	private String matchId;
	private String awayTeamNameTH;
	private String awayTeamNameEN;
	private String totalSeating;
	private String bookingPrices;
	
	public DetailRevenueOfYearBean(){
		this.matchId 			= "";
		this.awayTeamNameTH 	= "";
		this.awayTeamNameEN 	= "";
		this.totalSeating 		= "";
		this.bookingPrices 		= "";
	}

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public String getAwayTeamNameTH() {
		return awayTeamNameTH;
	}

	public void setAwayTeamNameTH(String awayTeamNameTH) {
		this.awayTeamNameTH = awayTeamNameTH;
	}

	public String getAwayTeamNameEN() {
		return awayTeamNameEN;
	}

	public void setAwayTeamNameEN(String awayTeamNameEN) {
		this.awayTeamNameEN = awayTeamNameEN;
	}

	public String getTotalSeating() {
		return totalSeating;
	}

	public void setTotalSeating(String totalSeating) {
		this.totalSeating = totalSeating;
	}

	public String getBookingPrices() {
		return bookingPrices;
	}

	public void setBookingPrices(String bookingPrices) {
		this.bookingPrices = bookingPrices;
	}
	
	

}
