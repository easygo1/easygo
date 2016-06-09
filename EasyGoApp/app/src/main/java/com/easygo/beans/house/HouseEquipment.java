package com.easygo.beans.house;

public class HouseEquipment {
    private int house_equipment_id;// 房屋设施表ID
    private int house_id; // 房屋编号
    private int equipment_id; // 设施名称

    public HouseEquipment() {
        super();
    }

    public HouseEquipment(int house_equipment_id, int house_id, int equipment_id) {
        super();
        this.house_equipment_id = house_equipment_id;
        this.house_id = house_id;
        this.equipment_id = equipment_id;
    }

    public int getHouse_equipment_id() {
        return house_equipment_id;
    }

    public void setHouse_equipment_id(int house_equipment_id) {
        this.house_equipment_id = house_equipment_id;
    }

    public int getHouse_id() {
        return house_id;
    }

    public void setHouse_id(int house_id) {
        this.house_id = house_id;
    }

    public int getEquipment_id() {
        return equipment_id;
    }

    public void setEquipment_id(int equipment_id) {
        this.equipment_id = equipment_id;
    }

}
