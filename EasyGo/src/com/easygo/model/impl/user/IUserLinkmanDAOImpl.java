package com.easygo.model.impl.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.easygo.model.beans.user.UserLinkman;
import com.easygo.model.dao.user.IUserLinkmanDAO;
import com.easygo.utils.C3P0Utils;

public class IUserLinkmanDAOImpl implements IUserLinkmanDAO {
	private Connection connection = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;
	UserLinkman userlinkman;

	@Override
	public boolean addUserLinkman(UserLinkman userLinkman) {
		// TODO Auto-generated method stub
		boolean result = false;
		connection = C3P0Utils.getConnection();
		// 向表中加入该用户
		String sql = "INSERT INTO user_linkman (user_id,linkman_name,idcard) VALUES(?,?,?)";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, userLinkman.getUser_id());
			statement.setString(2, userLinkman.getLinkman_name());
			statement.setString(3, userLinkman.getIdcard());
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0Utils.close(null, statement, connection);
		}
		return result;
	}

	@Override
	public boolean delAllUserLinkman(int user_id) {
		// TODO Auto-generated method stub
		// 删除某个用户所有联系人
		boolean result = false;
		connection = C3P0Utils.getConnection();
		String sql = "DELETE FROM user_linkman WHERE user_id=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, user_id);
			statement.executeUpdate(sql);
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
		return result;
	}

	@Override
	public boolean updateUserLinkman(UserLinkman userlinkman) {
		// TODO Auto-generated method stub
		boolean result = false;
		connection = C3P0Utils.getConnection();
		String sql = "UPDATE  user_linkman SET linkman_name = ?,idcard = ? WHERE user_linkman_id = ?;";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, userlinkman.getLinkman_name());
			statement.setString(2, userlinkman.getIdcard());
			statement.setInt(3, userlinkman.getUser_linkman_id());
			statement.executeUpdate();
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
		return result;
	}

	@Override
	public List<UserLinkman> findAllUserLinkmanById(int user_id) {
		// TODO Auto-generated method stub
		List<UserLinkman> list = new ArrayList<>();
		connection = C3P0Utils.getConnection();
		String sql = "SELECT * FROM user_linkman WHERE user_id = ?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, user_id);
			// statement.executeUpdate(sql);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int user_linkman_id = resultSet.getInt(1);
				int user_id2 = resultSet.getInt(2);
				String linkman_name = resultSet.getString(3);
				String idcard = resultSet.getString(4);
				UserLinkman userLinkman = new UserLinkman(user_linkman_id,
						user_id2, linkman_name, idcard);
				list.add(userLinkman);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
		return list;
	}

	@Override
	public boolean delUserLinkman(int user_linkman_id) {
		// TODO Auto-generated method stub
		boolean result = false;
		connection = C3P0Utils.getConnection();
		// 向表中加入该用户
		String sql = "delete from user_linkman where user_linkman_id = ?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, user_linkman_id);
			// statement.setString(2, userLinkman.getLinkman_name());
			// statement.setString(3, userLinkman.getIdcard());
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0Utils.close(null, statement, connection);
		}
		return result;
	}

	@Override
	public int addUserLinkmanReturnId(UserLinkman userlinkman) {
		// TODO Auto-generated method stub
		int linkmanId = 0;
		connection = C3P0Utils.getConnection();
		// 向表中加入该用户
		String sql = "INSERT INTO user_linkman (user_id,linkman_name,idcard) VALUES(?,?,?)";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, userlinkman.getUser_id());
			statement.setString(2, userlinkman.getLinkman_name());
			statement.setString(3, userlinkman.getIdcard());
			statement.executeUpdate();
			resultSet = statement.executeQuery("SELECT LAST_INSERT_ID()");
			if (resultSet.next()) {
				// 获取到当前插入数据时的主键
				linkmanId = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0Utils.close(null, statement, connection);
		}
		return linkmanId;
	}

}
