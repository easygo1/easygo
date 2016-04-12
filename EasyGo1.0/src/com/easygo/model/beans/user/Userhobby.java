package com.easygo.model.beans.user;

public class Userhobby {
	private int user_hobby_id;
	private int user_id; // 用户ID(外键)
	private String user_hobby_name; // 爱好名称

	public Userhobby() {
		super();
	}
}
