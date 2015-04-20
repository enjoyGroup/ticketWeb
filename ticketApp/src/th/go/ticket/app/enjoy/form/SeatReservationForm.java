package th.go.ticket.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.ticket.app.enjoy.bean.SeatReservationBean;

public class SeatReservationForm {
	
	private String							fieldZoneId;
	private String							fieldZoneName;
	private String							matchId;
	private String							season;
	private String							awayTeamNameTH;
	private SeatReservationBean				seatReservationBean;
	private List<SeatReservationBean> 		ticketTypeList;

	public SeatReservationForm() {
		this.fieldZoneId 			= "";
		this.fieldZoneName 			= "";
		this.matchId 				= "";
		this.season 				= "";
		this.awayTeamNameTH 		= "";
		this.seatReservationBean 	= new SeatReservationBean();
		this.ticketTypeList			= new ArrayList<SeatReservationBean>();
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

	public SeatReservationBean getSeatReservationBean() {
		return seatReservationBean;
	}

	public void setSeatReservationBean(SeatReservationBean seatReservationBean) {
		this.seatReservationBean = seatReservationBean;
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
	
}
