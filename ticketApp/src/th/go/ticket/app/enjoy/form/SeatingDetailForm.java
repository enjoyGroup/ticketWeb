package th.go.ticket.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;
 
 
import th.go.ticket.app.enjoy.bean.SeatingDetailBean;

public class SeatingDetailForm {
	
	List<SeatingDetailBean> 		seatingDetailBeans; 
	
	public SeatingDetailForm(){
		this.seatingDetailBeans     = new ArrayList<SeatingDetailBean>();  
	}

	public List<SeatingDetailBean> getSeatingDetailBeans() {
		return seatingDetailBeans;
	}

	public void setSeatingDetailBeans(List<SeatingDetailBean> seatingDetailBeans) {
		this.seatingDetailBeans = seatingDetailBeans;
	}

	@Override
	public String toString() {
		return "SeatingDetailForm [seatingDetailBeans=" + seatingDetailBeans
				+ "]";
	}
 

}
