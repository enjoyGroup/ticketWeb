package th.go.ticket.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ticketstatus database table.
 * 
 */
@Entity
@NamedQuery(name="Ticketstatus.findAll", query="SELECT t FROM Ticketstatus t")
public class Ticketstatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String ticketStatusId;

	private String ticketStatusName;

	public Ticketstatus() {
	}

	public String getTicketStatusId() {
		return this.ticketStatusId;
	}

	public void setTicketStatusId(String ticketStatusId) {
		this.ticketStatusId = ticketStatusId;
	}

	public String getTicketStatusName() {
		return this.ticketStatusName;
	}

	public void setTicketStatusName(String ticketStatusName) {
		this.ticketStatusName = ticketStatusName;
	}

}