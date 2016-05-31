package com.easygo.model.dao.news;

import java.util.List;

import com.easygo.model.beans.chat.Comment;

public interface ICommentDAO {
	/**
	 * 对评论进行增删改查
	 */
	// 添加评论
	public abstract boolean addIComment(Comment comment);
	// 删除评论
	public abstract boolean delIComment(int comment_id);
	// 所有评论（某个说说的所有评论）
	public abstract List<Comment> selectAllIComment();

		
}
