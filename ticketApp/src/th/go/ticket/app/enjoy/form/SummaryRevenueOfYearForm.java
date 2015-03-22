package th.go.ticket.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.ticket.app.enjoy.bean.SummaryRevenueOfYearBean;

public class SummaryRevenueOfYearForm {
	
	List<SummaryRevenueOfYearBean> 	resultList;
	String							dataFlow;
	
	public SummaryRevenueOfYearForm(){
		this.resultList = new ArrayList<SummaryRevenueOfYearBean>();
		this.dataFlow	= "";
	}

	public List<SummaryRevenueOfYearBean> getResultList() {
		return resultList;
	}

	public void setResultList(List<SummaryRevenueOfYearBean> resultList) {
		this.resultList = resultList;
	}

	public String getDataFlow() {
		return dataFlow;
	}

	public void setDataFlow(String dataFlow) {
		this.dataFlow = dataFlow;
	}

}
