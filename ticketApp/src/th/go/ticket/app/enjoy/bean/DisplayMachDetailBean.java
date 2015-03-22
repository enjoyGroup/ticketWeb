package th.go.ticket.app.enjoy.bean;

public class DisplayMachDetailBean {
	
	private String season;
	private String awayTeamNameTH;
	private String matchId;
	private String bookingTypeName;
	private String fieldZoneName;
	private String bookingTypeId;
	private String totalSeating;
	private String tatalMoney;
	
	public DisplayMachDetailBean(){
		this.season 			= "";
		this.awayTeamNameTH 	= "";
		this.matchId			= "";
		this.bookingTypeName	= "";
		this.fieldZoneName		= "";
		this.bookingTypeId		= "";
		this.totalSeating		= "";
		this.tatalMoney			= "";
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getAwayTeamNameTH() {
		return awayTeamNameTH;
	}

	public void setAwayTeamNameTH(String awayTeamNameTH) {
		this.awayTeamNameTH = awayTeamNameTH;
	}

	public String getMatchId() {
		return matchId;
	}

	public String getFieldZoneName() {
		return fieldZoneName;
	}

	public void setFieldZoneName(String fieldZoneName) {
		this.fieldZoneName = fieldZoneName;
	}

	public String getBookingTypeId() {
		return bookingTypeId;
	}

	public void setBookingTypeId(String bookingTypeId) {
		this.bookingTypeId = bookingTypeId;
	}

	public String getTotalSeating() {
		return totalSeating;
	}

	public void setTotalSeating(String totalSeating) {
		this.totalSeating = totalSeating;
	}

	public String getTatalMoney() {
		return tatalMoney;
	}

	public void setTatalMoney(String tatalMoney) {
		this.tatalMoney = tatalMoney;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public String getBookingTypeName() {
		return bookingTypeName;
	}

	public void setBookingTypeName(String bookingTypeName) {
		this.bookingTypeName = bookingTypeName;
	}
	
}
