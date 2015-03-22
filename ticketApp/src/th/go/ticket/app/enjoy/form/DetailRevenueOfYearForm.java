package th.go.ticket.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.ticket.app.enjoy.bean.DetailRevenueOfYearBean;

public class DetailRevenueOfYearForm {
	
	List<String> 					seasonList;
	List<DetailRevenueOfYearBean> 	detailList;
	String							season;
	String							dataFlow;
	
	public DetailRevenueOfYearForm(){
		this.seasonList = new ArrayList<String>();
		this.detailList = new ArrayList<DetailRevenueOfYearBean>();
		this.season		= "";
		this.dataFlow	= "";
	}

	public List<String> getSeasonList() {
		return seasonList;
	}

	public void setSeasonList(List<String> seasonList) {
		this.seasonList = seasonList;
	}

	public List<DetailRevenueOfYearBean> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<DetailRevenueOfYearBean> detailList) {
		this.detailList = detailList;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getDataFlow() {
		return dataFlow;
	}

	public void setDataFlow(String dataFlow) {
		this.dataFlow = dataFlow;
	}

}
