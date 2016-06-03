package com.easygo.model.impl.house;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
		boolean result = false;
		connection = C3P0Utils.getConnection();
		String sql = "INSERT INTO house_photo(house_id,house_photo_path,isFirst) VALUE(?,?,?);";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, housePhoto.getHouse_id());
			statement.setString(2, housePhoto.getHouse_photo_path());
			statement.setInt(3, housePhoto.getIsFirst());
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
	public boolean deleteSpecIHousePhoto(int house_id) {
		// TODO Auto-generated method stub
		return false;
	}

	// 查找某个房屋的所有照片
	@Override
	public List<HousePhoto> selectSpecIHousePhoto(int house_id) {
		// TODO Auto-generated method stub
		List<HousePhoto> housePhotoList = new ArrayList<HousePhoto>();
		connection = C3P0Utils.getConnection();
		String sql = "select * from house_photo where house_id = ?";

		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, house_id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int house_photo_id = resultSet.getInt(1);
				int house_id1 = resultSet.getInt(2);
				String house_photo_path = resultSet.getString(3);
				int isFirst = resultSet.getInt(4);
				HousePhoto housePhoto = new HousePhoto(house_photo_id,
						house_id1, house_photo_path, isFirst);
				housePhotoList.add(housePhoto);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
		return housePhotoList;
	}

	@Override
	public HousePhoto selectSpecIHousePhotoFirst(int house_id) {
		// TODO Auto-generated method stub
		HousePhoto housePhoto = new HousePhoto();
		connection = C3P0Utils.getConnection();
		String sql = "select * from house_photo where house_id =? and isFirst = 1";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, house_id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int house_photo_id = resultSet.getInt(1);
				int house_id1 = resultSet.getInt(2);
				String house_photo_path = resultSet.getString(3);
				int isFirst = resultSet.getInt(4);

				housePhoto = new HousePhoto(house_photo_id, house_id1,
						house_photo_path, isFirst);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
		return housePhoto;
	}

}
