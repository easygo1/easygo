package com.easygo.beans.gson;

import com.easygo.beans.house.House;
import com.easygo.beans.house.HousePhoto;
import com.easygo.beans.user.User;

import java.util.List;

public class GsonUserCollect {
	
	//房源信息
	private List<House> houselist;
	//房源图片主图
	private List<HousePhoto> housephotolist;
	//房源房东
	private List<User> userlist;
	//房源评价
	private List<Integer> assessList;
	//房源星级
	private List<Integer> starNumList;
	
	public GsonUserCollect(List<House> houselist,
						   List<HousePhoto> housephotolist, List<User> userlist,
						   List<Integer> assessList,List<Integer> starNumList) {
		super();
		this.houselist = houselist;
		this.housephotolist = housephotolist;
		this.userlist = userlist;
		this.assessList = assessList;
		this.starNumList=starNumList;
	}
	public List<House> getHouselist() {
		return houselist;
	}
	public void setHouselist(List<House> houselist) {
		this.houselist = houselist;
	}
	public List<HousePhoto> getHousephotolist() {
		return housephotolist;
	}
	public void setHousephotolist(List<HousePhoto> housephotolist) {
		this.housephotolist = housephotolist;
	}
	public List<User> getUserlist() {
		return userlist;
	}
	public void setUserlist(List<User> userlist) {
		this.userlist = userlist;
	}
	public List<Integer> getAssessList() {
		return assessList;
	}
	public void setAssessList(List<Integer> assessList) {
		this.assessList = assessList;
	}

	public List<Integer> getStarNumList() {
		return starNumList;
	}

	public void setStarNumList(List<Integer> starNumList) {
		this.starNumList = starNumList;
	}

	@Override
	public String toString() {
		return "GsonUserCollect{" +
				"houselist=" + houselist +
				", housephotolist=" + housephotolist +
				", userlist=" + userlist +
				", assessList=" + assessList +
				", starNumList=" + starNumList +
				'}';
	}
}
