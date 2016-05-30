package com.easygo.model.beans.order;

public class Assess {
	private int assess_id; // 无意义
	private int order_id; // 订单id（外键）
	private int house_id;// 房屋编号
	private int user_id; // 用户id（外键）(房客，评价人的id)
	private int star; // 星级
	private String assess_content; // 评价内容

	public Assess() {
		super();
	}

	public Assess(int assess_id, int order_id, int house_id, int user_id,
			int star, String assess_content) {
		super();
		this.assess_id = assess_id;
		this.order_id = order_id;
		this.house_id = house_id;
		this.user_id = user_id;
		this.star = star;
		this.assess_content = assess_content;
	}

	public int getAssess_id() {
		return assess_id;
	}

	public void setAssess_id(int assess_id) {
		this.assess_id = assess_id;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
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

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public String getAssess_content() {
		return assess_content;
	}

	public void setAssess_content(String assess_content) {
		this.assess_content = assess_content;
	}

}
