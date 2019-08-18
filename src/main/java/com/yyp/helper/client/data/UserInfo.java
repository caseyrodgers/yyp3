package com.yyp.helper.client.data;

import java.io.Serializable;


public class UserInfo implements Serializable {
	
	String userName, password;
	int uid;
	
	public UserInfo(){}
	
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public UserInfo(String u, String p, int uid) {
		this.userName = u;
		this.password = p;
		this.uid = uid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserInfo [userName=" + userName + ", password=" + password
				+ ", uid=" + uid + "]";
	}
}
