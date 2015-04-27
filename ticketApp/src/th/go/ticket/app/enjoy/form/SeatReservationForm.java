package th.go.ticket.app.enjoy.form;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import th.go.ticket.app.enjoy.bean.SeatReservationBean;
import th.go.ticket.app.enjoy.main.ConfigFile;

public class SeatReservationForm {
	
	public final static String FREE 				= "";//สถานะว่าง
	public final static String ACTIVE 				= "A";//สถานะมีคนจองแล้ว
	public final static String PENDING 				= "P";//สถานะกำลังจะจอง
	public final static String REJECT 				= "R";//สถานะยกเลิก
	public final static String FILL_ZERO 			= ConfigFile.getPadingSeatNo();
	public final static String FILL_ZERO_TICKET_ID 	= ConfigFile.getPadingTicketId();
	
	private String							fieldZoneId;
	private String							fieldZoneName;
	private String							matchId;
	private String							season;
	private String							awayTeamNameTH;
	private List<SeatReservationBean> 		ticketTypeList;
	private String							flagAlterSeat;
	private List<String>					seatNameList;
	private Map								rowsMap;
	private String							userUniqueId;
	private String							bookingTypeId;
	private String							bookingTypeName;
	private String							fieldZoneNameTicket;
	private Map<String, Integer>			mapBookingType;

	public SeatReservationForm() {
		this.fieldZoneId 			= "";
		this.fieldZoneName 			= "";
		this.matchId 				= "";
		this.season 				= "";
		this.awayTeamNameTH 		= "";
		this.ticketTypeList			= new ArrayList<SeatReservationBean>();
		this.flagAlterSeat			= "0";//0 ไม่สามารถเลือกที่นั่งได้, 1 เลือกที่นั่งได้
		this.seatNameList			= new ArrayList<String>();
		this.rowsMap				= new Hashtable();
		this.userUniqueId			= "";
		this.bookingTypeId			= "";
		this.bookingTypeName		= "";
		this.fieldZoneNameTicket	= "";
		this.mapBookingType			= new Hashtable<String, Integer>();
	}

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public String getFieldZoneId() {
		return fieldZoneId;
	}

	public void setFieldZoneId(String fieldZoneId) {
		this.fieldZoneId = fieldZoneId;
	}

	public List<SeatReservationBean> getTicketTypeList() {
		return ticketTypeList;
	}

	public void setTicketTypeList(List<SeatReservationBean> ticketTypeList) {
		this.ticketTypeList = ticketTypeList;
	}

	public String getFieldZoneName() {
		return fieldZoneName;
	}

	public void setFieldZoneName(String fieldZoneName) {
		this.fieldZoneName = fieldZoneName;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getAwayTeamNameTH() {
		return awayTeamNameTH;
	}

	public void setAwayTeamNameTH(String awayTeamNameTH) {
		this.awayTeamNameTH = awayTeamNameTH;
	}

	public String getFlagAlterSeat() {
		return flagAlterSeat;
	}

	public void setFlagAlterSeat(String flagAlterSeat) {
		this.flagAlterSeat = flagAlterSeat;
	}

	public List<String> getSeatNameList() {
		return seatNameList;
	}

	public void setSeatNameList(List<String> seatNameList) {
		this.seatNameList = seatNameList;
	}

	public Map getRowsMap() {
		return rowsMap;
	}

	public void setRowsMap(Map rowsMap) {
		this.rowsMap = rowsMap;
	}

	public String getUserUniqueId() {
		return userUniqueId;
	}

	public void setUserUniqueId(String userUniqueId) {
		this.userUniqueId = userUniqueId;
	}

	public String getBookingTypeId() {
		return bookingTypeId;
	}

	public void setBookingTypeId(String bookingTypeId) {
		this.bookingTypeId = bookingTypeId;
	}

	public String getBookingTypeName() {
		return bookingTypeName;
	}

	public void setBookingTypeName(String bookingTypeName) {
		this.bookingTypeName = bookingTypeName;
	}

	public String getFieldZoneNameTicket() {
		return fieldZoneNameTicket;
	}

	public void setFieldZoneNameTicket(String fieldZoneNameTicket) {
		this.fieldZoneNameTicket = fieldZoneNameTicket;
	}

	public Map<String, Integer> getMapBookingType() {
		return mapBookingType;
	}

	public void setMapBookingType(Map<String, Integer> mapBookingType) {
		this.mapBookingType = mapBookingType;
	}
	
}
