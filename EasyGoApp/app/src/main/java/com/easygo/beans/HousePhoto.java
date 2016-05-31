package com.easygo.beans;

public class HousePhoto {
	// 房源图片表（一对多）
	private int house_photo_id;
	private int house_id;
	private String house_photo_path; // 图片地址
	
	public HousePhoto() {
		super();
	}

	public HousePhoto(int house_photo_id, int house_id, String house_photo_path) {
		super();
		this.house_photo_id = house_photo_id;
		this.house_id = house_id;
		this.house_photo_path = house_photo_path;
	}

	public int getHouse_photo_id() {
		return house_photo_id;
	}

	public void setHouse_photo_id(int house_photo_id) {
		this.house_photo_id = house_photo_id;
	}

	public int getHouse_id() {
		return house_id;
	}

	public void setHouse_id(int house_id) {
		this.house_id = house_id;
	}

	public String getHouse_photo_path() {
		return house_photo_path;
	}

	public void setHouse_photo_path(String house_photo_path) {
		this.house_photo_path = house_photo_path;
	}
	
}
