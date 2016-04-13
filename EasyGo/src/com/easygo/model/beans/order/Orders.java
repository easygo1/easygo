package com.easygo.model.beans.order;

import java.sql.Date;

public class Orders {
	private int order_id; // 订单编号
	private int house_id; // 房屋编号 外键
	private int user_id; // 房客编号 外键
	private int checknum; // 入住人数
	private String checkname; // 入住人姓名
	private Date checktime; // 入住时间（计算出天数）
	private Date leavetime; // 离开时间
	private double total; // 订单总额（总价=天数*单价）
	private String tel; // 联系方式
	private String order_state; // 订单状态 #待确认，待付款，待入住，已完成，取消订单

	public Orders() {
		super();
	}

	public Orders(int order_id, int house_id, int user_id, int checknum,
			String checkname, Date checktime, Date leavetime, double total,
			String tel, String order_state) {
		super();
		this.order_id = order_id;
		this.house_id = house_id;
		this.user_id = user_id;
		this.checknum = checknum;
		this.checkname = checkname;
		this.checktime = checktime;
		this.leavetime = leavetime;
		this.total = total;
		this.tel = tel;
		this.order_state = order_state;
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

	public int getChecknum() {
		return checknum;
	}

	public void setChecknum(int checknum) {
		this.checknum = checknum;
	}

	public String getCheckname() {
		return checkname;
	}

	public void setCheckname(String checkname) {
		this.checkname = checkname;
	}

	public Date getChecktime() {
		return checktime;
	}

	public void setChecktime(Date checktime) {
		this.checktime = checktime;
	}

	public Date getLeavetime() {
		return leavetime;
	}

	public void setLeavetime(Date leavetime) {
		this.leavetime = leavetime;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getOrder_state() {
		return order_state;
	}

	public void setOrder_state(String order_state) {
		this.order_state = order_state;
	}
	
}
