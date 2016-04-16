package com.easygo.model.impl.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.easygo.model.beans.house.House;
import com.easygo.model.beans.order.Orders;
import com.easygo.model.dao.order.IOrderDAO;
import com.easygo.utils.C3P0Utils;
import com.easygo.utils.Paging;

public class IOrderDAOImpl implements IOrderDAO {

	private Connection connection = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;
	Paging paging=new Paging();
	
	

	// 将数据处理，计算出需要分成几页
	//改动的话只需改动查询语句即可
	public int getTotalPage() {
		//查询语句，查出数据总的记录数
		String table = "orders";
		int count = 0;
		count=paging.getTotalPage(table);
		return count;
	}
	
	public IOrderDAOImpl() {
		connection = C3P0Utils.getConnection();
	}

	@Override
	public boolean addOrders(Orders orders) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
			//添加一个订单到数据库中
			String sql = "insert into orders(house_id,user_id,checknum,checktime,leavetime,total,tel,order_state,order_time) values(?,?,?,?,?,?,?,?,'"+df.format(new Date())+"')";
			try {
				statement = connection.prepareStatement(sql);
				statement.setInt(1, orders.getHouse_id());
				statement.setInt(2, orders.getUser_id());
				statement.setInt(3, orders.getChecknum());
				statement.setString(4, orders.getChecktime());
				statement.setString(5, orders.getLeavetime());
				statement.setDouble(6, orders.getTotal());
				statement.setString(7, orders.getTel());
				statement.setString(8, orders.getOrder_state());
			
				statement.executeUpdate();
				System.out.println("插入数据成功");
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} finally {
				C3P0Utils.close(resultSet, statement, connection);
			}
	}

	@Override
	public boolean delOrders(int order_id) {
		try {
			statement=connection.prepareStatement("delete from orders where order_id = ?");
			statement.setInt(1, order_id);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateOrders(int order_id, House house) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Orders> selectAllOrders(int cur) {
		List<Orders> orderList= new ArrayList<Orders>();
		try {
			statement=connection.prepareStatement("select * from orders where 1 limit ?,?");
			//分页处理
			statement.setInt(1, (cur - 1) * paging.getPageSize());
			statement.setInt(2, paging.getPageSize());
			//分页处理
			resultSet= statement.executeQuery();
			System.out.println("IOrderDAOImpl里面。。。");
			while(resultSet.next()){
				Orders orders = new Orders(resultSet.getInt(1),resultSet.getInt(2),resultSet.getInt(3),resultSet.getInt(4),resultSet.getString(5),resultSet.getString(6),resultSet.getDouble(7),resultSet.getString(8),resultSet.getString(9),resultSet.getString(10));
				System.out.println(orders);
				orderList.add(orders);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			C3P0Utils.close(resultSet, statement, connection);
		}	
		return orderList;
	}

	@Override
	public Orders findSpecOrdersById(int user_id) {
		Orders order = new Orders();
		return null;
	}
}
