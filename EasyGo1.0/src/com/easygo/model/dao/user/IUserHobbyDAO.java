package com.easygo.model.dao.user;

import com.easygo.model.beans.user.Userhobby;

public interface IUserHobbyDAO {
	/*
	 * 对用户的兴趣爱好进行增、删、查
	 */

	// 添加爱好
	public abstract boolean addHobby(Userhobby userhobby);

	// 删除某个爱好(通过账号删除)
	public abstract boolean delHobby(int user_hobby_id);

	// 查找某个用户的所有爱好
	public abstract Userhobby findAllHobbyById(int user_id);

}
