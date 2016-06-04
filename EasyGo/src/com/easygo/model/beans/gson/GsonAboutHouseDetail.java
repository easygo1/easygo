package com.easygo.model.beans.gson;

import java.util.List;

import com.easygo.model.beans.house.Equipment;
import com.easygo.model.beans.house.House;
import com.easygo.model.beans.house.HouseCollect;
import com.easygo.model.beans.house.HousePhoto;
import com.easygo.model.beans.user.User;

/**
 * @Author PengHong
 * 
 * @Date 2016年6月1日 下午9:54:34
 * 
 *       某个房源的具体信息
 */
// 未完
public class GsonAboutHouseDetail {
	// 房屋
	House house;
	// 房源的图片
	List<HousePhoto> housePhotoList;
	// 用户收藏
	List<HouseCollect> houseCollectList;
	// 房源的设施
	List<Equipment> houseEquipmentList;
	// 房源的房东
	User user;

	public GsonAboutHouseDetail(House house, List<HousePhoto> housePhotoList,
			List<HouseCollect> houseCollectList,
			List<Equipment> houseEquipmentList, User user) {
		super();
		this.house = house;
		this.housePhotoList = housePhotoList;
		this.houseCollectList = houseCollectList;
		this.houseEquipmentList = houseEquipmentList;
		this.user = user;
	}

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

	public List<HousePhoto> getHousePhotoList() {
		return housePhotoList;
	}

	public void setHousePhotoList(List<HousePhoto> housePhotoList) {
		this.housePhotoList = housePhotoList;
	}

	public List<HouseCollect> getHouseCollectList() {
		return houseCollectList;
	}

	public void setHouseCollectList(List<HouseCollect> houseCollectList) {
		this.houseCollectList = houseCollectList;
	}

	public List<Equipment> getHouseEquipmentList() {
		return houseEquipmentList;
	}

	public void setHouseEquipmentList(List<Equipment> houseEquipmentList) {
		this.houseEquipmentList = houseEquipmentList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
