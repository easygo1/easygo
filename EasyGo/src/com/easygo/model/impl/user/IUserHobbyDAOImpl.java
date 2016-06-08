package com.easygo.model.impl.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.easygo.model.beans.user.Hobby;
import com.easygo.model.beans.user.Userhobby;
import com.easygo.model.dao.user.IUserHobbyDAO;
import com.easygo.utils.C3P0Utils;

public class IUserHobbyDAOImpl implements IUserHobbyDAO {

	private Connection connection = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;
	private boolean flag;
	private List<Integer> hobbyidlist;

	@Override
	public boolean addHobby(Hobby hobby) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteHobby(int hobby_id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addUserHobby(Userhobby userhobby) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUserHobby(int user_id, int hobby_id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Userhobby> findUserAllHobbyById(int user_id) {
		// TODO Auto-generated method stub
		return null;
	}
//向用户爱好表中插入数据
	@Override
	public boolean inssertUserHobby(int user_id, int hobby_id) {
		connection = C3P0Utils.getConnection();
		String sql = "INSERT INTO user_hobby(user_id,hobby_id) VALUE(?,?);";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, user_id);
			statement.setInt(2, hobby_id);
			statement.executeUpdate();
			flag = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = false;
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
		return flag;
	}

	@Override
	public boolean selectExistUserHobby(int user_id, int hobby_id) {
		connection = C3P0Utils.getConnection();
		String sql = "select user_hobby_id from user_hobby where user_id =? and hobby_id=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, user_id);
			statement.setInt(2, hobby_id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return true;
			}else{
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
	}

	@Override
	public boolean DeleteUserHobbyByUserId(int user_id) {
		connection = C3P0Utils.getConnection();
		String sql = "delete from user_hobby where user_id = ?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, user_id);
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
	public List<Integer> selectAllUserHobbyById(int user_id) {
		hobbyidlist=new ArrayList<Integer>();
		connection = C3P0Utils.getConnection();
		String sql = "select hobby_id from user_hobby where user_id=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, user_id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int hobby_id = resultSet.getInt(1);
				hobbyidlist.add(hobby_id);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
		return hobbyidlist;
	}
	
}
