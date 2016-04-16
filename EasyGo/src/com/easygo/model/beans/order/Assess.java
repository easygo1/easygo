package com.easygo.model.beans.order;

public class Assess {
	private int assess_id; // 无意义
	private int order_id; // 订单id（外键）
	private int user_id; // 用户id（外键）(房客，评价人的id)
	private int star; // 星级
	private String assess_content; // 评价内容

	public Assess() {
		super();
	}

	public Assess(int assess_id, int order_id, int user_id, int star,
			String assess_content) {
		super();
		this.assess_id = assess_id;
		this.order_id = order_id;
		this.user_id = user_id;
		this.star = star;
		this.assess_content = assess_content;
	}
}
