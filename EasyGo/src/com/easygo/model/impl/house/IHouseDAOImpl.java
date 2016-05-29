package com.easygo.model.impl.house;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.easygo.model.beans.house.House;
import com.easygo.model.dao.house.IHouseDAO;
import com.easygo.utils.C3P0Utils;

public class IHouseDAOImpl implements IHouseDAO {
	private Connection connection = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;
	@Override
	public boolean addHouse(House house) {
		// TODO Auto-generated method stub
		return false;
	}
	//根据房源id删除房源，删除成功返回true,失败返回false
	@Override
	public boolean delHouse(int house_id) {
		// TODO Auto-generated method stub
		connection = C3P0Utils.getConnection();
		String sql = "delete from house where house_id = ?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, house_id);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally{
			C3P0Utils.close(resultSet, statement, connection);
		}
		
	}

	@Override
	public boolean updateHouse(int house_id, House house) {
		// TODO Auto-generated method stub
		connection = C3P0Utils.getConnection();
		return false;
	}

	@Override
	public  List<House> findSpecHouseById(int house_id) {
		// TODO Auto-generated method stub
		List<House> houseList = new ArrayList<House>();
		connection = C3P0Utils.getConnection();
		String sql = "select * from house where house_id =?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, house_id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int house_id1 = resultSet.getInt(1);
				int user_id = resultSet.getInt(2);
				String house_title = resultSet.getString(3);
				String house_describe = resultSet.getString(4);
				String house_style = resultSet.getString(5);
				String house_address_province = resultSet.getString(6);
				String house_address_city = resultSet.getString(7);
				double house_address_lng = resultSet.getDouble(8);
				double house_address_lat = resultSet.getDouble(9);
				String house_traffic = resultSet.getString(10);
				int house_most_num = resultSet.getInt(11);
				double house_one_price = resultSet.getDouble(12);
				double house_add_price = resultSet.getDouble(13);
				String house_limit_sex = resultSet.getString(14);
				int house_stay_time = resultSet.getInt(15);
				int house_assess_sum = resultSet.getInt(16);
				House house = new House(house_id1, user_id, house_title, house_describe, 
						house_style,house_address_province,house_address_city, house_address_lng,
						house_address_lat, house_traffic, 
						house_most_num, house_one_price, house_add_price,
						house_limit_sex, house_stay_time, house_assess_sum);
				houseList.add(house);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			C3P0Utils.close(resultSet, statement, connection);
		}
		return houseList;
	}

	@Override
	public List<House> findSpecHouseByStyle(String house_style) {
		// TODO Auto-generated method stub
		List<House> houseList = new ArrayList<House>();
		connection = C3P0Utils.getConnection();
		String sql = "select * from house where house_style =?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, house_style);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int house_id1 = resultSet.getInt(1);
				int user_id = resultSet.getInt(2);
				String house_title = resultSet.getString(3);
				String house_describe = resultSet.getString(4);
				String house_style1 = resultSet.getString(5);
				String house_address_province = resultSet.getString(6);
				String house_address_city = resultSet.getString(7);
				double house_address_lng = resultSet.getDouble(8);
				double house_address_lat = resultSet.getDouble(9);
				String house_traffic = resultSet.getString(10);
				int house_most_num = resultSet.getInt(11);
				double house_one_price = resultSet.getDouble(12);
				double house_add_price = resultSet.getDouble(13);
				String house_limit_sex = resultSet.getString(14);
				int house_stay_time = resultSet.getInt(15);
				int house_assess_sum = resultSet.getInt(16);
				House house = new House(house_id1, user_id, house_title, house_describe, 
						house_style1,house_address_province,house_address_city, house_address_lng,
						house_address_lat, house_traffic, 
						house_most_num, house_one_price, house_add_price,
						house_limit_sex, house_stay_time, house_assess_sum);
				houseList.add(house);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			C3P0Utils.close(resultSet, statement, connection);
		}
		return houseList;
	}

	@Override
	public List<House> selectAllHouse() {
		// TODO Auto-generated method stub
		List<House> houseList = new ArrayList<House>();
		connection = C3P0Utils.getConnection();
		String sql = "select * from house";
		try {
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int house_id = resultSet.getInt(1);
				int user_id = resultSet.getInt(2);
				String house_title = resultSet.getString(3);
				String house_describe = resultSet.getString(4);
				String house_style = resultSet.getString(5);
				String house_address_province = resultSet.getString(6);
				String house_address_city = resultSet.getString(7);
				double house_address_lng = resultSet.getDouble(8);
				double house_address_lat = resultSet.getDouble(9);
				String house_traffic = resultSet.getString(10);
				int house_most_num = resultSet.getInt(11);
				double house_one_price = resultSet.getDouble(12);
				double house_add_price = resultSet.getDouble(13);
				String house_limit_sex = resultSet.getString(14);
				int house_stay_time = resultSet.getInt(15);
				int house_assess_sum = resultSet.getInt(16);
				House house = new House(house_id, user_id, house_title, house_describe, 
						house_style,house_address_province,house_address_city, house_address_lng,
						house_address_lat, house_traffic, 
						house_most_num, house_one_price, house_add_price,
						house_limit_sex, house_stay_time, house_assess_sum);
				houseList.add(house);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			C3P0Utils.close(resultSet, statement, connection);
		}
		
		return houseList;
	}

}
