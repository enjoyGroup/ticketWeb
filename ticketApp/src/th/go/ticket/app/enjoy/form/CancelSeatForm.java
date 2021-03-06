package th.go.ticket.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.ticket.app.enjoy.bean.CancelSeatBean;
import th.go.ticket.app.enjoy.bean.SeatZoneBean;


public class CancelSeatForm {
	
	public static final String NEW 	= "NEW";
	public static final String EDIT = "EDIT";
	
	private List<CancelSeatBean> 				fieldZoneList;
	private List<CancelSeatBean> 				resultList;
	private List<String> 						seasonList;
	private List<CancelSeatBean> 				teamList;
	private String								errMsg;
	private String								pageMode;
	private String								seatingNoBegin;
	private String 								seatingNoEnd;
	private String 								ticketId;
	private String 								fieldZoneId;
	private String								matchId;
	private String								season;
	private CancelSeatBean		 				cancelSeatBean;
	private String								resultSize;
	private String								awayTeamNameTH;
	

	public CancelSeatForm() {

		this.errMsg				= "";
		this.pageMode			= NEW;
		this.fieldZoneList 		= new ArrayList<CancelSeatBean>();
		this.resultList 		= new ArrayList<CancelSeatBean>();
		this.seasonList 		= new ArrayList<String>();
		this.teamList	 		= new ArrayList<CancelSeatBean>();
		this.cancelSeatBean		= new CancelSeatBean();
		
		this.seatingNoBegin		= "";
		this.seatingNoEnd		= "";
		this.ticketId			= "";
		this.fieldZoneId 		= "";
		this.season 			= "";
		this.matchId 			= "";
		this.resultSize			= "0";
		this.awayTeamNameTH		= "";
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

	public List<CancelSeatBean> getFieldZoneList() {
		return fieldZoneList;
	}
	public void setFieldZoneList(List<CancelSeatBean> fieldZoneList) {
		this.fieldZoneList = fieldZoneList;
	}

	public List<CancelSeatBean> getResultList() {
		return resultList;
	}
	public void setResultList(List<CancelSeatBean> resultList) {
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

	public String getFieldZoneId() {
		return fieldZoneId;
	}

	public void setFieldZoneId(String fieldZoneId) {
		this.fieldZoneId = fieldZoneId;
	}

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public List<String> getSeasonList() {
		return seasonList;
	}

	public void setSeasonList(List<String> seasonList) {
		this.seasonList = seasonList;
	}

	public List<CancelSeatBean> getTeamList() {
		return teamList;
	}

	public void setTeamList(List<CancelSeatBean> teamList) {
		this.teamList = teamList;
	}

	public CancelSeatBean getCancelSeatBean() {
		return cancelSeatBean;
	}

	public void setCancelSeatBean(CancelSeatBean cancelSeatBean) {
		this.cancelSeatBean = cancelSeatBean;
	}

	public String getResultSize() {
		return resultSize;
	}

	public void setResultSize(String resultSize) {
		this.resultSize = resultSize;
	}

	public String getAwayTeamNameTH() {
		return awayTeamNameTH;
	}

	public void setAwayTeamNameTH(String awayTeamNameTH) {
		this.awayTeamNameTH = awayTeamNameTH;
	}
	
}
