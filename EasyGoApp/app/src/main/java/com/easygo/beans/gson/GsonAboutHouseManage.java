package com.easygo.beans.gson;


import com.easygo.beans.house.HouseDateManage;

import java.io.Serializable;
import java.util.List;

/**
 * @Author PengHong
 * 
 *         getHouseDateByHouseId APP请求日期管理时，封装JSON时使用的Beans
 */
public class GsonAboutHouseManage implements Serializable {
	List<HouseDateManage> houseUserBuyList;// 用户预定的某个房屋的日期
	List<HouseDateManage> houseNotList;// 房东设置的某个房屋的不可租的日期

	public GsonAboutHouseManage(List<HouseDateManage> houseUserBuyList,
			List<HouseDateManage> houseNotList) {
		super();
		this.houseUserBuyList = houseUserBuyList;
		this.houseNotList = houseNotList;
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
