package th.go.ticket.app.enjoy.form;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import th.go.ticket.app.enjoy.bean.DisplayMatchDetailBean;
import th.go.ticket.app.enjoy.bean.SeatZoneBean;

public class SeatReservationForm {

	Map 				matchMap;
	List<SeatZoneBean> 	fieldZoneList;
	String				matchId;

	public SeatReservationForm() {
		this.matchMap 		= new Hashtable();
		this.fieldZoneList 	= new ArrayList<SeatZoneBean>();
		this.matchId		= "";
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

}
