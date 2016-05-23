package com.easygo.beans;

import java.io.Serializable;

/**
 * Created by PengHong on 2016/5/18.
 */
public class House implements Serializable {
    private int house_id;// 房子编号 主键
    private String house_title; // 房源标题(0-10字)
    private String house_describe;// 房屋描述（100字）
    private String house_style; // 房源类型（客厅沙发，独立房间）
    private Float house_address_lng; // 房源地址（经度）
    private Float house_address_lat; // 房源地址（纬度）
    private String house_traffic; // 交通信息
    private int house_most_num; // 最多入住人数
    private double house_one_price; // 价格（1人）
    private double house_add_price; // 每多一人的价格
    private String house_limit_sex; // 房客性别要求（不限，只男，只女）
    private int house_stay_time; // 最长入住时间
    private int house_assess_sum; // 评价次数（在房东发布房源时不显示）


    private boolean collected = false;

    public House() {
    }

    public House(int house_id, String house_title, String house_describe, String house_style, Float house_address_lng, Float house_address_lat, String house_traffic, int house_most_num, double house_one_price, double house_add_price, String house_limit_sex, int house_stay_time, int house_assess_sum) {
        this.house_id = house_id;
        this.house_title = house_title;
        this.house_describe = house_describe;
        this.house_style = house_style;
        this.house_address_lng = house_address_lng;
        this.house_address_lat = house_address_lat;
        this.house_traffic = house_traffic;
        this.house_most_num = house_most_num;
        this.house_one_price = house_one_price;
        this.house_add_price = house_add_price;
        this.house_limit_sex = house_limit_sex;
        this.house_stay_time = house_stay_time;
        this.house_assess_sum = house_assess_sum;
    }

    public House(String house_describe, String house_style, double house_one_price) {
        this.house_describe = house_describe;
        this.house_style = house_style;
        this.house_one_price = house_one_price;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public int getHouse_id() {
        return house_id;
    }

    public void setHouse_id(int house_id) {
        this.house_id = house_id;
    }

    public String getHouse_title() {
        return house_title;
    }

    public void setHouse_title(String house_title) {
        this.house_title = house_title;
    }

    public String getHouse_describe() {
        return house_describe;
    }

    public void setHouse_describe(String house_describe) {
        this.house_describe = house_describe;
    }

    public String getHouse_style() {
        return house_style;
    }

    public void setHouse_style(String house_style) {
        this.house_style = house_style;
    }

    public Float getHouse_address_lng() {
        return house_address_lng;
    }

    public void setHouse_address_lng(Float house_address_lng) {
        this.house_address_lng = house_address_lng;
    }

    public Float getHouse_address_lat() {
        return house_address_lat;
    }

    public void setHouse_address_lat(Float house_address_lat) {
        this.house_address_lat = house_address_lat;
    }

    public String getHouse_traffic() {
        return house_traffic;
    }

    public void setHouse_traffic(String house_traffic) {
        this.house_traffic = house_traffic;
    }

    public int getHouse_most_num() {
        return house_most_num;
    }

    public void setHouse_most_num(int house_most_num) {
        this.house_most_num = house_most_num;
    }

    public double getHouse_one_price() {
        return house_one_price;
    }

    public void setHouse_one_price(double house_one_price) {
        this.house_one_price = house_one_price;
    }

    public double getHouse_add_price() {
        return house_add_price;
    }

    public void setHouse_add_price(double house_add_price) {
        this.house_add_price = house_add_price;
    }

    public String getHouse_limit_sex() {
        return house_limit_sex;
    }

    public void setHouse_limit_sex(String house_limit_sex) {
        this.house_limit_sex = house_limit_sex;
    }

    public int getHouse_stay_time() {
        return house_stay_time;
    }

    public void setHouse_stay_time(int house_stay_time) {
        this.house_stay_time = house_stay_time;
    }

    public int getHouse_assess_sum() {
        return house_assess_sum;
    }

    public void setHouse_assess_sum(int house_assess_sum) {
        this.house_assess_sum = house_assess_sum;
    }

    @Override
    public String toString() {
        return "House{" +
                "house_id=" + house_id +
                ", house_title='" + house_title + '\'' +
                ", house_describe='" + house_describe + '\'' +
                ", house_style='" + house_style + '\'' +
                ", house_address_lng=" + house_address_lng +
                ", house_address_lat=" + house_address_lat +
                ", house_traffic='" + house_traffic + '\'' +
                ", house_most_num=" + house_most_num +
                ", house_one_price=" + house_one_price +
                ", house_add_price=" + house_add_price +
                ", house_limit_sex='" + house_limit_sex + '\'' +
                ", house_stay_time=" + house_stay_time +
                ", house_assess_sum=" + house_assess_sum +
                '}';
    }
}
