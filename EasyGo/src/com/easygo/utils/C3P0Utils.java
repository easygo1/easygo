package com.easygo.utils;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @Author PengHong
 * 
 * @Date 2016-3-23 上午10:30:03
 */
public class C3P0Utils {
	private static ComboPooledDataSource cpds = null;
	private static Connection connection = null;
	static {
		try {
			System.out.println("初始化数据库连接池");
			cpds = new ComboPooledDataSource();
			cpds.setDriverClass("com.mysql.jdbc.Driver");
			cpds.setJdbcUrl("jdbc:mysql://115.29.96.130:3306/easygo");
			cpds.setUser("root");
			cpds.setPassword("123456");
			cpds.setMinPoolSize(5);// 数据库连接池中最少连接数
			cpds.setAcquireIncrement(5);// 当前空闲连接不足时(初始化时的空闲连接数目)，每次自动增加的连接数
			cpds.setMaxPoolSize(20);// 数据库连接池中最多连接数
			cpds.setInitialPoolSize(10);// 初始化的时候连接池中空闲连接的数目
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}

	}

	public static Connection getConnection() {
		try {
			connection = cpds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static void close(ResultSet resultSet, Statement statement,
			Connection connection) {

		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
