package th.go.ticket.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.ticket.app.enjoy.bean.SeatSummaryReservationBean;
import th.go.ticket.app.enjoy.bean.SeatZoneBean;


public class CancelSeatForm {
	
	public static final String NEW 	= "NEW";
	public static final String EDIT = "EDIT";
	
	private String								sumBookingPrices;
	private String 								awayTeamNameTH;
	private String 								awayTeamNameEN;
	private String 								matchDate;
	private String 								matchTime;
	private String								matchId;
//	private String								fieldZoneId;

	private List<SeatZoneBean> 					fieldZoneList;
	private List<SeatSummaryReservationBean> 	resultList;
	private String								errMsg;
	private String								pageMode;
	private String								seatingNoBegin;
	private String 								seatingNoEnd;
	private String 								ticketId;
	private String 								fieldZoneId;
	

	public CancelSeatForm() {
		this.sumBookingPrices 	= "";
		this.awayTeamNameTH 	= "";
		this.awayTeamNameEN 	= "";
		this.matchDate 			= "";
		this.matchTime 			= "";
		this.matchId 			= "";

		this.errMsg				= "";
		this.pageMode			= NEW;
		this.fieldZoneList 		= new ArrayList<SeatZoneBean>();
		this.resultList 		= new ArrayList<SeatSummaryReservationBean>();

		this.seatingNoBegin		= "";
		this.seatingNoEnd		= "";
		this.ticketId			= "";
		this.fieldZoneId 		= "";
	}



	public String getSumBookingPrices() {
		return sumBookingPrices;
	}


	public void setSumBookingPrices(String sumBookingPrices) {
		this.sumBookingPrices = sumBookingPrices;
	}


	public String getAwayTeamNameTH() {
		return awayTeamNameTH;
	}


	public void setAwayTeamNameTH(String awayTeamNameTH) {
		this.awayTeamNameTH = awayTeamNameTH;
	}


	public String getAwayTeamNameEN() {
		return awayTeamNameEN;
	}


	public void setAwayTeamNameEN(String awayTeamNameEN) {
		this.awayTeamNameEN = awayTeamNameEN;
	}


	public String getMatchDate() {
		return matchDate;
	}


	public void setMatchDate(String matchDate) {
		this.matchDate = matchDate;
	}


	public String getMatchTime() {
		return matchTime;
	}


	public void setMatchTime(String matchTime) {
		this.matchTime = matchTime;
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
	
	

	//*****************************************************//
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getPageMode() {
		return pageMode;
	}
	public void setPageMode(String pageMode) {
		this.pageMode = pageMode;
	}

	public List<SeatZoneBean> getFieldZoneList() {
		return fieldZoneList;
	}
	public void setFieldZoneList(List<SeatZoneBean> fieldZoneList) {
		this.fieldZoneList = fieldZoneList;
	}

	public List<SeatSummaryReservationBean> getResultList() {
		return resultList;
	}
	public void setResultList(List<SeatSummaryReservationBean> resultList) {
		this.resultList = resultList;
	}

	public String getSeatingNoBegin() {
		return seatingNoBegin;
	}
	public void setSeatingNoBegin(String seatingNoBegin) {
		this.seatingNoBegin = seatingNoBegin;
	}

	public String getSeatingNoEnd() {
		return seatingNoEnd;
	}
	public void setSeatingNoEnd(String seatingNoEnd) {
		this.seatingNoEnd = seatingNoEnd;
	}

	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	
}
