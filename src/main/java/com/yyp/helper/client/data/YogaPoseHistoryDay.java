package com.yyp.helper.client.data;

import java.io.Serializable;

public class YogaPoseHistoryDay implements Serializable {
	
	String dayName;
	int minutes;

	public YogaPoseHistoryDay(){}
	
	public YogaPoseHistoryDay(String dayName, int minutes) {
		this.dayName = dayName;
		this.minutes = minutes;
	}

	public String getDayName() {
		return dayName;
	}

	public void setDayName(String dayName) {
		this.dayName = dayName;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	@Override
	public String toString() {
		return "YogaPoseHistoryDay [dayName=" + dayName + ", minutes="
				+ minutes + "]";
	}

}
