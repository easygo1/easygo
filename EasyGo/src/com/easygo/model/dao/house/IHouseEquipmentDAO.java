package com.easygo.model.dao.house;

import java.util.List;

import com.easygo.model.beans.house.Equipment;
import com.easygo.model.beans.house.HouseEquipment;
import com.easygo.model.beans.house.HouseEquipmentName;

public interface IHouseEquipmentDAO {
	/**
	 * 对房源设施进行增删改查
	 */
	// 增加房源设施
	public abstract boolean addEquipment(Equipment equipment);

	// 删除某个房源设施
	public abstract boolean deleteEquipment(int equipment_id);

	// 增加某一房源的房源设施
	public abstract boolean addHouseEquipment(HouseEquipment houseEquipment);

	// 删除某个房源的房源设施设施
	public abstract boolean deleteHouseEquipment(int house_id, int equipment_id);

	// 查询某个房源的所有房源设施
	public abstract List<Equipment> selectHouseEquipment(int house_id);

	// 根据房源设施名称查询设施id
	public abstract int selectEquipmentId(String equipment_name);

	// 根据设施id查询设施
	// public abstract int selectEquipmentId(String equipment_name);

	// 根据设施id，连接查询设施
	public abstract List<HouseEquipmentName> selectEquipmentName(int house_id);

}
