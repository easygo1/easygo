package com.easygo.model.dao.user;


public interface IHobbyDAO {
	/**
	 * 对爱好表的操作
	 */
	//爱好的名称找到爱好的id
	public abstract int SelectHobbyIDByHobbyName(String HobbyName);

    //根据hobbyid查询出hobby的名字
    public abstract String selectNameByHobbyId(int hobby_id);
}
