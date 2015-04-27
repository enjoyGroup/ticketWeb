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

	private String fieldZoneNameTicket;

	private String rowName;

	private int rows;

	private int seating;

	private int startSeatingNo;

	private int totalSeating;

	private int typeRowName;

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

	public String getFieldZoneNameTicket() {
		return this.fieldZoneNameTicket;
	}

	public void setFieldZoneNameTicket(String fieldZoneNameTicket) {
		this.fieldZoneNameTicket = fieldZoneNameTicket;
	}

	public String getRowName() {
		return this.rowName;
	}

	public void setRowName(String rowName) {
		this.rowName = rowName;
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

	public int getStartSeatingNo() {
		return this.startSeatingNo;
	}

	public void setStartSeatingNo(int startSeatingNo) {
		this.startSeatingNo = startSeatingNo;
	}

	public int getTotalSeating() {
		return this.totalSeating;
	}

	public void setTotalSeating(int totalSeating) {
		this.totalSeating = totalSeating;
	}

	public int getTypeRowName() {
		return this.typeRowName;
	}

	public void setTypeRowName(int typeRowName) {
		this.typeRowName = typeRowName;
	}

}