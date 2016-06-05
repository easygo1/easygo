package com.easygo.model.impl.house;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.easygo.model.beans.house.Equipment;
import com.easygo.model.beans.house.HouseEquipment;
import com.easygo.model.beans.house.HouseEquipmentName;
import com.easygo.model.dao.house.IHouseEquipmentDAO;
import com.easygo.utils.C3P0Utils;

public class IHouseEquipmentDAOImpl implements IHouseEquipmentDAO {
	private Connection connection = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;

	@Override
	public boolean addEquipment(Equipment equipment) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteEquipment(int equipment_id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addHouseEquipment(HouseEquipment houseEquipment) {
		// TODO Auto-generated method stub
		boolean result = false;
		connection = C3P0Utils.getConnection();
		String sql = "INSERT INTO house_equipment(house_id,equipment_id) VALUE(?,?);";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, houseEquipment.getHouse_id());
			statement.setInt(2, houseEquipment.getEquipment_id());
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
	public boolean deleteHouseEquipment(int house_id, int equipment_id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Equipment> selectHouseEquipment(int house_id) {
		// TODO Auto-generated method stub
		// 未写完
		connection = C3P0Utils.getConnection();
		Equipment equipment = new Equipment();
		List<Equipment> list = new ArrayList<>();
		String sql = "SELECT * FROM house_equipment WHERE house_id =?;";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, house_id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				equipment.setEquipment_id(resultSet.getInt(1));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
		return list;
	}

	@Override
	public int selectEquipmentId(String equipment_name) {
		// TODO Auto-generated method stub
		connection = C3P0Utils.getConnection();
		int equipment_id = 0;
		String sql = "select equipment_id from equipment where equipment_name =?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, equipment_name);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				equipment_id = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
		return equipment_id;
	}

	@Override
	public List<HouseEquipmentName> selectEquipmentName(int house_id) {
		// TODO Auto-generated method stub
		// 根据设施id，连接查询设施
		List<HouseEquipmentName> nameList = new ArrayList<>();
		connection = C3P0Utils.getConnection();
		String sql = "SELECT house_id,equipment_name FROM house_equipment,equipment "
				+ "WHERE house_id =? "
				+ "AND equipment.equipment_id = house_equipment.equipment_id;";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, house_id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int house_id2 = resultSet.getInt(1);
				String equipment_name = resultSet.getString(2);
				HouseEquipmentName houseEquipmentName = new HouseEquipmentName(
						house_id2, equipment_name);
				nameList.add(houseEquipmentName);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
		return nameList;
	}
}
