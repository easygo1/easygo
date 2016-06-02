package com.easygo.model.dao.chat;

import java.util.List;

import com.easygo.model.beans.chat.Friend;

public interface IFriendDAO {
	/**
	 * 对好友列表进行增删改 查
	 */
	// 添加好友
	public abstract boolean addIFriend(Friend friend);

	// 删除好友
	public abstract boolean delIFriend(int user_id);

	// 查找好友
	public abstract boolean selectSpecStudent(int user_id);
	//添加好友时进行筛选
	public abstract boolean selectTwoFriend(int user_id1,int user_id2);
	// 查询所有好友（某个用户的所有好友的id）
	public abstract List<Integer> selectAllFriend(int user_id);

}
