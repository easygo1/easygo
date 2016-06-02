package com.easygo.model.impl.chat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.easygo.model.beans.chat.Friend;
import com.easygo.model.dao.chat.IFriendDAO;
import com.easygo.utils.C3P0Utils;

public class IFriendDAOImpl implements IFriendDAO {
	//获取连接
	private Connection connection = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;
	
	@Override
	public boolean addIFriend(Friend friend) {
		// TODO Auto-generated method stub
		boolean result = false;
		connection = C3P0Utils.getConnection();
		String sql = "INSERT INTO friend(user_id1,user_id2) VALUE(?,?);";
		try {
			//让其始终保持user_id1<user_id2
			if(friend.getUser_id1()>friend.getUser_id2()){
				int temp=friend.getUser_id1();
				friend.setUser_id1(friend.getUser_id2());
				friend.setUser_id2(temp);
			}
			statement = connection.prepareStatement(sql);
			//向数据库插入数据,添加好友关系
			statement.setInt(1, friend.getUser_id1());
			statement.setInt(2, friend.getUser_id2());
			statement.executeUpdate();
			System.out.println("好友插入成功");
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

	@Override
	public boolean delIFriend(int user_id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean selectSpecStudent(int user_id) {
		// TODO Auto-generated method stub
		return false;
	}
	//添加好友时进行筛选
	@Override
	public boolean selectTwoFriend(int user_id1,int user_id2) {
		// TODO Auto-generated method stub
		boolean result = false;
		connection = C3P0Utils.getConnection();
		String sql = "select * from friend where user_id1=? and user_id2=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, user_id1);
			statement.setInt(2, user_id2);
			//在好友表中查找是否存在这样一条数据
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				//表示已经查找到
				System.out.println("查找成功");
				result=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("查找失败");
			e.printStackTrace();
			result=false;
			
		} finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
		return result;
	}
	//根据某一id查出所有的好友id
	@Override
	public List<Integer> selectAllFriend(int user_id) {
		// TODO Auto-generated method stub
		List<Integer> friend_userid1=new ArrayList<>();
		List<Integer> friend_userid2=new ArrayList<>();
		/*List<Integer> friendList=new ArrayList<>();*/
		connection = C3P0Utils.getConnection();
		String sql1 = "select user_id1 from friend where user_id2 =?";
		String sql2 = "select user_id2 from friend where user_id1 =?";
		try {
			statement = connection.prepareStatement(sql1);
			statement.setInt(1, user_id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				//查找出user_id1中所有的好友集合
				int friend_id1=resultSet.getInt(1);
				friend_userid1.add(friend_id1);
			}
			
			statement = connection.prepareStatement(sql2);
			statement.setInt(1, user_id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				//查找出user_id2中所有的好友集合
				int friend_id2=resultSet.getInt(1);
				friend_userid2.add(friend_id2);
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			C3P0Utils.close(resultSet, statement, connection);
		}
		//将连个集合合并
		//把第二个list集合添加到第一个list集合中  
		friend_userid1.addAll(friend_userid2);
		
		return friend_userid1;
	}

}
