package com.easygo.beans.user;

import java.io.Serializable;

/**
 * Created by PengHong on 2016/5/18.
 */
public class User implements Serializable{
    // 用户
    private int user_id; // 用户ID
    private String user_no; // 用户账号（唯一）
    private String user_realname; // 用户真实姓名
    private String user_password; // 用户密码
    private String user_nickname; // 用户昵称
    private String user_sex; // 性别'男'，'女'
    private String user_phone; // 手机号11位
    private int user_type; // 用户类型1'房东',2'房客'
    private String user_photo; // 头像//存地址
    private String user_job; // 职业
    private String user_address_province; // 所在省份
    private String user_address_city; // 所在城市
    private String user_mood; // 个性签名15字内
    private String user_mail; // 邮箱
    private String user_introduct; // 个人简介
    private String user_birthday; // 出生日期
    private String user_idcard; // 身份证号
    private String token;//token
    private String remarks;//备注

    public User() {
        super();
    }


    public User(int user_id, String user_no, String user_realname,
                String user_password, String user_nickname, String user_sex,
                String user_phone, int user_type, String user_photo,
                String user_job, String user_address_province,
                String user_address_city, String user_mood, String user_mail,
                String user_introduct, String user_birthday, String user_idcard,
                String token, String remarks) {
        super();
        this.user_id = user_id;
        this.user_no = user_no;
        this.user_realname = user_realname;
        this.user_password = user_password;
        this.user_nickname = user_nickname;
        this.user_sex = user_sex;
        this.user_phone = user_phone;
        this.user_type = user_type;
        this.user_photo = user_photo;
        this.user_job = user_job;
        this.user_address_province = user_address_province;
        this.user_address_city = user_address_city;
        this.user_mood = user_mood;
        this.user_mail = user_mail;
        this.user_introduct = user_introduct;
        this.user_birthday = user_birthday;
        this.user_idcard = user_idcard;
        this.token = token;
        this.remarks = remarks;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_no() {
        return user_no;
    }

    public void setUser_no(String user_no) {
        this.user_no = user_no;
    }

    public String getUser_realname() {
        return user_realname;
    }

    public void setUser_realname(String user_realname) {
        this.user_realname = user_realname;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public String getUser_photo() {
        return user_photo;
    }

    public void setUser_photo(String user_photo) {
        this.user_photo = user_photo;
    }

    public String getUser_job() {
        return user_job;
    }

    public void setUser_job(String user_job) {
        this.user_job = user_job;
    }

    public String getUser_address_province() {
        return user_address_province;
    }

    public void setUser_address_province(String user_address_province) {
        this.user_address_province = user_address_province;
    }

    public String getUser_address_city() {
        return user_address_city;
    }

    public void setUser_address_city(String user_address_city) {
        this.user_address_city = user_address_city;
    }

    public String getUser_mood() {
        return user_mood;
    }

    public void setUser_mood(String user_mood) {
        this.user_mood = user_mood;
    }

    public String getUser_mail() {
        return user_mail;
    }

    public void setUser_mail(String user_mail) {
        this.user_mail = user_mail;
    }

    public String getUser_introduct() {
        return user_introduct;
    }

    public void setUser_introduct(String user_introduct) {
        this.user_introduct = user_introduct;
    }

    public String getUser_birthday() {
        return user_birthday;
    }

    public void setUser_birthday(String user_birthday) {
        this.user_birthday = user_birthday;
    }

    public String getUser_idcard() {
        return user_idcard;
    }

    public void setUser_idcard(String user_idcard) {
        this.user_idcard = user_idcard;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", user_no='" + user_no + '\'' +
                ", user_realname='" + user_realname + '\'' +
                ", user_password='" + user_password + '\'' +
                ", user_nickname='" + user_nickname + '\'' +
                ", user_sex='" + user_sex + '\'' +
                ", user_phone='" + user_phone + '\'' +
                ", user_type=" + user_type +
                ", user_photo='" + user_photo + '\'' +
                ", user_job='" + user_job + '\'' +
                ", user_address_province='" + user_address_province + '\'' +
                ", user_address_city='" + user_address_city + '\'' +
                ", user_mood='" + user_mood + '\'' +
                ", user_mail='" + user_mail + '\'' +
                ", user_introduct='" + user_introduct + '\'' +
                ", user_birthday='" + user_birthday + '\'' +
                ", user_idcard='" + user_idcard + '\'' +
                ", token='" + token + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
