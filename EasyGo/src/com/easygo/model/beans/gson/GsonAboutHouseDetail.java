package com.easygo.model.beans.gson;

import java.util.List;

import com.easygo.model.beans.house.Equipment;
import com.easygo.model.beans.house.House;
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
	// 房源的设施
	List<Equipment> houseEquipmentList;
	// 房源的房东
	User user;
	// 得到用户的收藏

}
