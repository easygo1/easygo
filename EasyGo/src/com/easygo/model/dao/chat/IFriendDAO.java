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
	public abstract boolean delIFriend(int friend_id);

	// 查找好友
	public abstract boolean selectSpecStudent(int friend_id);
	// 查询所有好友（某个用户的所有好友）
	public abstract List<Friend> selectAllHouse();

}
