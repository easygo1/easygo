package com.easygo.model.impl.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.easygo.model.beans.house.House;
import com.easygo.model.beans.order.UserOrderLinkman;
import com.easygo.model.dao.order.IUserOrderLinkmanDAO;
import com.easygo.utils.C3P0Utils;

public class IUserOrderLinkmanDAOImpl implements IUserOrderLinkmanDAO {
	private Connection connection = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;
	private List<UserOrderLinkman> userorderlinkmanlist;
	private UserOrderLinkman userorderlinkman;

	@Override
	public boolean addUserOrderLinkman(UserOrderLinkman userOrderLinkman) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delUserOrderLinkman(int user_linkman_id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<UserOrderLinkman> selectUserOrderLinkmanByOrderid(int order_id) {
		userorderlinkmanlist=new ArrayList<UserOrderLinkman>();
		connection = C3P0Utils.getConnection();
		String sql = "select * from user_order_linkman where order_id =?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, order_id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int user_linkman_id = resultSet.getInt(1);
				int order_id2 = resultSet.getInt(2);
				String name = resultSet.getString(3);
				String idcard = resultSet.getString(4);
				userorderlinkman = new UserOrderLinkman(user_linkman_id, order_id2, name, idcard);
				userorderlinkmanlist.add(userorderlinkman);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
		return userorderlinkmanlist;
	}

}
