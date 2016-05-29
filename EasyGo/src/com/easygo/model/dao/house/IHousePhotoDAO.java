package com.easygo.model.dao.house;

import java.util.List;

import com.easygo.model.beans.house.HotCity;
import com.easygo.model.beans.house.HousePhoto;

public interface IHousePhotoDAO {
	/**
	 * 对房源照片进行增删改查
	 */
	// 添加房源照片
	public abstract boolean addSpecIHousePhoto(HousePhoto housePhoto);
	// 删除房源照片
	public abstract boolean deleteSpecIHousePhoto(int house_id);
	// 查询某个房源的所有房源照片
	public abstract List<HousePhoto> selectSpecIHousePhoto(int house_id);

}
