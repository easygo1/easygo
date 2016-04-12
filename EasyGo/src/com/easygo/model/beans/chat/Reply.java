package com.easygo.model.beans.chat;

import java.sql.Timestamp;

public class Reply {

	private int reply_id;// 回复表id
	private int reply_comment_id;// 说评论id
	private int reply_user_id;// 回复者id
	private int reply_respondent_id;// 被回复者id
	private String reply_content;// 回复内容
	private Timestamp reply_time;// 回复时间
}
