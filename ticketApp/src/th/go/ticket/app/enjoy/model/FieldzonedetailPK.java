package th.go.ticket.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the fieldzonedetail database table.
 * 
 */
@Embeddable
public class FieldzonedetailPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int fieldZoneId;

	private int seq;

	public FieldzonedetailPK() {
	}
	public int getFieldZoneId() {
		return this.fieldZoneId;
	}
	public void setFieldZoneId(int fieldZoneId) {
		this.fieldZoneId = fieldZoneId;
	}
	public int getSeq() {
		return this.seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FieldzonedetailPK)) {
			return false;
		}
		FieldzonedetailPK castOther = (FieldzonedetailPK)other;
		return 
			(this.fieldZoneId == castOther.fieldZoneId)
			&& (this.seq == castOther.seq);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.fieldZoneId;
		hash = hash * prime + this.seq;
		
		return hash;
	}
}