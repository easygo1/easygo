package com.easygo.model.dao.user;

import java.util.List;

import com.easygo.model.beans.user.Hobby;
import com.easygo.model.beans.user.Userhobby;

public interface IUserHobbyDAO {
	/*
	 * 对用户的兴趣爱好进行增、删、查
	 */

	
	// 添加爱好
	public abstract boolean addHobby(Hobby hobby);
	//删除某个爱好
	public abstract boolean deleteHobby(int hobby_id);
	//添加某个用户的爱好
	public abstract boolean addUserHobby(Userhobby userhobby);
	// 删除某个爱好(通过账号删除)
	public abstract boolean deleteUserHobby(int user_id,int hobby_id);
	// 查找某个用户的所有爱好
	public abstract List<Userhobby> findUserAllHobbyById(int user_id);
	//向表中插入用户的爱好
	public abstract boolean inssertUserHobby(int user_id,int hobby_id);
	//查询表中是否存在用户的某个爱好
    public abstract boolean selectExistUserHobby(int user_id,int hobby_id);
    //删除全部的该用户的爱好
    public abstract boolean DeleteUserHobbyByUserId(int user_id);
    //得到用户的所有爱好
    public abstract List<Integer> selectAllUserHobbyById(int user_id);

}
