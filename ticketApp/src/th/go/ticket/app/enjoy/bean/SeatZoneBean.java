package th.go.ticket.app.enjoy.bean;

public class SeatZoneBean {
	
	private String matchId;
	private String season;
	private String awayTeamNameTH;
	private String fieldZoneId;
	private String fieldZoneName;
	
	public SeatZoneBean(){
		this.matchId 			= "";
		this.season 			= "";
		this.awayTeamNameTH 	= "";
		this.fieldZoneId 		= "";
		this.fieldZoneName 		= "";
	}

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
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

	

}
