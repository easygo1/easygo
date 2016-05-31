package com.easygo.model.impl.user;

public class Mine {
	private int code;
	private String userId;
	private String token;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Override
	public String toString() {
		return "Mine [code=" + code + ", userId=" + userId + ", token=" + token
				+ "]";
	}
	
	
}
