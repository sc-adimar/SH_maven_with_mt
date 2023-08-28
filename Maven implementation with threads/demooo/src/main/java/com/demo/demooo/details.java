package com.demo.demooo;

import java.time.LocalDateTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class details {
	LocalDateTime startDate;
	LocalDateTime endDate;
     String capacity;
    String hallName;
    

	public LocalDateTime getStartDate() {
		return startDate;}
	
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	public LocalDateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	public String getCapacity() {
		return capacity;
	}
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	public String getHallName() {
		return hallName;
	}
	public void setHallName(String hallName) {
		this.hallName = hallName;
}
}
	
	
