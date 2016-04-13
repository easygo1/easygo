package com.easygo.model.impl.user;

import com.easygo.model.beans.user.Hobby;
import com.easygo.model.beans.user.Userhobby;
import com.easygo.model.dao.user.IUserHobbyDAO;

public class IUserHobbyDAOImpl implements IUserHobbyDAO {

	@Override
	public boolean addHobby(Hobby hobby) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteHobby(int hobby_id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addUserHobby(Userhobby userhobby) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUserHobby(int user_id, int hobby_id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Userhobby findUserAllHobbyById(int user_id) {
		// TODO Auto-generated method stub
		return null;
	}

}
