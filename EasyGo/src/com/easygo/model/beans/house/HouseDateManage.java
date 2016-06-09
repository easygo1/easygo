package com.easygo.model.beans.house;

public class HouseDateManage {
	// 房源时间管理
	private int date_manage_id;// 无意义
	private int house_id; // 房源id（外键）
	private String date_not_use; // 不可租日期，精确到天
	private int date_manage_type;// 1代表房客租用了，2代表房东自己设置为不可租

	public HouseDateManage() {
		super();
	}

	public HouseDateManage(int date_manage_id, int house_id,
			String date_not_use, int date_manage_type) {
		super();
		this.date_manage_id = date_manage_id;
		this.house_id = house_id;
		this.date_not_use = date_not_use;
		this.date_manage_type = date_manage_type;
	}

	public int getDate_manage_type() {
		return date_manage_type;
	}

	public void setDate_manage_type(int date_manage_type) {
		this.date_manage_type = date_manage_type;
	}

	public int getDate_manage_id() {
		return date_manage_id;
	}

	public void setDate_manage_id(int date_manage_id) {
		this.date_manage_id = date_manage_id;
	}

	public int getHouse_id() {
		return house_id;
	}

	public void setHouse_id(int house_id) {
		this.house_id = house_id;
	}

	public String getDate_not_use() {
		return date_not_use;
	}

	public void setDate_not_use(String date_not_use) {
		this.date_not_use = date_not_use;
	}

}
