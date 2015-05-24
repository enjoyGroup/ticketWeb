package th.go.ticket.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the genseqticketid database table.
 * 
 */
@Embeddable
public class GenseqticketidPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int season;

	private int matchId;

	public GenseqticketidPK() {
	}
	public int getSeason() {
		return this.season;
	}
	public void setSeason(int season) {
		this.season = season;
	}
	public int getMatchId() {
		return this.matchId;
	}
	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GenseqticketidPK)) {
			return false;
		}
		GenseqticketidPK castOther = (GenseqticketidPK)other;
		return 
			(this.season == castOther.season)
			&& (this.matchId == castOther.matchId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.season;
		hash = hash * prime + this.matchId;
		
		return hash;
	}
}