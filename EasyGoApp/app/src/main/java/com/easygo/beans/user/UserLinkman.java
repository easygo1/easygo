package com.easygo.beans.user;

import java.io.Serializable;

/**
 * Created by PengHong on 2016/5/24.
 */
public class UserLinkman implements Serializable {
    private int user_linkman_id; // 表编号
    private int user_id; // 用户id
    private String name; // 联系人的姓名
    private String idcard; // 联系人身份证号

    private boolean checked = false;//用来标志当前行数据中的checkbox是否被选中

    public UserLinkman() {
        super();
    }

    public UserLinkman(int user_linkman_id, int user_id, String name,
                       String idcard) {
        super();
        this.user_linkman_id = user_linkman_id;
        this.user_id = user_id;
        this.name = name;
        this.idcard = idcard;
    }

    public int getUser_linkman_id() {
        return user_linkman_id;
    }

    public void setUser_linkman_id(int user_linkman_id) {
        this.user_linkman_id = user_linkman_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
