package th.go.ticket.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.ticket.app.enjoy.bean.SeatSummaryReservationBean;


public class SeatSummaryReservationForm {
	
	private List<SeatSummaryReservationBean> 	resultList;
	private String								sumBookingPrices;
	private String 								awayTeamNameTH;
	private String 								awayTeamNameEN;
	private String 								matchDate;
	private String 								matchTime;
	private String								matchId;
	private String								fieldZoneId;
	

	public SeatSummaryReservationForm() {
		this.resultList 		= new ArrayList<SeatSummaryReservationBean>();
		this.sumBookingPrices 	= "";
		this.awayTeamNameTH 	= "";
		this.awayTeamNameEN 	= "";
		this.matchDate 			= "";
		this.matchTime 			= "";
		this.matchId 			= "";
		this.fieldZoneId 		= "";
	}


	public List<SeatSummaryReservationBean> getResultList() {
		return resultList;
	}


	public void setResultList(List<SeatSummaryReservationBean> resultList) {
		this.resultList = resultList;
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
	
	
}
