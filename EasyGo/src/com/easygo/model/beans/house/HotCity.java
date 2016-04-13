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

	public int getHot_city_id() {
		return hot_city_id;
	}

	public void setHot_city_id(int hot_city_id) {
		this.hot_city_id = hot_city_id;
	}

	public String getHot_city_name() {
		return hot_city_name;
	}

	public void setHot_city_name(String hot_city_name) {
		this.hot_city_name = hot_city_name;
	}

	public int getHot_city_num() {
		return hot_city_num;
	}

	public void setHot_city_num(int hot_city_num) {
		this.hot_city_num = hot_city_num;
	}
}
