package com.easygo.model.dao.house;

import java.util.List;

import com.easygo.model.beans.chat.Friend;
import com.easygo.model.beans.house.Equipment;
import com.easygo.model.beans.house.HotCity;
import com.easygo.model.beans.house.HouseEquipment;

public interface IHouseEquipmentDAO {
	/**
	 * 对房源设施进行增删改查
	 */
	// 增加房源设施
	public abstract boolean addEquipment(Equipment equipment);

	// 删除某个房源设施
	public abstract boolean deleteEquipment(int equipment_id);

	// 增加某一房源设施
	public abstract boolean addHouseEquipment(HouseEquipment houseEquipment);

	// 删除某个房源设施
	public abstract boolean deleteHouseEquipment(int house_id,int equipment_id);

	// 查询某个房源的所有房源设施
	public abstract HotCity selectHouseEquipment(int house_id);

}
