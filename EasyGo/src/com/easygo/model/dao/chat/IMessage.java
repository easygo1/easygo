package com.easygo.model.dao.chat;

import java.util.List;

import com.easygo.model.beans.chat.Friend;
import com.easygo.model.beans.chat.Message;

public interface IMessage {
	/**
	 * 对聊天记录列表进行增删改查
	 */
	// 添加一条聊天记录
	public abstract boolean addIFriend(Message message);

	// 删除一条聊天记录
	public abstract boolean delIFriend(int message_sender_id);

	// 查找一条聊天记录
	public abstract boolean selectIFriend(int message_sender_id, Message message);

	// 查找某人的所有聊天记录
	public abstract List<Message> selectAllHouse();

}
