package com.easygo.model.impl.house;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.easygo.model.beans.house.House;
import com.easygo.model.dao.house.IHouseDAO;
import com.easygo.utils.C3P0Utils;
import com.easygo.utils.Paging;

public class IHouseDAOImpl implements IHouseDAO {
	private Connection connection = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;

	Paging paging = new Paging();

	// 将数据处理，计算出需要分成几页
	// 改动的话只需改动查询语句即可
	public int getTotalPage() {
		// 查询语句，查出数据总的记录数
		String table = "house";
		int count = 0;
		count = paging.getTotalPage(table);
		return count;
	}

	@Override
	public boolean addHouse(House house) {
		// TODO Auto-generated method stub
		boolean result = false;
		connection = C3P0Utils.getConnection();
		String sql = "INSERT INTO house(user_id,house_title,house_style,house_address_province,house_address_city,"
				+ "house_address_lng,house_address_lat,house_most_num,house_one_price,house_add_price,house_describe,"
				+ "house_traffic,house_limit_sex,house_stay_time) VALUE(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, house.getUser_id());
			statement.setString(2, house.getHouse_title());
			statement.setString(3, house.getHouse_style());
			statement.setString(4, house.getHouse_address_province());
			statement.setString(5, house.getHouse_address_city());
			statement.setDouble(6, house.getHouse_address_lng());
			statement.setDouble(7, house.getHouse_address_lat());
			statement.setInt(8, house.getHouse_most_num());
			statement.setDouble(9, house.getHouse_one_price());
			statement.setDouble(10, house.getHouse_add_price());
			statement.setString(11, house.getHouse_describe());
			statement.setString(12, house.getHouse_traffic());
			statement.setString(13, house.getHouse_limit_sex());
			statement.setInt(14, house.getHouse_stay_time());
			statement.executeUpdate();
			result = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			result = false;
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}

		return result;
	}

	// 根据房源id删除房源，删除成功返回true,失败返回false
	@Override
	public boolean delHouse(int house_id) {
		// TODO Auto-generated method stub
		connection = C3P0Utils.getConnection();
		String sql = "delete from house where house_id = ?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, house_id);
			statement.executeUpdate();
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
	public boolean updateHouse(int house_id, House house) {
		// TODO Auto-generated method stub
		connection = C3P0Utils.getConnection();
		String sql = "update house set house_title=?,house_describe=?,house_style=?,house_address_province=?,house_address_city=?,house_address_lng=?,house_address_lat=?,house_traffic=?,house_most_num=?,house_one_price=?,house_add_price=?,house_limit_sex=?,house_stay_time=? where house_id=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, house.getHouse_title());
			statement.setString(2, house.getHouse_describe());
			statement.setString(3, house.getHouse_style());
			statement.setString(4, house.getHouse_address_province());
			statement.setString(5, house.getHouse_address_city());
			statement.setDouble(6, house.getHouse_address_lng());
			statement.setDouble(7, house.getHouse_address_lat());
			statement.setString(8, house.getHouse_traffic());
			statement.setInt(9, house.getHouse_most_num());
			statement.setDouble(10, house.getHouse_one_price());
			statement.setDouble(11, house.getHouse_add_price());
			statement.setString(12, house.getHouse_limit_sex());
			statement.setInt(13, house.getHouse_stay_time());
			statement.setInt(14, house_id);
			statement.executeUpdate();
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
	public House findSpecHouseById(int house_id) {
		// TODO Auto-generated method stub
		// List<House> houseList = new ArrayList<House>();
		House house = null;
		connection = C3P0Utils.getConnection();
		String sql = "select * from house where house_id =?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, house_id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int house_id1 = resultSet.getInt(1);
				int user_id = resultSet.getInt(2);
				String house_title = resultSet.getString(3);
				String house_describe = resultSet.getString(4);
				String house_style = resultSet.getString(5);
				String house_address_province = resultSet.getString(6);
				String house_address_city = resultSet.getString(7);
				double house_address_lng = resultSet.getDouble(8);
				double house_address_lat = resultSet.getDouble(9);
				String house_traffic = resultSet.getString(10);
				int house_most_num = resultSet.getInt(11);
				double house_one_price = resultSet.getDouble(12);
				double house_add_price = resultSet.getDouble(13);
				String house_limit_sex = resultSet.getString(14);
				int house_stay_time = resultSet.getInt(15);
				int house_assess_sum = resultSet.getInt(16);
				house = new House(house_id1, user_id, house_title,
						house_describe, house_style, house_address_province,
						house_address_city, house_address_lng,
						house_address_lat, house_traffic, house_most_num,
						house_one_price, house_add_price, house_limit_sex,
						house_stay_time, house_assess_sum, false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
		return house;
	}

	@Override
	public List<House> findSpecHouseByStyle(String house_style) {
		// TODO Auto-generated method stub
		List<House> houseList = new ArrayList<House>();
		connection = C3P0Utils.getConnection();
		String sql = "select * from house where house_style =?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, house_style);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int house_id1 = resultSet.getInt(1);
				int user_id = resultSet.getInt(2);
				String house_title = resultSet.getString(3);
				String house_describe = resultSet.getString(4);
				String house_style1 = resultSet.getString(5);
				String house_address_province = resultSet.getString(6);
				String house_address_city = resultSet.getString(7);
				double house_address_lng = resultSet.getDouble(8);
				double house_address_lat = resultSet.getDouble(9);
				String house_traffic = resultSet.getString(10);
				int house_most_num = resultSet.getInt(11);
				double house_one_price = resultSet.getDouble(12);
				double house_add_price = resultSet.getDouble(13);
				String house_limit_sex = resultSet.getString(14);
				int house_stay_time = resultSet.getInt(15);
				int house_assess_sum = resultSet.getInt(16);
				House house = new House(house_id1, user_id, house_title,
						house_describe, house_style1, house_address_province,
						house_address_city, house_address_lng,
						house_address_lat, house_traffic, house_most_num,
						house_one_price, house_add_price, house_limit_sex,
						house_stay_time, house_assess_sum, false);
				houseList.add(house);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
		return houseList;
	}

	@Override
	public List<House> selectAllHouse() {
		// TODO Auto-generated method stub
		List<House> houseList = new ArrayList<House>();
		connection = C3P0Utils.getConnection();
		String sql = "select * from house";
		try {
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int house_id = resultSet.getInt(1);
				int user_id = resultSet.getInt(2);
				String house_title = resultSet.getString(3);
				String house_describe = resultSet.getString(4);
				String house_style = resultSet.getString(5);
				String house_address_province = resultSet.getString(6);
				String house_address_city = resultSet.getString(7);
				double house_address_lng = resultSet.getDouble(8);
				double house_address_lat = resultSet.getDouble(9);
				String house_traffic = resultSet.getString(10);
				int house_most_num = resultSet.getInt(11);
				double house_one_price = resultSet.getDouble(12);
				double house_add_price = resultSet.getDouble(13);
				String house_limit_sex = resultSet.getString(14);
				int house_stay_time = resultSet.getInt(15);
				int house_assess_sum = resultSet.getInt(16);
				House house = new House(house_id, user_id, house_title,
						house_describe, house_style, house_address_province,
						house_address_city, house_address_lng,
						house_address_lat, house_traffic, house_most_num,
						house_one_price, house_add_price, house_limit_sex,
						house_stay_time, house_assess_sum, false);
				houseList.add(house);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}

		return houseList;
	}

	// 某个地区的所有房源
	@Override
	public List<House> findSpecHouseByCity(String house_address_city, int cur) {
		List<House> houseList = new ArrayList<House>();
		connection = C3P0Utils.getConnection();
		String sql = "select * from house where house_address_city =? limit ?,?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, house_address_city);

			// 分页处理
			statement.setInt(2, (cur - 1) * paging.getPageSize());
			statement.setInt(3, paging.getPageSize());

			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int house_id = resultSet.getInt(1);
				int user_id = resultSet.getInt(2);
				String house_title = resultSet.getString(3);
				String house_describe = resultSet.getString(4);
				String house_style = resultSet.getString(5);
				String house_address_province = resultSet.getString(6);
				String house_address_city2 = resultSet.getString(7);
				double house_address_lng = resultSet.getDouble(8);
				double house_address_lat = resultSet.getDouble(9);
				String house_traffic = resultSet.getString(10);
				int house_most_num = resultSet.getInt(11);
				double house_one_price = resultSet.getDouble(12);
				double house_add_price = resultSet.getDouble(13);
				String house_limit_sex = resultSet.getString(14);
				int house_stay_time = resultSet.getInt(15);
				int house_assess_sum = resultSet.getInt(16);
				House house = new House(house_id, user_id, house_title,
						house_describe, house_style, house_address_province,
						house_address_city2, house_address_lng,
						house_address_lat, house_traffic, house_most_num,
						house_one_price, house_add_price, house_limit_sex,
						house_stay_time, house_assess_sum, false);
				houseList.add(house);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
		return houseList;
	}

	@Override
	public House findSpecHouseByUserId(int user_id) {
		// TODO Auto-generated method stub
		House house = null;
		connection = C3P0Utils.getConnection();
		String sql = "select * from house where user_id =?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, user_id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int house_id = resultSet.getInt(1);
				int user_id1 = resultSet.getInt(2);
				String house_title = resultSet.getString(3);
				String house_describe = resultSet.getString(4);
				String house_style = resultSet.getString(5);
				String house_address_province = resultSet.getString(6);
				String house_address_city = resultSet.getString(7);
				double house_address_lng = resultSet.getDouble(8);
				double house_address_lat = resultSet.getDouble(9);
				String house_traffic = resultSet.getString(10);
				int house_most_num = resultSet.getInt(11);
				double house_one_price = resultSet.getDouble(12);
				double house_add_price = resultSet.getDouble(13);
				String house_limit_sex = resultSet.getString(14);
				int house_stay_time = resultSet.getInt(15);
				int house_assess_sum = resultSet.getInt(16);
				house = new House(house_id, user_id1, house_title,
						house_describe, house_style, house_address_province,
						house_address_city, house_address_lng,
						house_address_lat, house_traffic, house_most_num,
						house_one_price, house_add_price, house_limit_sex,
						house_stay_time, house_assess_sum, false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
		return house;
	}

	@Override
	public int findUseridByHouseid(int house_id) {
		int user_id = 0;
		connection = C3P0Utils.getConnection();
		String sql = "select user_id from house where house_id =?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, house_id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				user_id = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
		return user_id;
	}

	@Override
	public List<House> sortHouse(String house_address_city, int cur,
			String style_limit, String sex_limit, String price_limit,
			String stay_time) {
		// TODO Auto-generated method stub
		List<House> houseList = new ArrayList<House>();
		connection = C3P0Utils.getConnection();

		String sql = "SELECT * FROM house WHERE house_address_city =? AND house_style LIKE ? "
				+ "AND house_limit_sex LIKE ? AND house_stay_time  LIKE ? "
				+ "LIMIT ?,?";

		String sql2 = "SELECT * FROM house WHERE house_address_city =? AND house_style LIKE ? "
				+ "AND house_limit_sex LIKE ? AND house_stay_time  LIKE ? "
				+ "ORDER BY house_one_price LIMIT ?,?";

		String sql3 = "SELECT * FROM house WHERE house_address_city =? AND house_style LIKE ? "
				+ "AND house_limit_sex LIKE ? AND house_stay_time  LIKE ? "
				+ "ORDER BY house_one_price DESC LIMIT ?,?";
		try {
			int Begin = (cur - 1) * 4;
			int num = 4;
			if (price_limit.equals("价格") || price_limit.equals("默认")) {
				System.out.println(price_limit + "--------------");
				System.out.println(style_limit + "--------------");
				System.out.println(sex_limit + "--------------");
				System.out.println(stay_time + "--------------");
				statement = connection.prepareStatement(sql);
				statement.setString(1, house_address_city);
				if (style_limit.equals("类型") || style_limit.equals("全部")) {
					statement.setString(2, "%");
				} else {
					statement.setString(2, style_limit);
				}
				if (sex_limit.equals("性别") || sex_limit.equals("全部")) {
					statement.setString(3, "%");
				} else {
					statement.setString(3, sex_limit);
				}
				if (stay_time.equals("时间") || stay_time.equals("全部")) {
					statement.setString(4, "%");
				} else {
					statement.setString(4, stay_time);
				}
				System.out.println(Begin + "！！！");
				// 分页处理
				statement.setInt(5, Begin);
				// System.out.println((cur - 1) * paging.getPageSize() +
				// "分页是多少了");
				// System.out.println(stay_time + "分页是多少了");
				statement.setInt(6, num);
				// System.out.println(paging.getPageSize() + "分页是多少了");
			} else if (price_limit.equals("低-高")) {
				System.out.println(price_limit + "======低---高");
				System.out.println(style_limit + "======低---高");
				System.out.println(sex_limit + "======低---高");
				System.out.println(stay_time + "-======低---高");
				statement = connection.prepareStatement(sql2);
				statement.setString(1, house_address_city);
				if (style_limit.equals("类型") || style_limit.equals("全部")) {
					statement.setString(2, "%");
				} else {
					statement.setString(2, style_limit);
				}
				if (sex_limit.equals("性别") || sex_limit.equals("全部")) {
					statement.setString(3, "%");
				} else {
					statement.setString(3, sex_limit);
				}
				if (stay_time.equals("时间") || stay_time.equals("全部")) {
					statement.setString(4, "%");
				} else {
					statement.setString(4, stay_time);
				}
				System.out.println("这还没空呀222！！！");
				// 分页处理
				statement.setInt(5, Begin);
				statement.setInt(6, num);
			} else if (price_limit.equals("高-低")) {
				System.out.println(price_limit + "+++++++++");
				System.out.println(style_limit + "+++++++");
				System.out.println(sex_limit + "++++++");
				System.out.println(stay_time + "+++++");
				// 降序
				statement = connection.prepareStatement(sql3);
				statement.setString(1, house_address_city);
				if (style_limit.equals("类型") || style_limit.equals("全部")) {
					statement.setString(2, "%");
				} else {
					statement.setString(2, style_limit);
				}
				if (sex_limit.equals("性别") || sex_limit.equals("全部")) {
					statement.setString(3, "%");
				} else {
					statement.setString(3, sex_limit);
				}
				if (stay_time.equals("时间") || stay_time.equals("全部")) {
					statement.setString(4, "%");
				} else {
					statement.setString(4, stay_time);
				}
				// System.out.println("这还没空呀3333！！！");
				// 分页处理
				statement.setInt(5, Begin);
				statement.setInt(6, num);
			}

			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int house_id = resultSet.getInt(1);
				int user_id = resultSet.getInt(2);
				String house_title = resultSet.getString(3);
				String house_describe = resultSet.getString(4);
				String house_style = resultSet.getString(5);
				String house_address_province = resultSet.getString(6);
				String house_address_city2 = resultSet.getString(7);
				double house_address_lng = resultSet.getDouble(8);
				double house_address_lat = resultSet.getDouble(9);
				String house_traffic = resultSet.getString(10);
				int house_most_num = resultSet.getInt(11);
				double house_one_price = resultSet.getDouble(12);
				double house_add_price = resultSet.getDouble(13);
				String house_limit_sex = resultSet.getString(14);
				int house_stay_time = resultSet.getInt(15);
				int house_assess_sum = resultSet.getInt(16);
				House house = new House(house_id, user_id, house_title,
						house_describe, house_style, house_address_province,
						house_address_city2, house_address_lng,
						house_address_lat, house_traffic, house_most_num,
						house_one_price, house_add_price, house_limit_sex,
						house_stay_time, house_assess_sum, false);
				houseList.add(house);
				// System.out.println(houseList.get(0).getHouse_id() + "取出啦了");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
		return houseList;
	}

	@Override
	public List<House> findSpecHouseByCity(String house_address_city) {
		List<House> houseList = new ArrayList<House>();
		connection = C3P0Utils.getConnection();
		String sql = "select * from house where house_address_city =?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, house_address_city);

			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int house_id = resultSet.getInt(1);
				int user_id = resultSet.getInt(2);
				String house_title = resultSet.getString(3);
				String house_describe = resultSet.getString(4);
				String house_style = resultSet.getString(5);
				String house_address_province = resultSet.getString(6);
				String house_address_city2 = resultSet.getString(7);
				double house_address_lng = resultSet.getDouble(8);
				double house_address_lat = resultSet.getDouble(9);
				String house_traffic = resultSet.getString(10);
				int house_most_num = resultSet.getInt(11);
				double house_one_price = resultSet.getDouble(12);
				double house_add_price = resultSet.getDouble(13);
				String house_limit_sex = resultSet.getString(14);
				int house_stay_time = resultSet.getInt(15);
				int house_assess_sum = resultSet.getInt(16);
				House house = new House(house_id, user_id, house_title,
						house_describe, house_style, house_address_province,
						house_address_city2, house_address_lng,
						house_address_lat, house_traffic, house_most_num,
						house_one_price, house_add_price, house_limit_sex,
						house_stay_time, house_assess_sum, false);
				houseList.add(house);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
		return houseList;
	}

}
