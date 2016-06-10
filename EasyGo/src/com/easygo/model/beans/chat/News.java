package com.easygo.model.beans.chat;

import java.sql.Timestamp;
import java.util.List;

public class News {
	private int news_id;// 说说动态id
	private int news_sender_id;// 发送人id
	private String user_photo;//发送人的头像地址
	private String user_nickname;//发送人的昵称
	private String news_content;// 发送内容
	private String news_time;// 发送时间
	private int news_stars;// 点赞次数
	private int news_views;// 浏览次数
	private List<String> photo_path;
	public News() {
		super();
	}
	//接收发表说说时的构造函数
	public News(int news_sender_id, String news_content, List<String> photo_path) {
		super();
		this.news_sender_id = news_sender_id;
		this.news_content = news_content;
		this.photo_path = photo_path;
	}
	//展现说说说时的构造函数
	public News(int news_id, String user_photo, String user_nickname,
			String news_content, String news_time, int news_stars,
			int news_views, List<String> photo_path) {
		super();
		this.news_id = news_id;
		this.user_photo = user_photo;
		this.user_nickname = user_nickname;
		this.news_content = news_content;
		this.news_time = news_time;
		this.news_stars = news_stars;
		this.news_views = news_views;
		this.photo_path = photo_path;
	}
	
	
	public int getNews_id() {
		return news_id;
	}
	
	public String getUser_photo() {
		return user_photo;
	}
	public void setUser_photo(String user_photo) {
		this.user_photo = user_photo;
	}
	public String getUser_nickname() {
		return user_nickname;
	}
	public void setUser_nickname(String user_nickname) {
		this.user_nickname = user_nickname;
	}
	public void setNews_id(int news_id) {
		this.news_id = news_id;
	}
	public int getNews_sender_id() {
		return news_sender_id;
	}
	public void setNews_sender_id(int news_sender_id) {
		this.news_sender_id = news_sender_id;
	}
	public String getNews_content() {
		return news_content;
	}
	public void setNews_content(String news_content) {
		this.news_content = news_content;
	}
	public String getNews_time() {
		return news_time;
	}
	public void setNews_time(String news_time) {
		this.news_time = news_time;
	}
	public int getNews_stars() {
		return news_stars;
	}
	public void setNews_stars(int news_stars) {
		this.news_stars = news_stars;
	}
	public int getNews_views() {
		return news_views;
	}
	public void setNews_views(int news_views) {
		this.news_views = news_views;
	}

	public List<String> getPhoto_path() {
		return photo_path;
	}

	public void setPhoto_path(List<String> photo_path) {
		this.photo_path = photo_path;
	}
	@Override
	public String toString() {
		return "News [user_photo=" + user_photo
				+ ", user_nickname=" + user_nickname + ", news_content="
				+ news_content + ", news_time=" + news_time + ", news_stars="
				+ news_stars + ", news_views=" + news_views + ", photo_path="
				+ photo_path + "]";
	}
	
	
}
