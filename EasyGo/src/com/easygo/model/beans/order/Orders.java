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
}
