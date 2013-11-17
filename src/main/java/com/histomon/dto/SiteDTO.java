package com.histomon.dto;

import java.util.List;
import java.util.Map;

import com.histomon.api.SiteTypeEnum;

public class SiteDTO {
	protected Long id;
	protected String name;
	protected String description;
	protected SiteTypeEnum type;
	protected String imageUrl;
	protected String address1;
	protected String city;
	protected String state;
	protected String country;
	protected String zip;
	protected String location;
	protected String externalId;
	protected String externalSource;
	
	protected String hike;
	protected Map<String, String> details;
	protected String district;
	protected List<String> gallery;
	protected Integer rank;
	protected String nicename;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public SiteTypeEnum getType() {
		return type;
	}
	public void setType(SiteTypeEnum type) {
		this.type = type;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getExternalSource() {
		return externalSource;
	}
	public void setExternalSource(String externalSource) {
		this.externalSource = externalSource;
	}
	public String getHike() {
		return hike;
	}
	public void setHike(String hike) {
		this.hike = hike;
	}
	public Map<String, String> getDetails() {
		return details;
	}
	public void setDetails(Map<String, String> details) {
		this.details = details;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public List<String> getGallery() {
		return gallery;
	}
	public void setGallery(List<String> gallery) {
		this.gallery = gallery;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	@Override
  public String toString() {
	  return "SiteDTO ["
	      + (id != null ? "id=" + id + ", " : "")
	      + (name != null ? "name=" + name + ", " : "")
	      + (description != null ? "description=" + description + ", " : "")
	      + (type != null ? "type=" + type + ", " : "")
	      + (imageUrl != null ? "imageUrl=" + imageUrl + ", " : "")
	      + (address1 != null ? "address1=" + address1 + ", " : "")
	      + (city != null ? "city=" + city + ", " : "")
	      + (state != null ? "state=" + state + ", " : "")
	      + (country != null ? "country=" + country + ", " : "")
	      + (zip != null ? "zip=" + zip + ", " : "")
	      + (location != null ? "location=" + location + ", " : "")
	      + (externalId != null ? "externalId=" + externalId + ", " : "")
	      + (externalSource != null ? "externalSource=" + externalSource + ", "
	          : "") + (hike != null ? "hike=" + hike + ", " : "")
	      + (details != null ? "details=" + details + ", " : "")
	      + (district != null ? "district=" + district + ", " : "")
	      + (gallery != null ? "gallery=" + gallery + ", " : "")
	      + (rank != null ? "rank=" + rank : "") + "]";
  }
	public String getNicename() {
		return nicename;
	}
	public void setNicename(String nicename) {
		this.nicename = nicename;
	}
	
}
