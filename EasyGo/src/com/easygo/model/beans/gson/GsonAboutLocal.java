package com.easygo.model.beans.gson;

import java.util.List;

import com.easygo.model.beans.house.HousePhoto;

/**
 * @Author PengHong
 * 
 * @Date 2016年6月15日 下午6:13:31
 */
public class GsonAboutLocal {
	private List<Integer> localList;
	private List<HousePhoto> housePhotoList;

	public GsonAboutLocal() {
		super();
	}

	public GsonAboutLocal(List<Integer> localList,
			List<HousePhoto> housePhotoList) {
		super();
		this.localList = localList;
		this.housePhotoList = housePhotoList;
	}

	public List<Integer> getLocalList() {
		return localList;
	}

	public void setLocalList(List<Integer> localList) {
		this.localList = localList;
	}

	public List<HousePhoto> getHousePhotoList() {
		return housePhotoList;
	}

	public void setHousePhotoList(List<HousePhoto> housePhotoList) {
		this.housePhotoList = housePhotoList;
	}

}
