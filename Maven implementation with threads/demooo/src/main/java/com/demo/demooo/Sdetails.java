package com.demo.demooo;

public class Sdetails {

	String  startDate;
//	   @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss",shape = JsonFormat.Shape.STRING) 
	   String endDate;
	   public Sdetails(String startDate, String endDate, String capacity, String hallName) {
	super();
	this.startDate = startDate;
	this.endDate = endDate;
	this.capacity = capacity;
	this.hallName = hallName;
}
	public Sdetails() {
	super();
}
	@Override
	public String toString() {
		return "Sdetails [startDate=" + startDate + ", endDate=" + endDate + ", capacity=" + capacity + ", hallName="
				+ hallName + "]";
	}
	String capacity;
	   String hallName;
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
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
