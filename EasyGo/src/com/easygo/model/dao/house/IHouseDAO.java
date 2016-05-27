package com.easygo.model.dao.house;

import java.util.List;

import com.easygo.model.beans.house.House;

public interface IHouseDAO {

	/**
	 * 
	 * 对房源进行增删改查
	 */
	// 添加房源
	public abstract boolean addHouse(House house);

	// 删除房源
	public abstract boolean delHouse(int house_id);

	// 修改房源
	public abstract boolean updateHouse(int house_id, House house);

	// 查找房源（通过房源ID查询）
	public abstract List<House> findSpecHouseById(int house_id);

	// 某个地区的所有房源
	public abstract List<House> findSpecHouseByStyle(String house_style);

	// 所有房源
	public abstract List<House> selectAllHouse();

}
