package com.easygo.model.beans.user;

public class Userhobby {
	private int user_hobby_id;
	private int user_id; // 用户ID(外键)
	private String user_hobby_name; // 爱好名称

	public Userhobby() {
		super();
	}

	public Userhobby(int user_hobby_id, int user_id, String user_hobby_name) {
		super();
		this.user_hobby_id = user_hobby_id;
		this.user_id = user_id;
		this.user_hobby_name = user_hobby_name;
	}

	public int getUser_hobby_id() {
		return user_hobby_id;
	}

	public void setUser_hobby_id(int user_hobby_id) {
		this.user_hobby_id = user_hobby_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_hobby_name() {
		return user_hobby_name;
	}

	public void setUser_hobby_name(String user_hobby_name) {
		this.user_hobby_name = user_hobby_name;
	}
	
}
