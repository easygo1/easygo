package com.easygo.model.impl.order;

import java.util.List;

import com.easygo.model.beans.house.House;
import com.easygo.model.beans.order.Orders;
import com.easygo.model.dao.order.IOrderDAO;

public class IOrderDAOImpl implements IOrderDAO {

	@Override
	public boolean addOrders(Orders orders) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delOrders(int order_id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateOrders(int order_id, House house) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Orders> selectAllOrders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Orders findSpecOrdersById(int user_id) {
		// TODO Auto-generated method stub
		return null;
	}

}
