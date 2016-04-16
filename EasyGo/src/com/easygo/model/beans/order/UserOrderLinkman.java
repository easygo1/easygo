package com.easygo.model.beans.order;

public class UserOrderLinkman {
	private int user_linkman_id; // 表编号
	private int order_id; // 订单编号
	private String name;
	private String idcard;

	public UserOrderLinkman() {
		super();
	}

	public UserOrderLinkman(int user_linkman_id, int order_id, String name,
			String idcard) {
		super();
		this.user_linkman_id = user_linkman_id;
		this.order_id = order_id;
		this.name = name;
		this.idcard = idcard;
	}
}
