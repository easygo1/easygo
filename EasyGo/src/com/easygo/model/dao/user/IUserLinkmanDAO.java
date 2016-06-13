package com.easygo.model.dao.user;

import java.util.List;

import com.easygo.model.beans.user.UserLinkman;

public interface IUserLinkmanDAO {
	/*
	 * 对用户的联系人进行增删改查
	 */

	// 添加某个用户联系人
	public abstract boolean addUserLinkman(UserLinkman userlinkman);

	// 删除某个用户的一个联系人
	public abstract boolean delUserLinkman(int user_linkman_id);

	// 删除某个用户的所有联系人
	public abstract boolean delAllUserLinkman(int user_id);

	// 修改某个用户的联系人
	public abstract boolean updateUserLinkman(UserLinkman userlinkman);

	// 查找某个用户的所有联系人（通过某个用户的id）
	public abstract List<UserLinkman> findAllUserLinkmanById(int user_id);

	// 添加某个用户联系人并且返回当前sql中的id
	public abstract int addUserLinkmanReturnId(UserLinkman userlinkman);
}
