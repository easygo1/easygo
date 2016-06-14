package com.easygo.model.beans.gson;

import java.io.Serializable;
import java.util.List;

import com.easygo.model.beans.house.House;
import com.easygo.model.beans.house.HouseCollect;
import com.easygo.model.beans.house.HousePhoto;
import com.easygo.model.beans.user.User;

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
	List<Integer> starNumList;

	public GsonAboutHouse() {
		super();
	}

	public GsonAboutHouse(List<House> houseList, List<User> userList,
			List<HousePhoto> housePhotoList, List<Integer> assessList,
			List<HouseCollect> houseCollectList, List<Integer> starNumList) {
		super();
		this.houseList = houseList;
		this.userList = userList;
		this.housePhotoList = housePhotoList;
		this.assessList = assessList;
		this.houseCollectList = houseCollectList;
		this.starNumList = starNumList;
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

	public List<Integer> getStarNumList() {
		return starNumList;
	}

	public void setStarNumList(List<Integer> starNumList) {
		this.starNumList = starNumList;
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
