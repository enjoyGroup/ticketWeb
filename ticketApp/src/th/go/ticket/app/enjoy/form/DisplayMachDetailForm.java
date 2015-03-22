package th.go.ticket.app.enjoy.form;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import th.go.ticket.app.enjoy.bean.DisplayMachDetailBean;

public class DisplayMachDetailForm {
	
	List<String> 				seasonList;
	Map							matchMap;
	List<DisplayMachDetailBean>	headerTicketTypeList;
	List<DisplayMachDetailBean>	detailOfMatchList;
	String						awayTeamName;
	String						jsonString;
	
	public DisplayMachDetailForm(){
		this.seasonList 			= new ArrayList<String>();
		this.matchMap				= new Hashtable();
		this.headerTicketTypeList 	= new ArrayList<DisplayMachDetailBean>();
		this.detailOfMatchList 		= new ArrayList<DisplayMachDetailBean>();
		this.awayTeamName			= "";
		this.jsonString				= "";
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

	public List<DisplayMachDetailBean> getHeaderTicketTypeList() {
		return headerTicketTypeList;
	}

	public void setHeaderTicketTypeList(
			List<DisplayMachDetailBean> headerTicketTypeList) {
		this.headerTicketTypeList = headerTicketTypeList;
	}

	public List<DisplayMachDetailBean> getDetailOfMatchList() {
		return detailOfMatchList;
	}

	public void setDetailOfMatchList(List<DisplayMachDetailBean> detailOfMatchList) {
		this.detailOfMatchList = detailOfMatchList;
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

}
