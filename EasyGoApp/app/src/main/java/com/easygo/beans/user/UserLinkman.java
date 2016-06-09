package com.easygo.beans.user;

import java.io.Serializable;

public class UserLinkman implements Serializable{
    private int user_linkman_id; // 表编号
    private int user_id; // 用户id
    private String linkman_name; // 联系人的姓名
    private String idcard; // 联系人身份证号
    private boolean checked;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public UserLinkman() {
        super();
    }

    public UserLinkman(int user_linkman_id, int user_id, String linkman_name,
                       String idcard) {
        super();
        this.user_linkman_id = user_linkman_id;
        this.user_id = user_id;
        this.linkman_name = linkman_name;
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

    public String getLinkman_name() {
        return linkman_name;
    }

    public void setLinkman_name(String linkman_name) {
        this.linkman_name = linkman_name;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

}
