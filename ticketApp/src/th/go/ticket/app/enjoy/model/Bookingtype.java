package th.go.ticket.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the bookingtype database table.
 * 
 */
@Entity
@NamedQuery(name="Bookingtype.findAll", query="SELECT b FROM Bookingtype b")
public class Bookingtype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int bookingTypeId;

	private String bookingTypeName;

	public Bookingtype() {
	}

	public int getBookingTypeId() {
		return this.bookingTypeId;
	}

	public void setBookingTypeId(int bookingTypeId) {
		this.bookingTypeId = bookingTypeId;
	}

	public String getBookingTypeName() {
		return this.bookingTypeName;
	}

	public void setBookingTypeName(String bookingTypeName) {
		this.bookingTypeName = bookingTypeName;
	}

}