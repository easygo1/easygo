package com.easygo.model.beans.chat;

import java.sql.Timestamp;

public class News {
	private int news_id;// 说说动态id
	private int news_sender_id;// 发送人id
	private String news_content;// 发送内容
	private Timestamp news_time;// 发送时间
	private int news_stars;// 点赞次数
	private int news_views;// ？？
}
