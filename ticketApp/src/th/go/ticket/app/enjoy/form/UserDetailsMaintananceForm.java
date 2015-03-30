package th.go.ticket.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.ticket.app.enjoy.bean.RefuserstatusBean;
import th.go.ticket.app.enjoy.bean.UserDetailsBean;
import th.go.ticket.app.enjoy.model.Refuserstatus;
import th.go.ticket.app.enjoy.model.Userprivilege;

public class UserDetailsMaintananceForm {

	
	UserDetailsBean 		userDetailsBean;
	List<RefuserstatusBean> refuserstatusCombo;
	List<Userprivilege> 	userprivilegeList;
	String					errMsg;
	
	public UserDetailsMaintananceForm(){
		this.userDetailsBean 		= new UserDetailsBean();
		this.refuserstatusCombo		= new ArrayList<RefuserstatusBean>();
		this.userprivilegeList		= new ArrayList<Userprivilege>();
		this.errMsg					= "";
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

	
	
}
