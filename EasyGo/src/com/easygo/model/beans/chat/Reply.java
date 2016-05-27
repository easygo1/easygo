package com.easygo.model.beans.chat;

import java.sql.Timestamp;

public class Reply {

	private int reply_id;// 回复表id
	private int reply_comment_id;// 说评论id
	private int reply_user_id;// 回复者id
	private int reply_respondent_id;// 被回复者id
	private String reply_content;// 回复内容
	private Timestamp reply_time;// 回复时间
	public Reply() {
		super();
	}
	public Reply(int reply_id, int reply_comment_id, int reply_user_id,
			int reply_respondent_id, String reply_content, Timestamp reply_time) {
		super();
		this.reply_id = reply_id;
		this.reply_comment_id = reply_comment_id;
		this.reply_user_id = reply_user_id;
		this.reply_respondent_id = reply_respondent_id;
		this.reply_content = reply_content;
		this.reply_time = reply_time;
	}
	public int getReply_id() {
		return reply_id;
	}
	public void setReply_id(int reply_id) {
		this.reply_id = reply_id;
	}
	public int getReply_comment_id() {
		return reply_comment_id;
	}
	public void setReply_comment_id(int reply_comment_id) {
		this.reply_comment_id = reply_comment_id;
	}
	public int getReply_user_id() {
		return reply_user_id;
	}
	public void setReply_user_id(int reply_user_id) {
		this.reply_user_id = reply_user_id;
	}
	public int getReply_respondent_id() {
		return reply_respondent_id;
	}
	public void setReply_respondent_id(int reply_respondent_id) {
		this.reply_respondent_id = reply_respondent_id;
	}
	public String getReply_content() {
		return reply_content;
	}
	public void setReply_content(String reply_content) {
		this.reply_content = reply_content;
	}
	public Timestamp getReply_time() {
		return reply_time;
	}
	public void setReply_time(Timestamp reply_time) {
		this.reply_time = reply_time;
	}
	
}
