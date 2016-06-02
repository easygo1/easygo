package com.easygo.model.dao.order;

import java.util.List;

import com.easygo.model.beans.order.Assess;

public interface IAssessDAO {
	// 添加评价
	public abstract boolean addAssess(Assess assess);

	// 删除评价（某个订单的评价）
	public abstract boolean delAssess(int assess_id);

	// 查找评价（通过用户ID）
	public abstract Assess findSpecAssessById(int assess_id);

	// 查找所有评价（某个房源的所有评价）
	public abstract List<Assess> selectAllAssess(int house_id);

}
