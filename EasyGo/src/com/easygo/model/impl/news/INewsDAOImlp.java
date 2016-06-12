package com.easygo.model.impl.news;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.easygo.model.beans.chat.News;
import com.easygo.model.beans.chat.NewsPhoto;
import com.easygo.model.dao.news.INewsDAO;
import com.easygo.model.dao.news.INewsPhotoDAO;
import com.easygo.utils.C3P0Utils;
import com.mysql.jdbc.Statement;

public class INewsDAOImlp implements INewsDAO {

	private Connection connection = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;
	private ResultSet resultSet1 = null;
	private ResultSet resultSet2 = null;
	private ResultSet resultSet3 = null;

	News news = null;
	INewsPhotoDAO inewphotodao;
	List<String> news_photos;
	List<News> newslist;
	@Override
	public boolean addNews(News news) {
		inewphotodao = new INewsPhotoDAOImpl();
		// TODO Auto-generated method stub
		boolean result = false;
		// 插入说说时自动生成的id
		int id = 0;
		connection = C3P0Utils.getConnection();
		String sql = "INSERT INTO news(news_sender_id,news_content,news_time) values(?,?,now());";
		try {
			// 可以获取到数据
			statement = connection.prepareStatement(sql);
			statement.setInt(1, news.getNews_sender_id());
			statement.setString(2, news.getNews_content());
			statement.executeUpdate();
			// 获取到刚插入（最后一行）id的数据
			resultSet = statement.executeQuery("SELECT LAST_INSERT_ID()");
			if (resultSet.next()) {
				// 获取到当前插入数据时的主键
				id = resultSet.getInt(1);
			}

			for (int i = 0; news.getPhoto_path().size() > i; i++) {
				// 向数据库插入图片
				System.out.println("向数据库中插入第" +i+"条数据"+ news.getPhoto_path().get(i));
				inewphotodao.addNewsPhoto(id, news.getPhoto_path().get(i));
			}

			System.out.println("动态发表成功！");
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
	public boolean delNews(int news_sender_id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public News findSpecNewsById(int news_sender_id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<News> findSpecNews() {
		// TODO Auto-generated method stub
		/***
		 * 1.查找出news表中news_id和news_sender_id(一条查询语句返回两个数据)
		 * 2.拿到这些数据对news_photo表和user表进行查询，返回动态图片地址，昵称或者手机号，头像
		 * 3.将以上查找好的数据存入动态的javabean中	
		 */
		News news=null;
		newslist=new ArrayList<>(); 
		connection = C3P0Utils.getConnection();
		//1.查找出news表中news_id和news_sender_id(一条查询语句返回两个数据)
		String sql1 = "SELECT * FROM news order by news_id desc;";
		//2.拿到这些数据对news_photo表和，返回动态图片地址，
		String sql2 = "SELECT news_path FROM news_photo WHERE news_id=?;";
		//3.user表进行查询，昵称或者手机号，头像
		String sql3 = "SELECT user_nickname,user_photo FROM USER WHERE user_id=?;";
		//4.执行这些对数据库的操作
		try {
			//执行第一条查询语句
			statement = connection.prepareStatement(sql1);
			resultSet1=statement.executeQuery();
			while(resultSet1.next()){
				int news_id=resultSet1.getInt(1);
				int news_sender_id=resultSet1.getInt(2);
				String news_content=resultSet1.getString(3);
				String news_time=resultSet1.getString(4);
				int news_stars=resultSet1.getInt(5);
				int news_views=resultSet1.getInt(6);
				//执行第二条查询语句
				statement = connection.prepareStatement(sql2);
				statement.setInt(1, news_id);
				resultSet2=statement.executeQuery();
				news_photos=new ArrayList<>();
				while(resultSet2.next()){
					String news_photo=resultSet2.getString(1);
					//将查询到的语句放入集合
					news_photos.add(news_photo);
				}
				//执行第三条语句
				String user_nickname = null,user_photo=null;
				statement = connection.prepareStatement(sql3);
				statement.setInt(1, news_sender_id);
				resultSet3=statement.executeQuery();
				while(resultSet3.next()){
					user_nickname=resultSet3.getString(1);
					user_photo=resultSet3.getString(2);
				}
				//将查找到的数据存入news的javabean中
				news=new News(news_id,user_photo,user_nickname,news_content,news_time,news_stars,news_views,news_photos);
				newslist.add(news);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			C3P0Utils.close(resultSet1, statement, connection);
			C3P0Utils.close(resultSet2, statement, connection);
			C3P0Utils.close(resultSet3, statement, connection);
		}
		return newslist;
	}

}
