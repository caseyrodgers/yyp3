package com.yyp.helper.client.data;

import java.io.Serializable;

public class YogaPoseCompleted implements Serializable {
	
	String poseId;
	int secondsHeld;
	int timesHeld;

	public YogaPoseCompleted(){}
	
	public YogaPoseCompleted(String poseId, int secondsHeld) {
		this.poseId = poseId;
		this.secondsHeld = secondsHeld;
	}

	
	public int getTimesHeld() {
		return timesHeld;
	}

	public void setTimesHeld(int timesHeld) {
		this.timesHeld = timesHeld;
	}

	
	public String getPoseId() {
		return poseId;
	}

	public void setPoseId(String poseId) {
		this.poseId = poseId;
	}

	public int getSecondsHeld() {
		return secondsHeld;
	}

	public void setSecondsHeld(int secondsHeld) {
		this.secondsHeld = secondsHeld;
	}

	@Override
	public String toString() {
		return "YogaPoseCompleted [poseId=" + poseId + ", secondsHeld="
				+ secondsHeld + ", timesHeld=" + timesHeld + "]";
	}
}
