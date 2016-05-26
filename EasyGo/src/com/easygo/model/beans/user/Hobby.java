package com.easygo.model.beans.user;

public class Hobby {
	private int hobby_id;
	private String hobby_name; // 爱好名称
	public Hobby() {
		super();
	}
	public Hobby(int hobby_id, String hobby_name) {
		super();
		this.hobby_id = hobby_id;
		this.hobby_name = hobby_name;
	}
	public int getHobby_id() {
		return hobby_id;
	}
	public void setHobby_id(int hobby_id) {
		this.hobby_id = hobby_id;
	}
	public String getHobby_name() {
		return hobby_name;
	}
	public void setHobby_name(String hobby_name) {
		this.hobby_name = hobby_name;
	}
	
	
}
