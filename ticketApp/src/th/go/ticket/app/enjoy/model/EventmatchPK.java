package th.go.ticket.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the eventmatch database table.
 * 
 */
@Embeddable
public class EventmatchPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int matchId;

	private int season;

	public EventmatchPK() {
	}
	public int getMatchId() {
		return this.matchId;
	}
	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}
	public int getSeason() {
		return this.season;
	}
	public void setSeason(int season) {
		this.season = season;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EventmatchPK)) {
			return false;
		}
		EventmatchPK castOther = (EventmatchPK)other;
		return 
			(this.matchId == castOther.matchId)
			&& (this.season == castOther.season);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.matchId;
		hash = hash * prime + this.season;
		
		return hash;
	}
}