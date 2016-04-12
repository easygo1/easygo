package com.easygo.model.beans.chat;

import java.sql.Timestamp;

public class Comment {
	private int comment_id; // 评论表id
	private int comment_news_id; // 说说动态id
	private int comment_user_id; // 评论人id
	private String comment_content;// 评论内容
	private Timestamp comment_time;// 评论时间

	public Comment() {
		super();
	}
}
