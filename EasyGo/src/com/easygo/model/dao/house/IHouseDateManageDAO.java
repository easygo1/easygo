package com.easygo.model.dao.house;

import java.util.List;

import com.easygo.model.beans.house.HouseDateManage;

public interface IHouseDateManageDAO {
	/**
	 * 对房源可租日期进行增删查
	 */
	// 添加房源不可租的日期
	public abstract boolean addHouseDate(HouseDateManage houseDateManage);

	// 删除房源不可租的日期(根据房屋和日期删除)
	public abstract boolean delHouseDate(HouseDateManage houseDateManage);

	// 查询某个房源不可租的日期（所有的不可租）
	public abstract List<HouseDateManage> selectAllDateById(int house_id);

	// 查询某个房源不可租的日期（加上类型查询）
	public abstract List<HouseDateManage> selectAllDateById(int house_id,
			int date_manage_type);

	// 查询订单中被租的日期
	public abstract List<HouseDateManage> selectAllHouse();

	// 删除房源不可租的日期（加上类型删除）
	public abstract boolean delAllNotDateById(int house_id, int date_manage_type);

	// 删除当前日期之前所有的日期
	public abstract boolean delDelayDate();

}
