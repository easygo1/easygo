package com.easygo.model.impl.user;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.easygo.model.beans.user.User;
import com.easygo.model.dao.user.IUserDAO;
import com.easygo.utils.C3P0Utils;

public class IUserDAOImpl implements IUserDAO {
	private Connection connection = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;

	@Override
	public boolean addUser(User user) {
		// TODO Auto-generated method stub

		return false;
	}

	@Override
	public boolean delUser(int user_no) {
		// TODO Auto-generated method stub
		connection = C3P0Utils.getConnection();
		String sql = "delete from User where user_no = ?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, user_no);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
	}

	@Override
	public boolean updateUser(int user_no, User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User findSpecStudent(int user_no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> selectAllUser() {
		// TODO Auto-generated method stub
		List<User> userList = new ArrayList<User>();
		connection = C3P0Utils.getConnection();
		String sql = "select * from user";
		try {
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int user_id = resultSet.getInt(1);
				String user_no = resultSet.getString(2);
				String user_realname = resultSet.getString(3);
				String user_password = resultSet.getString(4);
				String user_nickname = resultSet.getString(5);
				String user_sex = resultSet.getString(6);
				String user_phone = resultSet.getString(7);
				int user_type = resultSet.getInt(8);
				String user_photo = resultSet.getString(9);
				String user_job = resultSet.getString(10);
				String user_address_province = resultSet.getString(11);
				String user_address_city = resultSet.getString(12);
				String user_mood = resultSet.getString(13);
				String user_mail = resultSet.getString(14);
				String user_introduct = resultSet.getString(15);
				Date user_birthday = resultSet.getDate(16);
				String user_idcard = resultSet.getString(17);

				User user = new User(user_id, user_no, user_realname,
						user_password, user_nickname, user_sex, user_phone,
						user_type, user_photo, user_job, user_address_province,
						user_address_city, user_mood, user_mail,
						user_introduct, user_birthday, user_idcard);

				userList.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
		return userList;
	}

}
