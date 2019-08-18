package com.yyp.helper.client.data;

import java.io.Serializable;


public class YogaServerConnection implements Serializable {
	
	YogaMasterData masterData;
	String serverUrl;
	
	public YogaServerConnection(){}
	
	public YogaServerConnection(YogaMasterData data, String serverUrl) {
		this.masterData = data;
		this.serverUrl = serverUrl;
	}

	public YogaMasterData getMasterData() {
		return masterData;
	}

	public void setMasterData(YogaMasterData masterData) {
		this.masterData = masterData;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
}
