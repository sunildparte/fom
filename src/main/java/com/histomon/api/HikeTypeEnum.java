package com.histomon.api;

public enum HikeTypeEnum {
	EASY(0, "Easy"), MEDIUM(1, "Medium"), DIFFICULT(3, "Difficult"), NO_HIKE_ROUTE(9, "No hiking route");
	private int id;
	private String name;
	private HikeTypeEnum( int id, String name ) {
		this.id = id;
		this.name = name;
	}
	
	public String getName() { return name; }
	public int getId() { return id; }
	
	public static final HikeTypeEnum getById(int i) {
		for (HikeTypeEnum type : HikeTypeEnum.values()) {
			if (type.id == i) {
				return type;
			}
		}
		return null;
	}
	
	public static final HikeTypeEnum getByName(String name) {
		for (HikeTypeEnum type : HikeTypeEnum.values()) {
			if (type.name.equals(name)) {
				return type;
			}
		}
		return null;
	}
}
