package th.go.ticket.app.enjoy.bean;

/**
 * @author acer
 *
 */
public class EventMatchBean {
	private String matchId;
	private String season;
	private String awayTeamNameEN; 
	private String awayTeamNameTH; 
	private String matchDate; 
	private String matchTime;
	private String active;
	private String matchDateId;
	private String status;
	
	public EventMatchBean(){ 
		this.matchId         = "";
		this.season          = "";
		this.awayTeamNameEN  = "";
		this.awayTeamNameTH  = "";
		this.matchDate       = "";
		this.matchTime       = "";
		this.active          = "A";
		this.matchDateId     = "matchDateId";
		this.status          = "";
		
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

	public String getAwayTeamNameEN() {
		return awayTeamNameEN;
	}

	public void setAwayTeamNameEN(String awayTeamNameEN) {
		this.awayTeamNameEN = awayTeamNameEN;
	}

	public String getAwayTeamNameTH() {
		return awayTeamNameTH;
	}

	public void setAwayTeamNameTH(String awayTeamNameTH) {
		this.awayTeamNameTH = awayTeamNameTH;
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

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getMatchDateId() {
		return matchDateId;
	}

	public void setMatchDateId(String matchDateId) {
		this.matchDateId = matchDateId;
	}
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "EventMatchBean [matchId=" + matchId + ", season=" + season
				+ ", awayTeamNameEN=" + awayTeamNameEN + ", awayTeamNameTH="
				+ awayTeamNameTH + ", matchDate=" + matchDate + ", matchTime="
				+ matchTime + ", active=" + active + ", matchDateId="
				+ matchDateId + ", status=" + status + "]";
	}

	 
	
	

}
