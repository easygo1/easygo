package com.easygo.model.dao.news;

import java.util.List;

import com.easygo.model.beans.chat.Reply;
import com.easygo.model.beans.house.House;

public interface IReplyDAO {
	/**
	 * 对评论的回复进行增删改查
	 */
	// 添加回复
	public abstract boolean addReply(Reply reply);
	// 删除回复
	public abstract boolean delReply(int reply_id);
	// 所有回复（某个评论的所有评论）
	public abstract List<Reply> selectAllReply();
	
}
