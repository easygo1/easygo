package com.easygo.model.impl.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.easygo.model.beans.order.Assess;
import com.easygo.model.dao.order.IAssessDAO;
import com.easygo.utils.C3P0Utils;

public class IAssessDAOImpl implements IAssessDAO {
	private Connection connection = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;

	@Override
	public boolean addAssess(Assess assess) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delAssess(int assess_id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Assess findSpecAssessById(int assess_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Assess> selectAllAssess(int house_id) {
		List<Assess> assessList = new ArrayList<Assess>();
		connection = C3P0Utils.getConnection();
		String sql = "select * from assess where house_id =?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, house_id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int assess_id = resultSet.getInt(1);
				int order_id = resultSet.getInt(2);
				int house_id2 = resultSet.getInt(3);
				int user_id = resultSet.getInt(4);
				int star = resultSet.getInt(5);
				String assess_content = resultSet.getString(6);

				Assess assess = new Assess(assess_id, order_id, house_id2,
						user_id, star, assess_content);
				assessList.add(assess);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
		return assessList;
	}

}
