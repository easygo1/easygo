package com.easygo.model.beans.gson;

import java.util.List;

import com.easygo.model.beans.order.Assess;

/**
 * @Author PengHong
 * 
 * @Date 2016年6月10日 下午5:44:20
 */
public class GsonAboutHouseAssess {
	private List<Assess> allAssessList;
	private List<String> userName;
	private List<String> userPhoto;

	public GsonAboutHouseAssess() {
		super();
	}

	public GsonAboutHouseAssess(List<Assess> allAssessList,
			List<String> userName, List<String> userPhoto) {
		super();
		this.allAssessList = allAssessList;
		this.userName = userName;
		this.userPhoto = userPhoto;
	}

	public List<Assess> getAllAssessList() {
		return allAssessList;
	}

	public void setAllAssessList(List<Assess> allAssessList) {
		this.allAssessList = allAssessList;
	}

	public List<String> getUserName() {
		return userName;
	}

	public void setUserName(List<String> userName) {
		this.userName = userName;
	}

	public List<String> getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(List<String> userPhoto) {
		this.userPhoto = userPhoto;
	}

}
