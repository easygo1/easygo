package com.easygo.model.beans.gson;

import java.io.Serializable;
import java.util.List;

import com.easygo.model.beans.house.HouseDateManage;

/**
 * @Author PengHong
 * 
 *         getHouseDateByHouseId APP请求日期管理时，封装JSON时使用的Beans
 */
public class GsonAboutHouseManage implements Serializable {
	List<HouseDateManage> houseUserBuyList;// 用户预定的某个房屋的日期
	List<HouseDateManage> houseNotList;// 房东设置的某个房屋的不可租的日期
	private int house_id;// 将房子的id一起返回

	public GsonAboutHouseManage() {
		super();
	}

	public GsonAboutHouseManage(List<HouseDateManage> houseUserBuyList,
			List<HouseDateManage> houseNotList, int house_id) {
		super();
		this.houseUserBuyList = houseUserBuyList;
		this.houseNotList = houseNotList;
		this.house_id = house_id;
	}

	public int getHouse_id() {
		return house_id;
	}

	public void setHouse_id(int house_id) {
		this.house_id = house_id;
	}

	public List<HouseDateManage> getHouseUserBuyList() {
		return houseUserBuyList;
	}

	public void setHouseUserBuyList(List<HouseDateManage> houseUserBuyList) {
		this.houseUserBuyList = houseUserBuyList;
	}

	public List<HouseDateManage> getHouseNotList() {
		return houseNotList;
	}

	public void setHouseNotList(List<HouseDateManage> houseNotList) {
		this.houseNotList = houseNotList;
	}

}
