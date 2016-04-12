package com.easygo.model.beans;

public class Admin {
	private int admin_id;// 管理员账号
	private String admin_name; // 管理员名字
	private String admin_password; // 管理员密码

	public Admin() {
		super();

	}

	public Admin(int admin_id, String admin_name, String admin_password) {
		super();
		this.admin_id = admin_id;
		this.admin_name = admin_name;
		this.admin_password = admin_password;
	}
}
