package com.easygo.model.beans.house;

public class HousePhoto {
	// 房源图片表（一对多）
	private int house_photo_id;
	private int house_id;
	private String house_photo_path; // 图片地址
	private int isFirst;// 是否为封面，0不是封面，1是封面

	public HousePhoto() {
		super();
	}

	public HousePhoto(int house_photo_id, int house_id,
			String house_photo_path, int isFirst) {
		super();
		this.house_photo_id = house_photo_id;
		this.house_id = house_id;
		this.house_photo_path = house_photo_path;
		this.isFirst = isFirst;
	}

	public int getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(int isFirst) {
		this.isFirst = isFirst;
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

	@Override
	public String toString() {
		return "HousePhoto [house_photo_id=" + house_photo_id + ", house_id="
				+ house_id + ", house_photo_path=" + house_photo_path
				+ ", isFirst=" + isFirst + "]";
	}
	

}
