package com.histomon.api;

public class SiteFilter {
	protected Integer startIndex;
	protected Integer numOfEntries;
	protected Integer type;
	protected String location;
	protected String country;
	protected String state;
	protected String city;
	public Integer getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}
	public Integer getNumOfEntries() {
		return numOfEntries;
	}
	public void setNumOfEntries(Integer numOfEntries) {
		this.numOfEntries = numOfEntries;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
}
