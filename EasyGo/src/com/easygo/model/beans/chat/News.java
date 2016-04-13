package com.easygo.model.beans.chat;

import java.sql.Timestamp;

public class News {
	private int news_id;// 说说动态id
	private int news_sender_id;// 发送人id
	private String news_content;// 发送内容
	private Timestamp news_time;// 发送时间
	private int news_stars;// 点赞次数
	private int news_views;// 浏览次数
	public News() {
		super();
	}
	public News(int news_id, int news_sender_id, String news_content,
			Timestamp news_time, int news_stars, int news_views) {
		super();
		this.news_id = news_id;
		this.news_sender_id = news_sender_id;
		this.news_content = news_content;
		this.news_time = news_time;
		this.news_stars = news_stars;
		this.news_views = news_views;
	}
	public int getNews_id() {
		return news_id;
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
	public Timestamp getNews_time() {
		return news_time;
	}
	public void setNews_time(Timestamp news_time) {
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
	
}
