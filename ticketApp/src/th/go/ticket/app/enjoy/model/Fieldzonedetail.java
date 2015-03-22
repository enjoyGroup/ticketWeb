package th.go.ticket.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fieldzonedetail database table.
 * 
 */
@Entity
@NamedQuery(name="Fieldzonedetail.findAll", query="SELECT f FROM Fieldzonedetail f")
public class Fieldzonedetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FieldzonedetailPK id;

	private double bookingPrices;

	private int bookingTypeId;

	public Fieldzonedetail() {
	}

	public FieldzonedetailPK getId() {
		return this.id;
	}

	public void setId(FieldzonedetailPK id) {
		this.id = id;
	}

	public double getBookingPrices() {
		return this.bookingPrices;
	}

	public void setBookingPrices(double bookingPrices) {
		this.bookingPrices = bookingPrices;
	}

	public int getBookingTypeId() {
		return this.bookingTypeId;
	}

	public void setBookingTypeId(int bookingTypeId) {
		this.bookingTypeId = bookingTypeId;
	}

}