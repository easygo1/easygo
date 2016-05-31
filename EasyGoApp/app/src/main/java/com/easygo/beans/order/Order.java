package com.easygo.beans.order;

import java.io.Serializable;

/**
 * Created by 崔凯 on 2016/5/25.
 */
public class Order implements Serializable {
    private String title;
    private String state;
    private String checktime;
    private String leavetime;
    private String sumtime;
    private String type;
    private Double total;
    private int image;

    public Order() {
    }

    public Order(String title, String state, String checktime, String leavetime, String sumtime, String type, Double total, int image) {
        this.title = title;
        this.state = state;
        this.checktime = checktime;
        this.leavetime = leavetime;
        this.sumtime = sumtime;
        this.type = type;
        this.total = total;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getChecktime() {
        return checktime;
    }

    public void setChecktime(String checktime) {
        this.checktime = checktime;
    }

    public String getLeavetime() {
        return leavetime;
    }

    public void setLeavetime(String leavetime) {
        this.leavetime = leavetime;
    }

    public String getSumtime() {
        return sumtime;
    }

    public void setSumtime(String sumtime) {
        this.sumtime = sumtime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
