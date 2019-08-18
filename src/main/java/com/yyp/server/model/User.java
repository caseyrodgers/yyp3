package com.yyp.server.model;

import java.util.ArrayList;
import java.util.List;

public class User {
	
	long userId;
	String email;
	private List<Client> clientKeys = new ArrayList<Client>();
	
	public User(long userId, String email, List<Client> clientKeys) {
		this.userId = userId;
		this.email = email;
		if(clientKeys != null) {
			this.clientKeys.addAll(clientKeys);
		}
	}
	
	public User(long userId, String email) {
		this(userId, email, null);
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Client> getClientKeys() {
		return this.clientKeys;
	}
}
