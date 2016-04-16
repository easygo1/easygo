package com.easygo.model.dao.order;

import java.util.List;

import com.easygo.model.beans.house.House;
import com.easygo.model.beans.order.Orders;

public interface IOrderDAO {

	/**
	 * 
	 * 对订单进行增删改查
	 */
	
	// 添加订单
	public abstract boolean addOrders(Orders orders);
	// 删除订单
	public abstract boolean delOrders(int order_id);
	// 修改订单
	public abstract boolean updateOrders(int order_id, House house);
	// 查找所有订单
	public abstract List<Orders> selectAllOrders(int cur);
	// 查找某个用户的订单
	public abstract Orders findSpecOrdersById(int user_id);
	//关闭

	public abstract int getTotalPage();
	
}
