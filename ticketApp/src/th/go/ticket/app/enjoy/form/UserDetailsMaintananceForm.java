package th.go.ticket.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.ticket.app.enjoy.bean.RefuserstatusBean;
import th.go.ticket.app.enjoy.bean.UserDetailsBean;
import th.go.ticket.app.enjoy.model.Refuserstatus;
import th.go.ticket.app.enjoy.model.Userprivilege;

public class UserDetailsMaintananceForm {
	
	public static final String NEW 	= "NEW";
	public static final String EDIT = "EDIT";
	
	
	private UserDetailsBean 		userDetailsBean;
	private List<RefuserstatusBean> refuserstatusCombo;
	private List<Userprivilege> 	userprivilegeList;
	private String					errMsg;
	private String					pageMode;
	private String					titlePage;
	
	public UserDetailsMaintananceForm(){
		this.userDetailsBean 		= new UserDetailsBean();
		this.refuserstatusCombo		= new ArrayList<RefuserstatusBean>();
		this.userprivilegeList		= new ArrayList<Userprivilege>();
		this.errMsg					= "";
		this.pageMode				= NEW;
		this.titlePage				= "";
	}

	public UserDetailsBean getUserDetailsBean() {
		return userDetailsBean;
	}

	public void setUserDetailsBean(UserDetailsBean userDetailsBean) {
		this.userDetailsBean = userDetailsBean;
	}

	public List<RefuserstatusBean> getRefuserstatusCombo() {
		return refuserstatusCombo;
	}

	public void setRefuserstatusCombo(List<RefuserstatusBean> refuserstatusCombo) {
		this.refuserstatusCombo = refuserstatusCombo;
	}

	public List<Userprivilege> getUserprivilegeList() {
		return userprivilegeList;
	}

	public void setUserprivilegeList(List<Userprivilege> userprivilegeList) {
		this.userprivilegeList = userprivilegeList;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getPageMode() {
		return pageMode;
	}

	public void setPageMode(String pageMode) {
		this.pageMode = pageMode;
	}

	public String getTitlePage() {
		return titlePage;
	}

	public void setTitlePage(String titlePage) {
		this.titlePage = titlePage;
	}

	
	
}
