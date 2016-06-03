package com.easygo.beans.gson;

import com.easygo.beans.user.User;

import java.io.Serializable;
import java.util.List;


public class GsonUserInfoHobby implements Serializable{

	/**
	 * //根据用户id得到user表信息 userhobby中的信息
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	private List<String> userHobbyNamelist;
	public GsonUserInfoHobby() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GsonUserInfoHobby(User user, List<String> userHobbyNamelist) {
		super();
		this.user = user;
		this.userHobbyNamelist = userHobbyNamelist;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<String> getUserHobbyNamelist() {
		return userHobbyNamelist;
	}
	public void setUserHobbyNamelist(List<String> userHobbyNamelist) {
		this.userHobbyNamelist = userHobbyNamelist;
	}
	@Override
	public String toString() {
		return "GsonUserInfoHobby [user=" + user + ", userHobbyNamelist="
				+ userHobbyNamelist + "]";
	}
	

}
