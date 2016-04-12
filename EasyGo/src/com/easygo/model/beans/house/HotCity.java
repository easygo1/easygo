package com.easygo.model.beans.house;

public class HotCity {
	private int hot_city_id;
	private String hot_city_name; // 热门城市名
	private int hot_city_num; // 热门城市订单完成次数

	public HotCity() {
		super();
	}

	public HotCity(int hot_city_id, String hot_city_name, int hot_city_num) {
		super();
		this.hot_city_id = hot_city_id;
		this.hot_city_name = hot_city_name;
		this.hot_city_num = hot_city_num;
	}
}
