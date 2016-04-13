package com.easygo.model.beans;

public class SystemMessage {
	private int system_message_id;
	private int user_id;// 系统信息接收者
	private String system_message_content;// 系统发送的内容
	private int system_message_linkorder;// 顾客购买的订单
	private int system_message_linkuser;// 请求加为好友的用户
	public SystemMessage() {
		super();
	}
	public SystemMessage(int system_message_id, int user_id,
			String system_message_content, int system_message_linkorder,
			int system_message_linkuser) {
		super();
		this.system_message_id = system_message_id;
		this.user_id = user_id;
		this.system_message_content = system_message_content;
		this.system_message_linkorder = system_message_linkorder;
		this.system_message_linkuser = system_message_linkuser;
	}
	public int getSystem_message_id() {
		return system_message_id;
	}
	public void setSystem_message_id(int system_message_id) {
		this.system_message_id = system_message_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getSystem_message_content() {
		return system_message_content;
	}
	public void setSystem_message_content(String system_message_content) {
		this.system_message_content = system_message_content;
	}
	public int getSystem_message_linkorder() {
		return system_message_linkorder;
	}
	public void setSystem_message_linkorder(int system_message_linkorder) {
		this.system_message_linkorder = system_message_linkorder;
	}
	public int getSystem_message_linkuser() {
		return system_message_linkuser;
	}
	public void setSystem_message_linkuser(int system_message_linkuser) {
		this.system_message_linkuser = system_message_linkuser;
	}

}
