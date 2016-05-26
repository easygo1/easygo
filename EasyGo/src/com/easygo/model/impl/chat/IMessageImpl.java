package com.easygo.model.impl.chat;

import java.util.List;

import com.easygo.model.beans.chat.Message;
import com.easygo.model.dao.chat.IMessage;

public class IMessageImpl implements IMessage {

	@Override
	public boolean addIFriend(Message message) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delIFriend(int message_sender_id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean selectIFriend(int message_sender_id, Message message) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Message> selectAllHouse() {
		// TODO Auto-generated method stub
		return null;
	}

}
