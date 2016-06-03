package com.easygo.model.beans.chat;

public class Friend {
	private int friend_no;// 朋友表ID
	private int user_id1;// 用户ID
	private int user_id2;// 好友ID

	public Friend() {
		super();
	}

	public Friend(int user_id1, int user_id2) {
		super();
		this.user_id1 = user_id1;
		this.user_id2 = user_id2;
	}

	public int getFriend_no() {
		return friend_no;
	}

	public void setFriend_no(int friend_no) {
		this.friend_no = friend_no;
	}

	public int getUser_id1() {
		return user_id1;
	}

	public void setUser_id1(int user_id1) {
		this.user_id1 = user_id1;
	}

	public int getUser_id2() {
		return user_id2;
	}

	public void setUser_id2(int user_id2) {
		this.user_id2 = user_id2;
	}
	
}
