package com.easygo.model.impl.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

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
	@Override
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
			String sql = "insert into orders(house_id,user_id,checknum,checktime,leavetime,total,tel,order_state,order_time,book_name) values(?,?,?,?,?,?,?,?,'"+df.format(new Date())+"',?)";
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
				statement.setString(9, orders.getBook_name());
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
	public boolean updateOrders(int order_id, Orders orders) {
		try {
			statement=connection.prepareStatement("UPDATE orders SET house_id=?,user_id=?,checknum=?,checktime=?,leavetime=?,total=?,tel=?,order_state=?,order_time=?,book_name=? where order_id = ?");
			statement.setInt(1, orders.getHouse_id());
			statement.setInt(2, orders.getHouse_id());
			statement.setInt(3, orders.getHouse_id());
			statement.setString(4, orders.getChecktime());
			statement.setString(5, orders.getLeavetime());
			statement.setDouble(6, orders.getTotal());
			statement.setString(7, orders.getTel());
			statement.setString(8, orders.getOrder_state());
			statement.setString(9, orders.getOrder_time());
			statement.setString(10, orders.getBook_name());
			statement.setInt(11, order_id);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
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
			while(resultSet.next()){
				Orders orders = new Orders(resultSet.getInt(1),resultSet.getInt(2),resultSet.getInt(3),resultSet.getInt(4),resultSet.getString(5),resultSet.getString(6),resultSet.getDouble(7),resultSet.getString(8),resultSet.getString(9),resultSet.getString(10));
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
	public Orders findOrdersByorderid(int order_id){
		Orders orders =null;
		try {
			statement=connection.prepareStatement("select * from orders where order_id=?");
			statement.setInt(1, order_id);
			resultSet= statement.executeQuery();
			while(resultSet.next()){
				orders = new Orders(resultSet.getInt(1),resultSet.getInt(2),resultSet.getInt(3),resultSet.getInt(4),resultSet.getString(5),resultSet.getString(6),resultSet.getDouble(7),resultSet.getString(8),resultSet.getString(9),resultSet.getString(10),resultSet.getString(11));
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			C3P0Utils.close(resultSet, statement, connection);
		}
		return orders;
	}

	@Override
	public List<Orders> findSpecOrdersByUserId(int user_id) {
		List<Orders> orderList= new ArrayList<Orders>();
		Orders orders =null;
		try {
			statement=connection.prepareStatement("select * from orders where user_id=?");
			statement.setInt(1, user_id);
			resultSet= statement.executeQuery();
			while(resultSet.next()){
				orders = new Orders(resultSet.getInt(1),resultSet.getInt(2),resultSet.getInt(3),resultSet.getInt(4),resultSet.getString(5),resultSet.getString(6),resultSet.getDouble(7),resultSet.getString(8),resultSet.getString(9),resultSet.getString(10),resultSet.getString(11));
				orderList.add(orders);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			C3P0Utils.close(resultSet, statement, connection);
		}
		return orderList;
	}

	@Override
	public List<Orders> selectsomeOrders(String orderserch,int cur) {
		List<Orders> orderList= new ArrayList<Orders>();
		if(isNumeric(orderserch)){
			int orderint=Integer.parseInt(orderserch);
			try {
				statement=connection.prepareStatement("select * from orders where order_id=? or user_id=? or house_id=? limit ?,?");
				statement.setInt(1, orderint);
				statement.setInt(2, orderint);
				statement.setInt(3, orderint);
				
				//分页处理
				statement.setInt(4, (cur - 1) * paging.getPageSize());
				statement.setInt(5, paging.getPageSize());
				//分页处理
				resultSet= statement.executeQuery();
				while(resultSet.next()){
					Orders orders = new Orders(resultSet.getInt(1),resultSet.getInt(2),resultSet.getInt(3),resultSet.getInt(4),resultSet.getString(5),resultSet.getString(6),resultSet.getDouble(7),resultSet.getString(8),resultSet.getString(9),resultSet.getString(10));
					orderList.add(orders);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				C3P0Utils.close(resultSet, statement, connection);
			}	
		}
		else{
			try {
				statement=connection.prepareStatement("select * from orders where checktime LIKE ? OR leavetime LIKE ? OR order_state LIKE ?  limit ?,?");
				statement.setString(1, "%"+orderserch+"%");
				statement.setString(2, "%"+orderserch+"%");
				statement.setString(3, "%"+orderserch+"%");
				
				//分页处理
				statement.setInt(4, (cur - 1) * paging.getPageSize());
				statement.setInt(5, paging.getPageSize());
				//分页处理
				resultSet= statement.executeQuery();
				while(resultSet.next()){
					Orders orders = new Orders(resultSet.getInt(1),resultSet.getInt(2),resultSet.getInt(3),resultSet.getInt(4),resultSet.getString(5),resultSet.getString(6),resultSet.getDouble(7),resultSet.getString(8),resultSet.getString(9),resultSet.getString(10));
					orderList.add(orders);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				C3P0Utils.close(resultSet, statement, connection);
			}	
		}
		
		return orderList;
	}
	
	//用正则表达式判断是否是数字  
	public static boolean isNumeric(String str){   
	    Pattern pattern = Pattern.compile("[0-9]*");   
	    return pattern.matcher(str).matches();      
	}

	@Override
	public boolean updateOrderBook(int order_id, String book_name,
			String book_tel) {
		try {
			statement=connection.prepareStatement("UPDATE orders SET tel=?,book_name=? where order_id = ?");
			statement.setString(1, book_tel);
			statement.setString(2, book_name);
			statement.setInt(3, order_id);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
	}

	@Override
	public boolean updateOrderState(int order_id, String order_state) {
		try {
			statement=connection.prepareStatement("UPDATE orders SET order_state=? where order_id = ?");
			statement.setString(1, order_state);
			statement.setInt(2, order_id);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
	}

	@Override
	public List<Orders> findOwnerOrdersByHouseId(int house_id) {
		// TODO Auto-generated method stub
		List<Orders> orderList= new ArrayList<Orders>();
		Orders orders =null;
		try {
			statement=connection.prepareStatement("select * from orders where house_id=?");
			statement.setInt(1, house_id);
			resultSet= statement.executeQuery();
			while(resultSet.next()){
				orders = new Orders(resultSet.getInt(1),resultSet.getInt(2),resultSet.getInt(3),resultSet.getInt(4),resultSet.getString(5),resultSet.getString(6),resultSet.getDouble(7),resultSet.getString(8),resultSet.getString(9),resultSet.getString(10),resultSet.getString(11));
				orderList.add(orders);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			C3P0Utils.close(resultSet, statement, connection);
		}
		return orderList;
	} 
}
