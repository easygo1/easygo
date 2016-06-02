package com.easygo.beans.gson;

import com.easygo.beans.house.House;
import com.easygo.beans.house.HouseCollect;
import com.easygo.beans.house.HousePhoto;
import com.easygo.beans.user.User;

import java.io.Serializable;
import java.util.List;

/**
 * @Author PengHong
 * 
 * @Date 2016年5月30日 下午9:40:07
 * 
 *       APP请求房源列表时，封装JSON时使用的Beans
 */
public class GsonAboutHouse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<House> houseList;
	List<User> userList;
	List<HousePhoto> housePhotoList;
	List<Integer> assessList;
	List<HouseCollect> houseCollectList;

	public GsonAboutHouse() {
		super();
	}

	public GsonAboutHouse(List<House> houseList, List<User> userList,
			List<HousePhoto> housePhotoList, List<Integer> assessList,
			List<HouseCollect> houseCollectList) {
		super();
		this.houseList = houseList;
		this.userList = userList;
		this.housePhotoList = housePhotoList;
		this.assessList = assessList;
		this.houseCollectList = houseCollectList;
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

	public List<Integer> getAssessList() {
		return assessList;
	}

	public void setAssessList(List<Integer> assessList) {
		this.assessList = assessList;
	}

	public List<HouseCollect> getHouseCollectList() {
		return houseCollectList;
	}

	public void setHouseCollectList(List<HouseCollect> houseCollectList) {
		this.houseCollectList = houseCollectList;
	}

}
