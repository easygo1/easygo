package com.easygo.model.beans.house;

public class Equipment {
	// 房屋设施表 0表示无，1表示有
	private int equipment_id; // 设施编号
	private String equipment_name;	//设施名称
	public Equipment() {
		super();
	}
	public Equipment(int equipment_id, String equipment_name) {
		super();
		this.equipment_id = equipment_id;
		this.equipment_name = equipment_name;
	}
	public int getEquipment_id() {
		return equipment_id;
	}
	public void setEquipment_id(int equipment_id) {
		this.equipment_id = equipment_id;
	}
	public String getEquipment_name() {
		return equipment_name;
	}
	public void setEquipment_name(String equipment_name) {
		this.equipment_name = equipment_name;
	}
	
}
