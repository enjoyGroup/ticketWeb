package th.go.ticket.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the genseqticketid database table.
 * 
 */
@Entity
@NamedQuery(name="Genseqticketid.findAll", query="SELECT g FROM Genseqticketid g")
public class Genseqticketid implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GenseqticketidPK id;

	private int ticketSeq;

	public Genseqticketid() {
	}

	public GenseqticketidPK getId() {
		return this.id;
	}

	public void setId(GenseqticketidPK id) {
		this.id = id;
	}

	public int getTicketSeq() {
		return this.ticketSeq;
	}

	public void setTicketSeq(int ticketSeq) {
		this.ticketSeq = ticketSeq;
	}

}