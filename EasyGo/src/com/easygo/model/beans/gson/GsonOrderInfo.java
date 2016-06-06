package com.easygo.model.beans.gson;



import java.io.Serializable;
import java.util.List;

import com.easygo.model.beans.house.House;
import com.easygo.model.beans.house.HousePhoto;
import com.easygo.model.beans.order.Orders;

/**
 * Created by 崔凯 on 2016/6/4.
 */
public class GsonOrderInfo implements Serializable {
    private List<Orders> mOrdersList;
    private List<House> mHouseList;
    private List<HousePhoto> mHousePhotoList;
    
    public GsonOrderInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GsonOrderInfo(List<Orders> ordersList, List<House> houseList, List<HousePhoto> housePhotoList) {
        super();
        this.mOrdersList = ordersList;
        this.mHouseList = houseList;
        this.mHousePhotoList = housePhotoList;
    }

    public List<Orders> getOrdersList() {
        return mOrdersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        mOrdersList = ordersList;
    }

    public List<House> getHouseList() {
        return mHouseList;
    }

    public void setHouseList(List<House> houseList) {
        mHouseList = houseList;
    }

    public List<HousePhoto> getHousePhotoList() {
        return mHousePhotoList;
    }

    public void setHousePhotoList(List<HousePhoto> housePhotoList) {
        mHousePhotoList = housePhotoList;
    }

    @Override
    public String toString() {
        return "GsonOrderInfo{" +
                "mOrdersList=" + mOrdersList +
                ", mHouseList=" + mHouseList +
                ", mHousePhotoList=" + mHousePhotoList +
                '}';
    }
}
