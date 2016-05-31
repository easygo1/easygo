package com.easygo.model.dao.house;

import java.util.List;

import com.easygo.model.beans.house.HouseDateManage;

public interface IHouseDateManageDAO {
	/**
	 * 对房源可租日期进行增删查
	 */
	// 添加房源不可租的日期
	public abstract boolean addHouseDate(HouseDateManage houseDateManage);

	// 删除房源不可租的日期
	public abstract boolean delHouseDate(int house_id);

	// 查询房源不可租的日期
	

	// 查询订单中被租的日期
	public abstract List<HouseDateManage> selectAllHouse();

}
