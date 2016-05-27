package com.easygo.model.beans.house;

import java.sql.Date;

public class HouseDateManage {
	// 房源时间管理
	private int date_manage_id;// 无意义
	private int house_id; // 房源id（外键）
	private Date date_not_use; // 不可租日期，精确到天

	public HouseDateManage() {
		super();
	}

	public HouseDateManage(int date_manage_id, int house_id, Date date_not_use) {
		super();
		this.date_manage_id = date_manage_id;
		this.house_id = house_id;
		this.date_not_use = date_not_use;
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

	public Date getDate_not_use() {
		return date_not_use;
	}

	public void setDate_not_use(Date date_not_use) {
		this.date_not_use = date_not_use;
	}
	
}
