package com.easygo.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Paging {
	
	private Connection connection = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;
	int pageSize = 4;// 每页显示几条记录
	int pageNow = 1;// 希望显示第几页
	int rowCount = 0;// 共有几条纪录（查表）
	int pageCount = 0;// 共有几页（计算出得到）

	// 将数据处理，计算出需要分成几页
	//改动的话只需改动查询语句即可
	public int getTotalPage(String table) {
		//查询语句，查出数据总的记录数
		String sql = "select count(*) from "+table;
		int count = 0;
		try {
			connection = C3P0Utils.getConnection();
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				count = resultSet.getInt(1);
			}
			count = (int) Math.ceil((count + 1.0 - 1.0) / pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			C3P0Utils.close(resultSet, statement, connection);
		}	
		return count;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNow() {
		return pageNow;
	}

	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
}
