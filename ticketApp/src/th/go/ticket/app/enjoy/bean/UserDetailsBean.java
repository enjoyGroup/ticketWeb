package th.go.ticket.app.enjoy.bean;

import java.util.ArrayList;

public class UserDetailsBean {
	
	private int 							userUniqueId;
	private String 							userId 	 			  = null;
	private String 							userName 	 		  = null;
	private String 							userSurname 	 	  = null;
	private String 							userPrivilege 	 	  = null;
	private String 							userLevel 	  		  = null;
	private String 							userStatus 	 		  = null;
	private String 							flagChangePassword 	  = null;
	private ArrayList<UserPrivilegeBean> 	userPrivilegeList	  = null;
	private String 							pwd 	 		  	  = null;
	private String  						currentDate			  = null;
	private String							userEmail			  = null;
	
	public UserDetailsBean(){
		this.userUniqueId		= 0;
		this.userId				= "";
		this.userName			= "";
		this.userSurname		= "";
		this.userPrivilege		= "";
		this.userLevel			= "";
		this.userStatus			= "";
		this.flagChangePassword	= "";
		this.userPrivilegeList	= new ArrayList<UserPrivilegeBean>();
		this.pwd				= "";
		this.currentDate		= "";
		this.userEmail			= "";
	}

	public int getUserUniqueId() {
		return userUniqueId;
	}

	public void setUserUniqueId(int userUniqueId) {
		this.userUniqueId = userUniqueId;
	}

	public String getFlagChangePassword() {
		return flagChangePassword;
	}

	public void setFlagChangePassword(String flagChangePassword) {
		this.flagChangePassword = flagChangePassword;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPrivilege() {
		return userPrivilege;
	}

	public void setUserPrivilege(String userPrivilege) {
		this.userPrivilege = userPrivilege;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getUserSurname() {
		return userSurname;
	}

	public void setUserSurname(String userSurname) {
		this.userSurname = userSurname;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public ArrayList<UserPrivilegeBean> getUserPrivilegeList() {
		return userPrivilegeList;
	}

	public void setUserPrivilegeList(ArrayList<UserPrivilegeBean> userPrivilegeList) {
		this.userPrivilegeList = userPrivilegeList;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
}
