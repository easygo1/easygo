package com.easygo.model.beans.user;

public class Userhobby {
	private int user_id;
	private String hobby_id;
	public Userhobby() {
		super();
	}
	public Userhobby(int user_id, String hobby_id) {
		super();
		this.user_id = user_id;
		this.hobby_id = hobby_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getHobby_id() {
		return hobby_id;
	}
	public void setHobby_id(String hobby_id) {
		this.hobby_id = hobby_id;
	}
}
