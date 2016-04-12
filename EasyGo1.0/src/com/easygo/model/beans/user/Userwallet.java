package com.easygo.model.beans.user;

public class Userwallet {
	private int user_wallet_id; // 钱包id
	private int user_id; // 用户id 外键
	private int alipay_no; // 支付宝号
	private int user_wallet_score; // 积分
	private double user_wallet_money; // 钱包余额

	public Userwallet() {
		super();
	}
}
