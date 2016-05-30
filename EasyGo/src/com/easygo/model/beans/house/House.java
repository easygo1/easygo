package com.easygo.model.beans.house;

public class House {
	private int house_id;// 房子编号 主键
	private int user_id; // 房东用户编号 外键
	private String house_title; // 房源标题(0-10字)
	private String house_describe;// 房屋描述（100字）
	private String house_style; // 房源类型（客厅沙发，独立房间）
	private String house_address_province; //所在省份
	private String house_address_city; //所在城市
	private double house_address_lng; // 房源地址（经度）
	private double house_address_lat; // 房源地址（纬度）
	private String house_traffic; // 交通信息
	private int house_most_num; // 最多入住人数
	private double house_one_price; // 价格（1人）
	private double house_add_price; // 每多一人的价格
	private String house_limit_sex; // 房客性别要求（不限，只男，只女）
	private int house_stay_time; // 最长入住时间
	private int house_assess_sum; // 评价次数（在房东发布房源时不显示）
	
	public House() {
		super();
		// TODO Auto-generated constructor stub
	}

	public House(int house_id, int user_id, String house_title,
			String house_describe, String house_style,
			String house_address_province, String house_address_city,
			double house_address_lng, double house_address_lat,
			String house_traffic, int house_most_num, double house_one_price,
			double house_add_price, String house_limit_sex,
			int house_stay_time, int house_assess_sum) {
		super();
		this.house_id = house_id;
		this.user_id = user_id;
		this.house_title = house_title;
		this.house_describe = house_describe;
		this.house_style = house_style;
		this.house_address_province = house_address_province;
		this.house_address_city = house_address_city;
		this.house_address_lng = house_address_lng;
		this.house_address_lat = house_address_lat;
		this.house_traffic = house_traffic;
		this.house_most_num = house_most_num;
		this.house_one_price = house_one_price;
		this.house_add_price = house_add_price;
		this.house_limit_sex = house_limit_sex;
		this.house_stay_time = house_stay_time;
		this.house_assess_sum = house_assess_sum;
	}

	public int getHouse_id() {
		return house_id;
	}

	public void setHouse_id(int house_id) {
		this.house_id = house_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getHouse_title() {
		return house_title;
	}

	public void setHouse_title(String house_title) {
		this.house_title = house_title;
	}

	public String getHouse_describe() {
		return house_describe;
	}

	public void setHouse_describe(String house_describe) {
		this.house_describe = house_describe;
	}

	public String getHouse_style() {
		return house_style;
	}

	public void setHouse_style(String house_style) {
		this.house_style = house_style;
	}

	public String getHouse_address_province() {
		return house_address_province;
	}

	public void setHouse_address_province(String house_address_province) {
		this.house_address_province = house_address_province;
	}

	public String getHouse_address_city() {
		return house_address_city;
	}

	public void setHouse_address_city(String house_address_city) {
		this.house_address_city = house_address_city;
	}

	public double getHouse_address_lng() {
		return house_address_lng;
	}

	public void setHouse_address_lng(double house_address_lng) {
		this.house_address_lng = house_address_lng;
	}

	public double getHouse_address_lat() {
		return house_address_lat;
	}

	public void setHouse_address_lat(double house_address_lat) {
		this.house_address_lat = house_address_lat;
	}

	public String getHouse_traffic() {
		return house_traffic;
	}

	public void setHouse_traffic(String house_traffic) {
		this.house_traffic = house_traffic;
	}

	public int getHouse_most_num() {
		return house_most_num;
	}

	public void setHouse_most_num(int house_most_num) {
		this.house_most_num = house_most_num;
	}

	public double getHouse_one_price() {
		return house_one_price;
	}

	public void setHouse_one_price(double house_one_price) {
		this.house_one_price = house_one_price;
	}

	public double getHouse_add_price() {
		return house_add_price;
	}

	public void setHouse_add_price(double house_add_price) {
		this.house_add_price = house_add_price;
	}

	public String getHouse_limit_sex() {
		return house_limit_sex;
	}

	public void setHouse_limit_sex(String house_limit_sex) {
		this.house_limit_sex = house_limit_sex;
	}

	public int getHouse_stay_time() {
		return house_stay_time;
	}

	public void setHouse_stay_time(int house_stay_time) {
		this.house_stay_time = house_stay_time;
	}

	public int getHouse_assess_sum() {
		return house_assess_sum;
	}

	public void setHouse_assess_sum(int house_assess_sum) {
		this.house_assess_sum = house_assess_sum;
	}
	
	
	

}
