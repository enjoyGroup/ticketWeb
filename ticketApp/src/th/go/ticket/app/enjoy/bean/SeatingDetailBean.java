package th.go.ticket.app.enjoy.bean;

import java.util.List;

/**
 * @author acer
 *
 */
public class SeatingDetailBean {
	private int fieldZoneId;

	private String fieldZoneName;

	private int rows;

	private int seating;

	private int totalSeating;
	 
	private String nameRow;
	
	private int typeRowName; 
	
	private String fieldZoneNameTicket;
	
	private int startSeatingNo;
	
	private List<FieldZoneDetailBean> fieldZoneDetailBeans;

 
	public SeatingDetailBean(){ 
		 
	}

	public int getFieldZoneId() {
		return fieldZoneId;
	}

	public void setFieldZoneId(int fieldZoneId) {
		this.fieldZoneId = fieldZoneId;
	}

	public String getFieldZoneName() {
		return fieldZoneName;
	}

	public void setFieldZoneName(String fieldZoneName) {
		this.fieldZoneName = fieldZoneName;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getSeating() {
		return seating;
	}

	public void setSeating(int seating) {
		this.seating = seating;
	}

	public int getTotalSeating() {
		return totalSeating;
	}

	public void setTotalSeating(int totalSeating) {
		this.totalSeating = totalSeating;
	}
 
	public String getNameRow() {
		return nameRow;
	}

	public void setNameRow(String nameRow) {
		this.nameRow = nameRow;
	}
	

	public int getTypeRowName() {
		return typeRowName;
	}

	public void setTypeRowName(int typeRowName) {
		this.typeRowName = typeRowName;
	}

	public List<FieldZoneDetailBean> getFieldZoneDetailBeans() {
		return fieldZoneDetailBeans;
	}

	public void setFieldZoneDetailBeans(
			List<FieldZoneDetailBean> fieldZoneDetailBeans) {
		this.fieldZoneDetailBeans = fieldZoneDetailBeans;
	}
	
	

	public String getFieldZoneNameTicket() {
		return fieldZoneNameTicket;
	}

	public void setFieldZoneNameTicket(String fieldZoneNameTicket) {
		this.fieldZoneNameTicket = fieldZoneNameTicket;
	}

	public int getStartSeatingNo() {
		return startSeatingNo;
	}

	public void setStartSeatingNo(int startSeatingNo) {
		this.startSeatingNo = startSeatingNo;
	}

	@Override
	public String toString() {
		return "SeatingDetailBean [fieldZoneId=" + fieldZoneId
				+ ", fieldZoneName=" + fieldZoneName + ", rows=" + rows
				+ ", seating=" + seating + ", totalSeating=" + totalSeating
				+ ", nameRow=" + nameRow + ", typeRowName=" + typeRowName
				+ ", fieldZoneNameTicket=" + fieldZoneNameTicket
				+ ", startSeatingNo=" + startSeatingNo
				+ ", fieldZoneDetailBeans=" + fieldZoneDetailBeans + "]";
	}

	 
 
  
 
}
