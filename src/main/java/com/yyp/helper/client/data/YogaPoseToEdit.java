package com.yyp.helper.client.data;

import java.io.Serializable;

public class YogaPoseToEdit implements Serializable {
	YogaPose pose;
	private String alignText;
	
	public YogaPoseToEdit() {}
	
	public YogaPoseToEdit(YogaPose pose, String alignText) {
		this.pose = pose;
		this.alignText = alignText;
	}
	public YogaPose getPose() {
		return pose;
	}
	public void setPose(YogaPose pose) {
		this.pose = pose;
	}
	public String getAlignText() {
		return alignText;
	}
	public void setAlignText(String alignText) {
		this.alignText = alignText;
	}
}
