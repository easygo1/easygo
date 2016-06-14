package com.easygo.model.impl.house;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.easygo.model.beans.house.HouseDateManage;
import com.easygo.model.dao.house.IHouseDateManageDAO;
import com.easygo.utils.C3P0Utils;

public class IHouseDateManageDAOImpl implements IHouseDateManageDAO {

	private Connection connection = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;

	@Override
	public boolean addHouseDate(HouseDateManage houseDateManage) {
		// TODO Auto-generated method stub
		boolean result = false;
		connection = C3P0Utils.getConnection();
		String sql = "INSERT INTO house_date_manage(house_id,date_not_use,date_manage_type) "
				+ "VALUES (?,?,?)";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, houseDateManage.getHouse_id());
			statement.setString(2, houseDateManage.getDate_not_use());
			statement.setInt(3, houseDateManage.getDate_manage_type());
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
	public boolean delHouseDate(HouseDateManage houseDateManage) {
		boolean result = false;
		connection = C3P0Utils.getConnection();
		String sql = "DELETE FROM house_date_manage WHERE house_id=? AND date_not_use=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, houseDateManage.getHouse_id());
			statement.setString(2, houseDateManage.getDate_not_use());

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

	// 查询某个房源不可租的日期
	@Override
	public List<HouseDateManage> selectAllDateById(int house_id) {
		connection = C3P0Utils.getConnection();
		List<HouseDateManage> houseDateList = new ArrayList<>();
		String sql = "SELECT * FROM house_date_manage WHERE house_id = ?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, house_id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int date_manage_id = resultSet.getInt(1);
				int house_id2 = resultSet.getInt(2);
				String date_not_use = resultSet.getString(3);
				int date_manage_type = resultSet.getInt(4);
				HouseDateManage hdm = new HouseDateManage(date_manage_id,
						house_id2, date_not_use, date_manage_type);
				houseDateList.add(hdm);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
		return houseDateList;
	}

	@Override
	public List<HouseDateManage> selectAllDateById(int house_id,
			int date_manage_type) {
		connection = C3P0Utils.getConnection();
		List<HouseDateManage> houseDateList = new ArrayList<>();
		String sql = "SELECT * FROM house_date_manage WHERE house_id = ? AND date_manage_type=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, house_id);
			statement.setInt(2, date_manage_type);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int date_manage_id = resultSet.getInt(1);
				int house_id2 = resultSet.getInt(2);
				String date_not_use = resultSet.getString(3);
				int date_manage_type2 = resultSet.getInt(4);
				HouseDateManage hdm = new HouseDateManage(date_manage_id,
						house_id2, date_not_use, date_manage_type2);
				houseDateList.add(hdm);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
		return houseDateList;
	}

	@Override
	public List<HouseDateManage> selectAllHouse() {
		// TODO Auto-generated method stub

		return null;
	}
	@Override
	public List<String> selsectNoGoHouse(int house_id) {
		List<String> housedatenolist=new ArrayList<String>();
		connection = C3P0Utils.getConnection();
		String sql = "SELECT date_not_use FROM house_date_manage WHERE house_id = ?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, house_id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				String date_not_use = resultSet.getString(1);
				housedatenolist.add(date_not_use);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
		return housedatenolist;
	}

}
