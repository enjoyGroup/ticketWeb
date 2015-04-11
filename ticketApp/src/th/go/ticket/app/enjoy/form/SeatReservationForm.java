package th.go.ticket.app.enjoy.form;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import th.go.ticket.app.enjoy.bean.DisplayMatchDetailBean;
import th.go.ticket.app.enjoy.bean.SeatZoneBean;

public class SeatReservationForm {
	
	String				matchId;
	String				fieldZoneId;

	public SeatReservationForm() {
		this.matchId 		= "";
		this.fieldZoneId 	= "";
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
