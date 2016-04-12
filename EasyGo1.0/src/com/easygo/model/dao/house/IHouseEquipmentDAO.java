package com.easygo.model.dao.house;

import java.util.List;

import com.easygo.model.beans.chat.Friend;
import com.easygo.model.beans.house.HotCity;
import com.easygo.model.beans.house.HouseEquipment;

public interface IHouseEquipmentDAO {
	/**
	 * 对房源设施进行增删改查
	 */
	// 添加房源设施
	public abstract boolean addIHouseEquipment(HouseEquipment houseEquipment);
	// 修改房源设施
	public abstract boolean updateSpecHouseEquipment(int house_id,HouseEquipment houseEquipment);
	// 查询某个房源的所有房源设施
	public abstract HotCity selectSpecHouseEquipment(int house_id);
		
}
