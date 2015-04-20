package th.go.ticket.app.enjoy.form;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import th.go.ticket.app.enjoy.bean.DisplayMatchDetailBean;
import th.go.ticket.app.enjoy.bean.SeatZoneBean;

public class SeatZoneForm {

	private Map 				matchMap;
	private List<SeatZoneBean> 	fieldZoneList;
	private String				matchId;
	private String 				awayTeamNameTH;
	private List<String> 		seasonList;
	private String 				season;

	public SeatZoneForm() {
		this.matchMap 			= new Hashtable();
		this.fieldZoneList 		= new ArrayList<SeatZoneBean>();
		this.matchId			= "";
		this.awayTeamNameTH		= "";
		this.season				= "";
		this.seasonList 		= new ArrayList<String>();
	}

	public Map getMatchMap() {
		return matchMap;
	}

	public void setMatchMap(Map matchMap) {
		this.matchMap = matchMap;
	}

	public List<SeatZoneBean> getFieldZoneList() {
		return fieldZoneList;
	}

	public void setFieldZoneList(List<SeatZoneBean> fieldZoneList) {
		this.fieldZoneList = fieldZoneList;
	}

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public List<String> getSeasonList() {
		return seasonList;
	}

	public void setSeasonList(List<String> seasonList) {
		this.seasonList = seasonList;
	}

	public String getAwayTeamNameTH() {
		return awayTeamNameTH;
	}

	public void setAwayTeamNameTH(String awayTeamNameTH) {
		this.awayTeamNameTH = awayTeamNameTH;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}
	

}
