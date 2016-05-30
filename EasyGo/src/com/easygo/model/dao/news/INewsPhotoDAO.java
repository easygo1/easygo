package com.easygo.model.dao.news;

import com.easygo.model.beans.chat.NewsPhoto;
import com.easygo.model.beans.house.House;

public interface INewsPhotoDAO {
	/**
	 * 对说说的图片进行增删
	 */
	// 增加图片
	public abstract boolean addNewsPhoto(NewsPhoto newsPhoto);
	// 删除说说
	public abstract boolean delNewsPhoto(int news_photo_id);
}
