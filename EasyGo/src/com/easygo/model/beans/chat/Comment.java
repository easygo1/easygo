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

	public Comment(int comment_id, int comment_news_id, int comment_user_id,
			String comment_content, Timestamp comment_time) {
		super();
		this.comment_id = comment_id;
		this.comment_news_id = comment_news_id;
		this.comment_user_id = comment_user_id;
		this.comment_content = comment_content;
		this.comment_time = comment_time;
	}

	public int getComment_id() {
		return comment_id;
	}

	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}

	public int getComment_news_id() {
		return comment_news_id;
	}

	public void setComment_news_id(int comment_news_id) {
		this.comment_news_id = comment_news_id;
	}

	public int getComment_user_id() {
		return comment_user_id;
	}

	public void setComment_user_id(int comment_user_id) {
		this.comment_user_id = comment_user_id;
	}

	public String getComment_content() {
		return comment_content;
	}

	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}

	public Timestamp getComment_time() {
		return comment_time;
	}

	public void setComment_time(Timestamp comment_time) {
		this.comment_time = comment_time;
	}
}
