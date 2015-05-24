package th.go.ticket.app.enjoy.bean;

public class CancelSeatBean {
	private String seatingNo;
	private String bookingPrices;
	private String bookingTypeName;
	private String userUniqueId;
	private String matchId;
	private String awayTeamNameTH;
	private String awayTeamNameEN;
	private String matchDate;
	private String matchTime;
	private String ticketId;
	private String fieldZoneId;
	private String fieldZoneName;
	private String seatRow;
	private String season;
	private String seatingNoBegin;
	private String seatingNoEnd;
	
	public CancelSeatBean(){
		this.seatingNo 				= "";
		this.bookingPrices 			= "";
		this.bookingTypeName 		= "";
		this.userUniqueId 			= "";
		this.matchId 				= "";
		this.fieldZoneId 			= "";
		this.awayTeamNameTH 		= "";
		this.awayTeamNameEN 		= "";
		this.matchDate 				= "";
		this.matchTime 				= "";
		this.ticketId				= "";
		this.fieldZoneName			= "";
		this.seatRow				= "";
		this.season					= "";
		this.seatingNoBegin			= "";
		this.seatingNoEnd			= "";
	}

	public String getSeatingNo() {
		return seatingNo;
	}

	public void setSeatingNo(String seatingNo) {
		this.seatingNo = seatingNo;
	}

	public String getBookingPrices() {
		return bookingPrices;
	}

	public void setBookingPrices(String bookingPrices) {
		this.bookingPrices = bookingPrices;
	}

	public String getBookingTypeName() {
		return bookingTypeName;
	}

	public void setBookingTypeName(String bookingTypeName) {
		this.bookingTypeName = bookingTypeName;
	}

	public String getUserUniqueId() {
		return userUniqueId;
	}

	public void setUserUniqueId(String userUniqueId) {
		this.userUniqueId = userUniqueId;
	}

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public String getFieldZoneId() {
		return fieldZoneId;
	}

	public void setFieldZoneId(String fieldZoneId) {
		this.fieldZoneId = fieldZoneId;
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

	public String getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(String matchDate) {
		this.matchDate = matchDate;
	}

	public String getMatchTime() {
		return matchTime;
	}

	public void setMatchTime(String matchTime) {
		this.matchTime = matchTime;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getFieldZoneName() {
		return fieldZoneName;
	}

	public void setFieldZoneName(String fieldZoneName) {
		this.fieldZoneName = fieldZoneName;
	}

	public String getSeatRow() {
		return seatRow;
	}

	public void setSeatRow(String seatRow) {
		this.seatRow = seatRow;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getSeatingNoBegin() {
		return seatingNoBegin;
	}

	public void setSeatingNoBegin(String seatingNoBegin) {
		this.seatingNoBegin = seatingNoBegin;
	}

	public String getSeatingNoEnd() {
		return seatingNoEnd;
	}

	public void setSeatingNoEnd(String seatingNoEnd) {
		this.seatingNoEnd = seatingNoEnd;
	}
	
}
