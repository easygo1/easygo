package com.easygo.model.dao.order;

import java.util.List;

import com.easygo.model.beans.order.UserOrderLinkman;

public interface IUserOrderLinkmanDAO {
	/*
	 * 对用户（订单中）的联系人进行增删
	 */

	// 添加某个用户联系人
	public abstract boolean addUserOrderLinkman(
			UserOrderLinkman userOrderLinkman);

	// 删除某个用户的联系人
	public abstract boolean delUserOrderLinkman(int user_linkman_id);

	// 根据订单号查询该订单的全部入住人信息
	 public abstract List<UserOrderLinkman> selectUserOrderLinkmanByOrderid(int order_id);

}
