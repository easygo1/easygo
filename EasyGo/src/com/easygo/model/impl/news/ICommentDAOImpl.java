package com.easygo.model.impl.news;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.easygo.model.beans.chat.Comment;
import com.easygo.model.dao.news.ICommentDAO;
import com.easygo.model.dao.user.IUserDAO;
import com.easygo.model.impl.user.IUserDAOImpl;
import com.easygo.utils.C3P0Utils;

public class ICommentDAOImpl implements ICommentDAO {

	private Connection connection = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;

	IUserDAO userdao;
	Comment comment = null;
	List<Comment> commentlist;
	int id = 0;

	// 增加一条评论
	@Override
	public boolean addIComment(Comment comment) {
		// TODO Auto-generated method stub
		System.out.println(comment.getComment_news_id());
		System.out.println(comment.getComment_user_id());
		System.out.println(comment.getComment_content());
		boolean result = false;

		connection = C3P0Utils.getConnection();
		String sql = "INSERT INTO COMMENT(comment_news_id,comment_user_id,comment_content,comment_time) VALUES(?,?,?,NOW());";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, comment.getComment_news_id());
			statement.setInt(2, comment.getComment_user_id());
			statement.setString(3, comment.getComment_content());
			statement.executeUpdate();
			System.out.println("评论成功");
			result = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean delIComment(int comment_id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Comment> selectAllIComment(int comment_news_id) {
		userdao = new IUserDAOImpl();
		// TODO Auto-generated method stub
		commentlist = new ArrayList<>();
		connection = C3P0Utils.getConnection();
		String sql = "SELECT * FROM COMMENT WHERE comment_news_id=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, comment_news_id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				// int comment_id=resultSet.getInt(1);
				int comment_user_id = resultSet.getInt(3);
				String comment_content = resultSet.getString(4);
				String comment_time = resultSet.getString(5);
				// 获取到昵称和头像
				String nickname = userdao.findSpecUserById(comment_user_id)
						.getUser_nickname();
				// 有问题，真实姓名返回的是头像的url
				String userphoto = userdao.findSpecUserById(comment_user_id)
						.getUser_photo();

				System.out.println(userphoto);
				comment = new Comment(userphoto, nickname, comment_content,
						comment_time);
				commentlist.add(comment);
			}
			System.out.println("评论查找成功");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return commentlist;
	}

}
