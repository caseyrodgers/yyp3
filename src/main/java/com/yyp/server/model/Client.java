package com.yyp.server.model;

import java.util.Date;

public class Client {
	private long id;
	private long userId;
	private String deviceId;
	private String clientKey;
	private String endPoint;
	private Date updated;

	public Client(){}
	
	public Client(int id, long userId, String deviceId, String clientKey, String endPoint, Date updated) {
		this.id = id;
		this.userId = userId;
		this.deviceId = deviceId;
		this.clientKey = clientKey;
		this.endPoint = endPoint;
		this.updated = updated;
	}

	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getClientKey() {
		return clientKey;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
}
