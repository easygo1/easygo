package com.easygo.model.beans.house;

/**
 * @Author PengHong
 * 
 * @Date 2016年6月4日 下午4:02:14
 * 
 *       房屋设施表的补充，只有房屋编号和房屋设施
 */
public class HouseEquipmentName {
	private int house_id; // 房屋编号
	private String equipment_name;

	public HouseEquipmentName(int house_id, String equipment_name) {
		super();
		this.house_id = house_id;
		this.equipment_name = equipment_name;
	}

	public int getHouse_id() {
		return house_id;
	}

	public void setHouse_id(int house_id) {
		this.house_id = house_id;
	}

	public String getEquipment_name() {
		return equipment_name;
	}

	public void setEquipment_name(String equipment_name) {
		this.equipment_name = equipment_name;
	}

}
