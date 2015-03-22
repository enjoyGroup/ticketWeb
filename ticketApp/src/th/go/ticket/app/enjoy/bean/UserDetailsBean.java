package th.go.ticket.app.enjoy.bean;

public class UserDetailsBean {
	
	private int 	userUniqueId;
	private String 	flagChangePassword;
	private String 	userId;
	private String 	userName;
	private String 	userPrivilege;
	private String 	userStatus;
	private String 	userSurname;
	
	public UserDetailsBean(){
		this.userUniqueId		= 0;
		this.flagChangePassword	= "";
		this.userId				= "";
		this.userName			= "";
		this.userPrivilege		= "";
		this.userStatus			= "";
		this.userSurname		= "";
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
	
}
