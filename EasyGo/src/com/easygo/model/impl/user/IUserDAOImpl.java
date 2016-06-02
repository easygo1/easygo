package com.easygo.model.impl.user;

import com.easygo.utils.io.rong.ApiHttpClient;
import com.easygo.utils.io.rong.models.FormatType;
import com.easygo.utils.io.rong.models.SdkHttpResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.easygo.model.beans.user.User;
import com.easygo.model.dao.user.IUserDAO;
import com.easygo.utils.C3P0Utils;
import com.google.gson.Gson;
import com.easygo.model.impl.user.Result;

public class IUserDAOImpl implements IUserDAO {

	String key = "8w7jv4qb7hpsy";// 替换成您的appkey
	String secret = "gVIYobVgqQe";// 替换成匹配上面key的secret

	private Connection connection = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;

	//
	// 用户注册,传入昵称获取token
	public String getToken(String user_phone) {
		Result token = null;
		// 获取到token的json数据
		SdkHttpResult result = null;
		Gson gson = new Gson();
		try {
			result = ApiHttpClient.getToken(key, secret, user_phone,
					user_phone,
					"http://d3.freep.cn/3tb_1605172026345g7c564917.jpg",
					FormatType.json);
			token = gson.fromJson(result.toString(), Result.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String a = token.getResult().getToken();
		return a;
	}

	
	
	@Override
	public boolean addUser(User user) {
		boolean result = false;
		connection = C3P0Utils.getConnection();
		String sql = "INSERT INTO USER(user_no,user_realname,user_password,user_nickname,user_sex,user_phone,user_type,user_photo,user_job,user_address_province,user_address_city,user_mood,user_mail,user_introduct,user_birthday,user_idcard,token,remarks) VALUE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		try {
			statement = connection.prepareStatement(sql);
			System.out.println(user.getUser_birthday());
			statement.setString(1, user.getUser_no());
			statement.setString(2, user.getUser_realname());
			statement.setString(3, user.getUser_password());
			statement.setString(4, user.getUser_nickname());
			statement.setString(5, user.getUser_sex());
			statement.setString(6, user.getUser_phone());
			statement.setInt(7, user.getUser_type());
			statement.setString(8, user.getUser_photo());
			statement.setString(9, user.getUser_job());
			statement.setString(10, user.getUser_address_province());
			statement.setString(11, user.getUser_address_city());
			statement.setString(12, user.getUser_mood());
			statement.setString(13, user.getUser_mail());
			statement.setString(14, user.getUser_introduct());
			statement.setString(15, user.getUser_birthday());
			statement.setString(16, user.getUser_idcard());
			// 向数据库中插入token
			statement.setString(17, getToken(user.getUser_nickname()));
			statement.setString(18, user.getRemarks());
			statement.executeUpdate();
			result = true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			result = false;
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}

		return result;
	}
	//注册
	@Override
	public boolean register(User user) {
		boolean result = false;
		connection = C3P0Utils.getConnection();
		String sql = "INSERT INTO USER(user_password,user_phone,token) VALUE(?,?,?);";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, user.getUser_password());
			statement.setString(2, user.getUser_phone());
			//向数据库中插入token
			statement.setString(3, getToken(user.getUser_phone()));
			statement.executeUpdate();
			result = true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			result = false;
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}

		return result;
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
	public boolean updateUser(String user_no, User user) {
		connection = C3P0Utils.getConnection();
		try {
			statement = connection
					.prepareStatement("UPDATE user SET user_realname=?,user_nickname=?,user_sex=?,user_photo=?,user_address_province=?,user_address_city=?,user_mood=?,user_mail=?,user_introduct=?,user_birthday=? where user_no = ?");
			statement.setString(1, user.getUser_realname());// 用户真实姓名
			statement.setString(2, user.getUser_nickname());// 用户昵称
			statement.setString(3, user.getUser_sex());
			statement.setString(4, user.getUser_photo());
			statement.setString(5, user.getUser_address_province());
			statement.setString(6, user.getUser_address_city());
			statement.setString(7, user.getUser_mood());// 个性签名
			statement.setString(8, user.getUser_mail());
			statement.setString(9, user.getUser_introduct());
			statement.setString(10, user.getUser_birthday());
			statement.setString(11, user_no);
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
	public User findSpecUserByNo(int user_no) {
		User user = null;
		connection = C3P0Utils.getConnection();
		String sql = "select * from user where user_no =?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, user_no);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int user_id = resultSet.getInt(1);
				String user_no2 = resultSet.getString(2);
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
				String user_birthday = resultSet.getString(16);
				String user_idcard = resultSet.getString(17);

				user = new User(user_id, user_no2, user_realname,
						user_password, user_nickname, user_sex, user_phone,
						user_type, user_photo, user_job, user_address_province,
						user_address_city, user_mood, user_mail,
						user_introduct, user_birthday, user_idcard);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return user;
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
		return user;
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
				String user_birthday = resultSet.getString(16);
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

	// 修改用户头像
	@Override
	public boolean updateUserPhoto(int user_id, String user_photo) {
		try {
			statement = connection
					.prepareStatement("UPDATE user SET user_photo=? where user_id = ?");
			statement.setInt(1, user_id);
			statement.setString(2, user_photo);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	// 用户登录，查找用户名和密码
	@Override
	public String login(String user_no,String user_password) {
		String token = null;
		connection = C3P0Utils.getConnection();
		String sql = "select token from user where user_phone =? and user_password=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, user_phone);
			statement.setString(2, user_password);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				token = resultSet.getString(1);
				System.out.println("数据查找成功");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return token;
	}

	@Override
	public User findSpecUserById(int user_id) {
		// TODO Auto-generated method stub
		// 通过id查询用户
		User user = null;
		connection = C3P0Utils.getConnection();
		String sql = "select * from user where user_id =?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, user_id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int user_id2 = resultSet.getInt(1);
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
				String user_birthday = resultSet.getString(16);
				String user_idcard = resultSet.getString(17);

				user = new User(user_id2, user_no, user_realname,
						user_password, user_nickname, user_sex, user_phone,
						user_type, user_photo, user_job, user_address_province,
						user_address_city, user_mood, user_mail,
						user_introduct, user_birthday, user_idcard);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return user;
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
		return user;
	}

	@Override
	public boolean updateUserById(int user_id, User user) {
		connection = C3P0Utils.getConnection();
		try {
			statement = connection
					.prepareStatement("UPDATE user SET user_realname=?,user_nickname=?,user_sex=?,user_photo=?,user_address_province=?,user_address_city=?,user_mood=?,user_mail=?,user_introduct=?,user_birthday=? where user_id = ?");
			statement.setString(1, user.getUser_realname());// 用户真实姓名
			statement.setString(2, user.getUser_nickname());// 用户昵称
			statement.setString(3, user.getUser_sex());
			statement.setString(4, user.getUser_photo());
			statement.setString(5, user.getUser_address_province());
			statement.setString(6, user.getUser_address_city());
			statement.setString(7, user.getUser_mood());// 个性签名
			statement.setString(8, user.getUser_mail());
			statement.setString(9, user.getUser_introduct());
			statement.setString(10, user.getUser_birthday());
			statement.setInt(11, user_id);
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

}
