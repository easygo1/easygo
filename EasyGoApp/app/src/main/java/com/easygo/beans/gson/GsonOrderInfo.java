package com.easygo.beans.gson;

import com.easygo.beans.house.House;
import com.easygo.beans.house.HousePhoto;
import com.easygo.beans.order.Orders;

import java.io.Serializable;

/**
 * Created by 崔凯 on 2016/6/4.
 */
public class GsonOrderInfo implements Serializable {
    private Orders orders;
    private House house;
    private HousePhoto housephoto;

    public GsonOrderInfo() {
        super();
    }

    public GsonOrderInfo(Orders orders, House house, HousePhoto housephoto) {
        this.orders = orders;
        this.house = house;
        this.housephoto = housephoto;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public HousePhoto getHousephoto() {
        return housephoto;
    }

    public void setHousephoto(HousePhoto housephoto) {
        this.housephoto = housephoto;
    }

    @Override
    public String toString() {
        return "GsonOrderInfo{" +
                "orders=" + orders +
                ", house=" + house +
                ", housephoto=" + housephoto +
                '}';
    }
}
