package th.go.ticket.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;
 

import th.go.ticket.app.enjoy.bean.EventMatchBean; 

public class EventMatchForm {
	
	List<String> 					seasonList;
	List<EventMatchBean> 	        eventMatchList;
	String							season; 
	
	public EventMatchForm(){
		this.seasonList     = new ArrayList<String>();
		this.eventMatchList = new ArrayList<EventMatchBean>();
		this.season		    = ""; 
	}

	public List<String> getSeasonList() {
		return seasonList;
	}

	public void setSeasonList(List<String> seasonList) {
		this.seasonList = seasonList;
	} 

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public List<EventMatchBean> getEventMatchList() {
		return eventMatchList;
	}

	public void setEventMatchList(List<EventMatchBean> eventMatchList) {
		this.eventMatchList = eventMatchList;
	}

	 

}
