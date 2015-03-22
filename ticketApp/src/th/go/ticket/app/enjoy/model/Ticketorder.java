package th.go.ticket.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ticketorder database table.
 * 
 */
@Entity
@NamedQuery(name="Ticketorder.findAll", query="SELECT t FROM Ticketorder t")
public class Ticketorder implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TicketorderPK id;

	private int bookingTypeId;

	private int fieldZoneId;

	private int matchId;

	private String ticketStatus;

	private int userUniqueId;

	public Ticketorder() {
	}

	public TicketorderPK getId() {
		return this.id;
	}

	public void setId(TicketorderPK id) {
		this.id = id;
	}

	public int getBookingTypeId() {
		return this.bookingTypeId;
	}

	public void setBookingTypeId(int bookingTypeId) {
		this.bookingTypeId = bookingTypeId;
	}

	public int getFieldZoneId() {
		return this.fieldZoneId;
	}

	public void setFieldZoneId(int fieldZoneId) {
		this.fieldZoneId = fieldZoneId;
	}

	public int getMatchId() {
		return this.matchId;
	}

	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}

	public String getTicketStatus() {
		return this.ticketStatus;
	}

	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public int getUserUniqueId() {
		return this.userUniqueId;
	}

	public void setUserUniqueId(int userUniqueId) {
		this.userUniqueId = userUniqueId;
	}

}