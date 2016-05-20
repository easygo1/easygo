package com.easygo.beans;

/**
 * Created by PengHong on 2016/5/18.
 */
public class HouseAndUser {
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

    
}
