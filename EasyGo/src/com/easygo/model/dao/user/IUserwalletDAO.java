package com.easygo.model.dao.user;

import com.easygo.model.beans.user.User;
import com.easygo.model.beans.user.Userwallet;

public interface IUserwalletDAO {
	/*
	 * 对用户的支付宝账号进行增、删、改
	 */

	// 添加支付宝账号
	public abstract boolean addUser(Userwallet userwallet);
	// 删除某个用户的支付宝账号
	public abstract boolean delUser(int user_id);
	// 查找某个用户的支付宝账号
	public abstract User findSpecStudent(int user_id);
	
}
