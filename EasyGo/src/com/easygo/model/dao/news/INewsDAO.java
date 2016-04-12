package com.easygo.model.dao.news;

import java.util.List;

import com.easygo.model.beans.chat.News;
import com.easygo.model.beans.house.House;

public interface INewsDAO {
	/**
	 * 对说说进行增删改查
	 */
	// 发表说说（增加说说）
	public abstract boolean addNews(News news);
	// 删除说说
	public abstract boolean delNews(int news_sender_id);
	// 查找说说（某个用户的）
	public abstract News findSpecNewsById(int news_sender_id);

}
