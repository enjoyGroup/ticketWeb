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

	@EmbeddedId
	private EventmatchPK id;

	private String activeFlag;

	private String awayTeamNameEN;

	private String awayTeamNameTH;

	private String matchDate;

	private String matchTime;

	public Eventmatch() {
	}

	public EventmatchPK getId() {
		return this.id;
	}

	public void setId(EventmatchPK id) {
		this.id = id;
	}

	public String getActiveFlag() {
		return this.activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
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

}