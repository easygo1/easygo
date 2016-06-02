package com.easygo.model.beans.gson;

import java.util.List;

import com.easygo.model.beans.house.House;
import com.easygo.model.beans.house.HousePhoto;
import com.easygo.model.beans.order.Assess;
import com.easygo.model.beans.user.User;

/**
 * @Author PengHong
 * 
 * @Date 2016年5月30日 下午9:40:07
 * 
 *       APP请求房源列表时，封装JSON时使用的Beans
 */
public class GsonAboutHouse {
	List<House> houseList;
	List<User> userList;
	List<HousePhoto> housePhotoList;
	List<Assess> assessList;

	public GsonAboutHouse() {
		super();
	}

	public GsonAboutHouse(List<House> houseList, List<User> userList,
			List<HousePhoto> housePhotoList, List<Assess> assessList) {
		super();
		this.houseList = houseList;
		this.userList = userList;
		this.housePhotoList = housePhotoList;
		this.assessList = assessList;
	}

	public List<House> getHouseList() {
		return houseList;
	}

	public void setHouseList(List<House> houseList) {
		this.houseList = houseList;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public List<HousePhoto> getHousePhotoList() {
		return housePhotoList;
	}

	public void setHousePhotoList(List<HousePhoto> housePhotoList) {
		this.housePhotoList = housePhotoList;
	}

	public List<Assess> getAssessList() {
		return assessList;
	}

	public void setAssessList(List<Assess> assessList) {
		this.assessList = assessList;
	}

}
