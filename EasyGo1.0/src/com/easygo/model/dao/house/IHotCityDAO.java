package com.easygo.model.dao.house;

import java.util.List;

import com.easygo.model.beans.house.HotCity;

public interface IHotCityDAO {
	/**
	 * 对热门城市进行增删改查
	 */
	// 添加热门城市
	public abstract boolean addSpecStudent(HotCity hotcity);

	// 删除热门城市
	public abstract boolean deleteSpecStudent(int hot_city_id);

	// 修改热门城市
	public abstract boolean updateSpecStudent(int hot_city_id,HotCity hotcity);

	// 查询所有热门城市
	public abstract List<HotCity> selectAllStudent();

	// 查找某个热门城市
	public abstract HotCity selectSpecStudent(int hot_city_id);
}
