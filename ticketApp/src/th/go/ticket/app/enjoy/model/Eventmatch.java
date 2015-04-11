package th.go.ticket.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eventmatch database table.
 * 
 */
@Entity
@NamedQuery(name="Eventmatch.findAll", query="SELECT e FROM Eventmatch e")
public class Eventmatch implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int matchId;

	private String awayTeamNameEN;

	private String awayTeamNameTH;

	private String matchDate;

	private String matchTime;

	private int season;
	
	private boolean activeFlag;

	public Eventmatch() {
	}

	public int getMatchId() {
		return this.matchId;
	}

	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}

	public String getAwayTeamNameEN() {
		return this.awayTeamNameEN;
	}

	public void setAwayTeamNameEN(String awayTeamNameEN) {
		this.awayTeamNameEN = awayTeamNameEN;
	}

	public String getAwayTeamNameTH() {
		return this.awayTeamNameTH;
	}

	public void setAwayTeamNameTH(String awayTeamNameTH) {
		this.awayTeamNameTH = awayTeamNameTH;
	}

	public String getMatchDate() {
		return this.matchDate;
	}

	public void setMatchDate(String matchDate) {
		this.matchDate = matchDate;
	}

	public String getMatchTime() {
		return this.matchTime;
	}

	public void setMatchTime(String matchTime) {
		this.matchTime = matchTime;
	}

	public int getSeason() {
		return this.season;
	}

	public void setSeason(int season) {
		this.season = season;
	}

	public boolean isActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	
}