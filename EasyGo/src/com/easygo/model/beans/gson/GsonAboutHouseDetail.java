package com.easygo.model.beans.gson;

import java.util.List;

import com.easygo.model.beans.house.House;
import com.easygo.model.beans.house.HouseEquipmentName;
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
	// List<HouseCollect> houseCollectList;
	boolean isCollected;
	// 房源的设施
	List<HouseEquipmentName> houseEquipmentNameList;
	// 房源的房东
	User user;

	public GsonAboutHouseDetail(House house, List<HousePhoto> housePhotoList,
			boolean isCollected,
			List<HouseEquipmentName> houseEquipmentNameList, User user) {
		super();
		this.house = house;
		this.housePhotoList = housePhotoList;
		this.isCollected = isCollected;
		this.houseEquipmentNameList = houseEquipmentNameList;
		this.user = user;
	}

	public boolean isCollected() {
		return isCollected;
	}

	public void setCollected(boolean isCollected) {
		this.isCollected = isCollected;
	}

	public List<HouseEquipmentName> getHouseEquipmentNameList() {
		return houseEquipmentNameList;
	}

	public void setHouseEquipmentNameList(
			List<HouseEquipmentName> houseEquipmentNameList) {
		this.houseEquipmentNameList = houseEquipmentNameList;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
