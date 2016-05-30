package com.easygo.model.impl.house;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.easygo.model.beans.house.HotCity;
import com.easygo.model.beans.house.House;
import com.easygo.model.beans.house.HousePhoto;
import com.easygo.model.dao.house.IHousePhotoDAO;
import com.easygo.utils.C3P0Utils;

public class IHousePhotoDAOImpl implements IHousePhotoDAO {
	private Connection connection = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;
	@Override
	public boolean addSpecIHousePhoto(HousePhoto housePhoto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteSpecIHousePhoto(int house_id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<HousePhoto> selectSpecIHousePhoto(int house_id) {
		// TODO Auto-generated method stub
		List<HousePhoto> housePhotoList = new ArrayList<HousePhoto>();
		connection = C3P0Utils.getConnection();
		String sql = "select * from house where house_id =?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, house_id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int house_photo_id = resultSet.getInt(1);
				int house_id1 = resultSet.getInt(2);
				String house_photo_path = resultSet.getString(3);
				HousePhoto housePhtoo = new HousePhoto(house_photo_id, house_id1, house_photo_path);
				housePhotoList.add(housePhtoo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			C3P0Utils.close(resultSet, statement, connection);
		}
		return null;
	}

}
