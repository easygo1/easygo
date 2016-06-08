package com.easygo.model.impl.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.easygo.model.dao.user.IHobbyDAO;
import com.easygo.utils.C3P0Utils;

public class IHobbyImpl implements IHobbyDAO{
	
	private Connection connection = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;
	
	private int hobby_id=0;
	private String hobbyName;

	@Override
	public int SelectHobbyIDByHobbyName(String hobby_name) {
		connection = C3P0Utils.getConnection();
		String sql = "select hobby_id from hobby where hobby_name =?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, hobby_name);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				hobby_id= resultSet.getInt(1);
				return hobby_id;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
		return hobby_id;
	}
	
	@Override
	public String selectNameByHobbyId(int hobby_id) {
		connection = C3P0Utils.getConnection();
		String sql = "select hobby_name from hobby where hobby_id=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, hobby_id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				hobbyName=resultSet.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
		return hobbyName;
	}

}
