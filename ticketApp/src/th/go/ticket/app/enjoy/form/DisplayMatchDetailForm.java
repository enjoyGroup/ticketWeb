package th.go.ticket.app.enjoy.form;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import th.go.ticket.app.enjoy.bean.DisplayMatchDetailBean;

public class DisplayMatchDetailForm {

	List<String> 				seasonList;
	Map		 					matchMap;
	List<DisplayMatchDetailBean> headerTicketTypeList;
	String 						awayTeamName;
	String 						jsonString;
	List<String> 				fieldZoneNameList;
	Map 						detailOfMatchMap;
	Map 						sumTatalMoneyMap;

	public DisplayMatchDetailForm() {
		this.seasonList 			= new ArrayList<String>();
		this.matchMap 				= new Hashtable();
		this.headerTicketTypeList 	= new ArrayList<DisplayMatchDetailBean>();
		this.awayTeamName 			= "";
		this.jsonString 			= "";
		this.fieldZoneNameList 		= new ArrayList<String>();
		this.detailOfMatchMap 		= new Hashtable();
		this.sumTatalMoneyMap 		= new Hashtable();
	}

	public Map getDetailOfMatchMap() {
		return detailOfMatchMap;
	}

	public void setDetailOfMatchMap(Map detailOfMatchMap) {
		this.detailOfMatchMap = detailOfMatchMap;
	}

	public List<String> getFieldZoneNameList() {
		return fieldZoneNameList;
	}

	public void setFieldZoneNameList(List<String> fieldZoneNameList) {
		this.fieldZoneNameList = fieldZoneNameList;
	}

	public List<String> getSeasonList() {
		return seasonList;
	}

	public Map getMatchMap() {
		return matchMap;
	}

	public void setMatchMap(Map matchMap) {
		this.matchMap = matchMap;
	}

	public void setSeasonList(List<String> seasonList) {
		this.seasonList = seasonList;
	}

	public List<DisplayMatchDetailBean> getHeaderTicketTypeList() {
		return headerTicketTypeList;
	}

	public void setHeaderTicketTypeList(
			List<DisplayMatchDetailBean> headerTicketTypeList) {
		this.headerTicketTypeList = headerTicketTypeList;
	}

	public String getAwayTeamName() {
		return awayTeamName;
	}

	public void setAwayTeamName(String awayTeamName) {
		this.awayTeamName = awayTeamName;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public Map getSumTatalMoneyMap() {
		return sumTatalMoneyMap;
	}

	public void setSumTatalMoneyMap(Map sumTatalMoneyMap) {
		this.sumTatalMoneyMap = sumTatalMoneyMap;
	}

}
