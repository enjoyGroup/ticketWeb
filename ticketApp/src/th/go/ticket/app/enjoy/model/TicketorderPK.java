package th.go.ticket.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ticketorder database table.
 * 
 */
@Embeddable
public class TicketorderPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int ticketId;

	private String seatingNo;

	public TicketorderPK() {
	}
	public int getTicketId() {
		return this.ticketId;
	}
	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}
	public String getSeatingNo() {
		return this.seatingNo;
	}
	public void setSeatingNo(String seatingNo) {
		this.seatingNo = seatingNo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TicketorderPK)) {
			return false;
		}
		TicketorderPK castOther = (TicketorderPK)other;
		return 
			(this.ticketId == castOther.ticketId)
			&& this.seatingNo.equals(castOther.seatingNo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.ticketId;
		hash = hash * prime + this.seatingNo.hashCode();
		
		return hash;
	}
}