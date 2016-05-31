package com.easygo.beans.house;

public class HouseCollect {
    private int house_collect_id;// 无意义
    private int user_id; // 用户ID
    private int house_id; // 收藏房源ID

    public HouseCollect() {
        super();
    }

    public HouseCollect(int house_collect_id, int user_id, int house_id) {
        super();
        this.house_collect_id = house_collect_id;
        this.user_id = user_id;
        this.house_id = house_id;
    }

    public int getHouse_collect_id() {
        return house_collect_id;
    }

    public void setHouse_collect_id(int house_collect_id) {
        this.house_collect_id = house_collect_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getHouse_id() {
        return house_id;
    }

    public void setHouse_id(int house_id) {
        this.house_id = house_id;
    }

}
