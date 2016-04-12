package com.easygo.model.beans.user;

import java.sql.Date;

public class User {
	// 用户
	private int user_id; // 用户ID
	private String user_no; // 用户账号（唯一）
	private String user_realname; // 用户真实姓名
	private String user_password; // 用户密码
	private String user_nickname; // 用户昵称
	private String user_sex; // 性别'男'，'女'
	private String user_phone; // 手机号11位
	private int user_type; // 用户类型1'房东',2'房客'
	private String user_photo; // 头像//存地址
	private String user_job; // 职业
	private String user_address_province; // 所在省份
	private String user_address_city; // 所在城市
	private String user_mood; // 个性签名15字内
	private String user_mail; // 邮箱
	private String user_introduct; // 个人简介
	private Date user_birthday; // 出生日期
	private String user_idcard; // 身份证号

	public User() {
		super();
	}
}
