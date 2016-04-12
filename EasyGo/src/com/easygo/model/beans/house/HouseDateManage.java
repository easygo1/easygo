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
}
