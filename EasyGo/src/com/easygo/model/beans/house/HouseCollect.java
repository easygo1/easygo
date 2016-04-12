package com.easygo.model.beans.house;

public class HouseCollect {
	private int house_collect_id;// 无意义
	private int user_id; // 用户ID
	private int house_id; // 收藏房源ID

	public HouseCollect() {
		super();
	}

	public HouseCollect(int house_collect_id, int user_id, int house_id) {
		super();
		this.house_collect_id = house_collect_id;
		this.user_id = user_id;
		this.house_id = house_id;
	}
}
