package com.easygo.model.impl.news;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.easygo.model.beans.chat.NewsPhoto;
import com.easygo.model.dao.news.INewsPhotoDAO;
import com.easygo.utils.C3P0Utils;

public class INewsPhotoDAOImpl implements INewsPhotoDAO {

	private Connection connection = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;

	@Override
	public boolean addNewsPhoto(int news_id,String path) {
		// TODO Auto-generated method stub
		boolean result = false;
		connection = C3P0Utils.getConnection();
		// 添加动态图片
		String sql = "insert into news_photo(news_id,news_path) values(?,?)";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, news_id);
			statement.setString(2, path);
			statement.executeUpdate();
			result = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
		return result;
	}

	@Override
	public boolean delNewsPhoto(int news_photo_id) {
		// TODO Auto-generated method stub
		return false;
	}

}
