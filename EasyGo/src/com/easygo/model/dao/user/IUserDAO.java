package com.easygo.model.dao.user;

import java.util.List;

import com.easygo.model.beans.user.User;

public interface IUserDAO {
	// 注册用户
	public abstract boolean addUser(User user);
	// 注册用户,只提供手机号码和密码
	public abstract boolean register(User user);

	// 删除用户(通过账号删除)
	public abstract boolean delUser(int user_no);

	// 修改用户（通过账号修改）
	public abstract boolean updateUser(int user_no, User user);

	// 查找用户（通过账号查询）
	public abstract User findSpecUserByNo(int user_no);


	// 查找用户（通过ID查询）
	public abstract User findSpecUserById(int user_id);

	// 所有用户
	public abstract List<User> selectAllUser();

	// 修改用户头像
	public abstract boolean updateUserPhoto(int user_id, String user_photo);
	//用户进行登录
	public abstract String login(String user_no,String user_password);
}
