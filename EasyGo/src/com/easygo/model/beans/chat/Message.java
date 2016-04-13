package com.easygo.model.beans.chat;

import java.sql.Timestamp;

public class Message {
	private int message_id;
	private int message_sender_id;// 发送人ID
	private int message_receiver_id;// 接收者ID
	private Timestamp message_time;// 发送时间
	private String message_content;// 聊天内容
	private String message_image;// 聊天图片地址
	private String message_isread;// 消息状态；01未读，02已读

	public Message() {
		super();
	}

	public Message(int message_id, int message_sender_id,
			int message_receiver_id, Timestamp message_time,
			String message_content, String message_image, String message_isread) {
		super();
		this.message_id = message_id;
		this.message_sender_id = message_sender_id;
		this.message_receiver_id = message_receiver_id;
		this.message_time = message_time;
		this.message_content = message_content;
		this.message_image = message_image;
		this.message_isread = message_isread;
	}

	public int getMessage_id() {
		return message_id;
	}

	public void setMessage_id(int message_id) {
		this.message_id = message_id;
	}

	public int getMessage_sender_id() {
		return message_sender_id;
	}

	public void setMessage_sender_id(int message_sender_id) {
		this.message_sender_id = message_sender_id;
	}

	public int getMessage_receiver_id() {
		return message_receiver_id;
	}

	public void setMessage_receiver_id(int message_receiver_id) {
		this.message_receiver_id = message_receiver_id;
	}

	public Timestamp getMessage_time() {
		return message_time;
	}

	public void setMessage_time(Timestamp message_time) {
		this.message_time = message_time;
	}

	public String getMessage_content() {
		return message_content;
	}

	public void setMessage_content(String message_content) {
		this.message_content = message_content;
	}

	public String getMessage_image() {
		return message_image;
	}

	public void setMessage_image(String message_image) {
		this.message_image = message_image;
	}

	public String getMessage_isread() {
		return message_isread;
	}

	public void setMessage_isread(String message_isread) {
		this.message_isread = message_isread;
	}
}
