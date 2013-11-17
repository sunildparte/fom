package com.histomon.api;

public enum SiteTypeEnum {
	FORT ( 1, "fort"), WARSITE(2, "warsite"), BUILDING (3, "building"),OTHERS(99,"others");
	
	private Integer id;
	private String name;
	private SiteTypeEnum ( Integer id, String name ) {
		this.id = id;
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}

	public static final SiteTypeEnum getById(int i) {
		for (SiteTypeEnum type : SiteTypeEnum.values()) {
			if (type.id == i) {
				return type;
			}
		}
		return null;
	}
	
	public static final SiteTypeEnum getByName(String name) {
		for (SiteTypeEnum type : SiteTypeEnum.values()) {
			if (type.name.equals(name)) {
				return type;
			}
		}
		return null;
	}
}
