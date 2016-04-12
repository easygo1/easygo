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
}
