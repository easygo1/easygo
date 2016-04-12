package com.easygo.model.beans.house;

public class House {
	private int house_id;// 房子编号 主键
	private int user_id; // 房东用户编号 外键
	private String house_title; // 房源标题(0-10字)
	private String house_describe;// 房屋描述（100字）
	private String house_style; // 房源类型（客厅沙发，独立房间）
	private String house_address; // 房源地址（？？）
	private String house_traffic; // 交通信息
	private int house_most_num; // 最多入住人数
	private double house_one_price; // 价格（1人）
	private double house_add_price; // 每多一人的价格
	private String house_limit_sex; // 房客性别要求（不限，只男，只女）
	private int house_stay_time; // 最长入住时间
	private int house_assess_sum; // 评价次数（在房东发布房源时不显示）

	public House() {
		super();
	}

	public House(int house_id, int user_id, String house_title,
			String house_describe, String house_style, String house_address,
			String house_traffic, int house_most_num, double house_one_price,
			double house_add_price, String house_limit_sex,
			int house_stay_time, int house_assess_sum) {
		super();
		this.house_id = house_id;
		this.user_id = user_id;
		this.house_title = house_title;
		this.house_describe = house_describe;
		this.house_style = house_style;
		this.house_address = house_address;
		this.house_traffic = house_traffic;
		this.house_most_num = house_most_num;
		this.house_one_price = house_one_price;
		this.house_add_price = house_add_price;
		this.house_limit_sex = house_limit_sex;
		this.house_stay_time = house_stay_time;
		this.house_assess_sum = house_assess_sum;
	}

}
