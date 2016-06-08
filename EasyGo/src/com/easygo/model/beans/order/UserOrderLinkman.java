package com.easygo.model.beans.order;

//订单的订单入住人
public class UserOrderLinkman {
	private int user_linkman_id; // 表编号
	private int order_id; // 订单编号
	private String name;
	private String idcard;

	public UserOrderLinkman() {
		super();
	}

	public UserOrderLinkman(int order_id, String name, String idcard) {
		super();
		this.order_id = order_id;
		this.name = name;
		this.idcard = idcard;
	}

	public UserOrderLinkman(int user_linkman_id, int order_id, String name,
			String idcard) {
		super();
		this.user_linkman_id = user_linkman_id;
		this.order_id = order_id;
		this.name = name;
		this.idcard = idcard;
	}

	public int getUser_linkman_id() {
		return user_linkman_id;
	}

	public void setUser_linkman_id(int user_linkman_id) {
		this.user_linkman_id = user_linkman_id;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	@Override
	public String toString() {
		return "UserOrderLinkman [user_linkman_id=" + user_linkman_id
				+ ", order_id=" + order_id + ", name=" + name + ", idcard="
				+ idcard + "]";
	}

}
