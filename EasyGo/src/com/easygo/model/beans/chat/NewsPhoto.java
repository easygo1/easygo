package com.easygo.model.beans.chat;

public class NewsPhoto {
	// 说说图片
	private int news_photo_id;// 图片id
	private int news_id;// 所属的说说id
	private String news_path;// 图片路径
	public NewsPhoto() {
		super();
	}
	public NewsPhoto(int news_photo_id, int news_id, String news_path) {
		super();
		this.news_photo_id = news_photo_id;
		this.news_id = news_id;
		this.news_path = news_path;
	}
	public int getNews_photo_id() {
		return news_photo_id;
	}
	public void setNews_photo_id(int news_photo_id) {
		this.news_photo_id = news_photo_id;
	}
	public int getNews_id() {
		return news_id;
	}
	public void setNews_id(int news_id) {
		this.news_id = news_id;
	}
	public String getNews_path() {
		return news_path;
	}
	public void setNews_path(String news_path) {
		this.news_path = news_path;
	}
	
}
