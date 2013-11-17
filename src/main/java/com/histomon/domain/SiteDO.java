package com.histomon.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="site")
public class SiteDO {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	protected Long id;
	@Column(name = "name", unique = false, nullable = false)
	protected String name;
	@Column(name = "description", nullable = true)
	protected String description;
	@Column(name = "type", nullable = true)
	protected Integer type;
	@Column(name = "image", nullable = true)
	protected String imageUrl;
	@Column(name = "address1", nullable = true)
	protected String address1;
	@Column(name = "district", nullable = true)
	protected String district;
	@Column(name = "city", nullable = true)
	protected String city;
	@Column(name = "state", nullable = true)
	protected String state;
	@Column(name = "country", nullable = true)
	protected String country;
	@Column(name = "zip", nullable = true)
	protected String zip;
	@Column(name = "location", nullable = true)
	protected String location;
	@Column(name="externalid", nullable=true)
	protected String externalid;
	@Column(name="externalsource", nullable=true)
	protected String externalSource;
	@Column(name="rank", nullable=true)
	protected Integer rank;
	@Column(name = "hike", nullable = true)
	protected Integer hike;
	@Column(name="details", nullable=true)
	protected String details;
	@Column(name="gallery", nullable=true)
	protected String gallery;
	@Column(name="nicename", nullable=false)
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
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
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
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
	public String getExternalid() {
		return externalid;
	}
	public void setExternalid(String externalid) {
		this.externalid = externalid;
	}
	public String getExternalSource() {
		return externalSource;
	}
	public void setExternalSource(String externalSource) {
		this.externalSource = externalSource;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public Integer getHike() {
		return hike;
	}
	public void setHike(Integer hike) {
		this.hike = hike;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getGallery() {
		return gallery;
	}
	public void setGallery(String gallery) {
		this.gallery = gallery;
	}
	public String getNicename() {
		return nicename;
	}
	public void setNicename(String nicename) {
		this.nicename = nicename;
	}
}
