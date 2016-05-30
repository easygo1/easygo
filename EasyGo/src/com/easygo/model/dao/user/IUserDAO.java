package com.easygo.model.dao.user;

import java.util.List;

import com.easygo.model.beans.user.User;

public interface IUserDAO {
	// 添加用户
	public abstract boolean addUser(User user);

	// 删除用户(通过账号删除)
	public abstract boolean delUser(int user_no);

	// 修改用户（通过账号修改）
	public abstract boolean updateUser(int user_no, User user);

	// 查找用户（通过账号查询）
	public abstract User findSpecStudent(int user_no);

	// 所有用户
	public abstract List<User> selectAllUser();
	
	//修改用户头像
	public abstract boolean updateUserPhoto(int user_no,String user_photo);
}
