package com.easygo.model.beans.chat;
//111222444
public class Friend {
	private int friend_no;// 朋友表ID
	private int user_id;// 用户ID
	private int friend_id;// 好友ID

	public Friend() {
		super();
	}

	public Friend(int friend_no, int user_id, int friend_id) {
		super();
		this.friend_no = friend_no;
		this.user_id = user_id;
		this.friend_id = friend_id;
	}

	public int getFriend_no() {
		return friend_no;
	}

	public void setFriend_no(int friend_no) {
		this.friend_no = friend_no;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getFriend_id() {
		return friend_id;
	}

	public void setFriend_id(int friend_id) {
		this.friend_id = friend_id;
	}
}
