package th.go.ticket.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fieldzonemaster database table.
 * 
 */
@Entity
@NamedQuery(name="Fieldzonemaster.findAll", query="SELECT f FROM Fieldzonemaster f")
public class Fieldzonemaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int fieldZoneId;

	private String fieldZoneName;

	private int rows;

	private int seating;

	private int totalSeating;

	public Fieldzonemaster() {
	}

	public int getFieldZoneId() {
		return this.fieldZoneId;
	}

	public void setFieldZoneId(int fieldZoneId) {
		this.fieldZoneId = fieldZoneId;
	}

	public String getFieldZoneName() {
		return this.fieldZoneName;
	}

	public void setFieldZoneName(String fieldZoneName) {
		this.fieldZoneName = fieldZoneName;
	}

	public int getRows() {
		return this.rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getSeating() {
		return this.seating;
	}

	public void setSeating(int seating) {
		this.seating = seating;
	}

	public int getTotalSeating() {
		return this.totalSeating;
	}

	public void setTotalSeating(int totalSeating) {
		this.totalSeating = totalSeating;
	}

}