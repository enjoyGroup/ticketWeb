package th.go.ticket.app.enjoy.form;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import th.go.ticket.app.enjoy.bean.DisplayMatchDetailBean;

public class DisplayMacthForm {

	List<String> 	seasonList;
	Map 			matchMap;
	String			matchId;
	String			awayTeamName;
	String			season;

	public DisplayMacthForm() {
		this.seasonList 	= new ArrayList<String>();
		this.matchMap 		= new Hashtable();
		this.matchId		= "";
		this.awayTeamName	= "";
		this.season			= "";
	}

	public List<String> getSeasonList() {
		return seasonList;
	}

	public void setSeasonList(List<String> seasonList) {
		this.seasonList = seasonList;
	}

	public Map getMatchMap() {
		return matchMap;
	}

	public void setMatchMap(Map matchMap) {
		this.matchMap = matchMap;
	}

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public String getAwayTeamName() {
		return awayTeamName;
	}

	public void setAwayTeamName(String awayTeamName) {
		this.awayTeamName = awayTeamName;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

}
