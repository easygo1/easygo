package com.easygo.model.impl.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.easygo.model.beans.house.HouseCollect;
import com.easygo.model.dao.user.IHouseCollectDAO;
import com.easygo.utils.C3P0Utils;

public class IHouseCollectDAOImpl implements IHouseCollectDAO {

	private Connection connection = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;

	@Override
	public boolean addHouseCollect(HouseCollect houseCollect) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delHouseCollect(int house_collect_id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<HouseCollect> findHouseCollectByUserId(int user_id) {
		// TODO Auto-generated method stub
		List<HouseCollect> houseCollectList = new ArrayList<>();
		connection = C3P0Utils.getConnection();
		String sql = "select * from house_collect where user_id =?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, user_id);

			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int house_collect_id = resultSet.getInt(1);
				int user_id2 = resultSet.getInt(2);
				int house_id = resultSet.getInt(3);
				HouseCollect houseCollect = new HouseCollect(house_collect_id,
						user_id2, house_id);

				houseCollectList.add(houseCollect);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
		return houseCollectList;
	}

}
