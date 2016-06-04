package com.easygo.beans.gson;

import com.easygo.beans.house.House;
import com.easygo.beans.house.HousePhoto;
import com.easygo.beans.order.Orders;
import com.easygo.beans.order.UserOrderLinkman;
import com.easygo.beans.user.User;

import java.io.Serializable;
import java.util.List;

public class GsonOrderInfoAllDetail implements Serializable{
	/**
	 * 
	 */
	//订单详情
	//订单对象，房客，房主，房源，房源主图，订单入住人（list）
    private Orders orders;
    private User book_user;
    private User house_user;
    private House house;
    private HousePhoto housephoto;
    private List<UserOrderLinkman> userorderlinkmanlist;
    
	public GsonOrderInfoAllDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GsonOrderInfoAllDetail(Orders orders, User book_user,
			User house_user, House house, HousePhoto housephoto,
			List<UserOrderLinkman> userorderlinkmanlist) {
		super();
		this.orders = orders;
		this.book_user = book_user;
		this.house_user = house_user;
		this.house = house;
		this.housephoto = housephoto;
		this.userorderlinkmanlist = userorderlinkmanlist;
	}

	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	public User getBook_user() {
		return book_user;
	}

	public void setBook_user(User book_user) {
		this.book_user = book_user;
	}

	public User getHouse_user() {
		return house_user;
	}

	public void setHouse_user(User house_user) {
		this.house_user = house_user;
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

	public List<UserOrderLinkman> getUserorderlinkmanlist() {
		return userorderlinkmanlist;
	}

	public void setUserorderlinkmanlist(List<UserOrderLinkman> userorderlinkmanlist) {
		this.userorderlinkmanlist = userorderlinkmanlist;
	}

	@Override
	public String toString() {
		return "GsonOrderInfoAllDetail [orders=" + orders + ", book_user="
				+ book_user + ", house_user=" + house_user + ", house=" + house
				+ ", housephoto=" + housephoto + ", userorderlinkmanlist="
				+ userorderlinkmanlist + "]";
	}

	
}
