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

	public Userwallet(int user_wallet_id, int user_id, int alipay_no,
			int user_wallet_score, double user_wallet_money) {
		super();
		this.user_wallet_id = user_wallet_id;
		this.user_id = user_id;
		this.alipay_no = alipay_no;
		this.user_wallet_score = user_wallet_score;
		this.user_wallet_money = user_wallet_money;
	}

	public int getUser_wallet_id() {
		return user_wallet_id;
	}

	public void setUser_wallet_id(int user_wallet_id) {
		this.user_wallet_id = user_wallet_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getAlipay_no() {
		return alipay_no;
	}

	public void setAlipay_no(int alipay_no) {
		this.alipay_no = alipay_no;
	}

	public int getUser_wallet_score() {
		return user_wallet_score;
	}

	public void setUser_wallet_score(int user_wallet_score) {
		this.user_wallet_score = user_wallet_score;
	}

	public double getUser_wallet_money() {
		return user_wallet_money;
	}

	public void setUser_wallet_money(double user_wallet_money) {
		this.user_wallet_money = user_wallet_money;
	}
	
}
