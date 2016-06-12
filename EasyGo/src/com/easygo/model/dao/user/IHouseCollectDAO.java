package com.easygo.model.dao.user;

import java.util.List;

import com.easygo.model.beans.house.HouseCollect;

public interface IHouseCollectDAO {
	/**
	 * 对收藏进行增删
	 */
	// 添加收藏
	public abstract boolean addHouseCollect(HouseCollect houseCollect);

	// 删除收藏
	public abstract boolean delHouseCollect(int house_collect_id);

	// 查找某个用户的收藏列表
	public abstract List<HouseCollect> findHouseCollectByUserId(int user_id);

	// 查找某个用户是否收藏某個房源
	public abstract boolean findHouseCollectById(int user_id, int house_id);

	// 根据用户和房屋ID删除某个房源
	public abstract boolean deleteHouseCollectById(int user_id, int house_id);
	
	//查找某个用户的收藏列表，并且分页显示
	public abstract List<Integer> findHouseCollectByUserIdCur(int cur,int user_id);

}
