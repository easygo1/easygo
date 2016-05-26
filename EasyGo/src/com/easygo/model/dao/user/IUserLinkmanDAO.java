package com.easygo.model.dao.user;

import com.easygo.model.beans.user.UserLinkman;

public interface IUserLinkmanDAO {
	/*
	 * 对用户的联系人进行增删改查
	 */

	// 添加某个用户联系人
	public abstract boolean addUserLinkman(UserLinkman userlinkman);

	// 删除某个用户的联系人
	public abstract boolean delUserLinkman(int user_linkman_id);

	// 修改某个用户的联系人
	public abstract boolean updateUserLinkman(int user_linkman_id,
			UserLinkman userlinkman);

	// 查找某个用户的所有联系人（通过某个用户的id）
	public abstract UserLinkman findAllUserLinkmanById(int user_id);
}
