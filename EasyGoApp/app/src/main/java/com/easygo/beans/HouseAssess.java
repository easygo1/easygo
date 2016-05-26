package com.easygo.beans;

import java.io.Serializable;

/**
 * 评价实体，测试用的
 * Created by zzia on 2016/5/24.
 */
public class HouseAssess implements Serializable{
    private int assesspersonphoto;
    private String assesspersonName;//评论人的姓名
    private int stars;//评论星级
    private String assesscontent;//评论内容
    private String assesstime;//入住时间
    private String assessreply;//房东的回复

    public HouseAssess() {
        super();
    }

    public HouseAssess( String assesspersonName, int stars, String assesscontent, String assesstime, String assessreply) {
        this.assesspersonName = assesspersonName;
        this.stars = stars;
        this.assesscontent = assesscontent;
        this.assesstime = assesstime;
        this.assessreply = assessreply;
    }

    public String getAssesspersonName() {
        return assesspersonName;
    }

    public void setAssesspersonName(String assesspersonName) {
        this.assesspersonName = assesspersonName;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getAssesscontent() {
        return assesscontent;
    }

    public void setAssesscontent(String assesscontent) {
        this.assesscontent = assesscontent;
    }

    public String getAssesstime() {
        return assesstime;
    }

    public void setAssesstime(String assesstime) {
        this.assesstime = assesstime;
    }

    public int getAssesspersonphoto() {
        return assesspersonphoto;
    }

    public void setAssesspersonphoto(int assesspersonphoto) {
        this.assesspersonphoto = assesspersonphoto;
    }

    @Override
    public String toString() {
        return "HouseAssess{" +
                "assesspersonphoto=" + assesspersonphoto +
                ", assesspersonName='" + assesspersonName + '\'' +
                ", stars=" + stars +
                ", assesscontent='" + assesscontent + '\'' +
                ", assesstime='" + assesstime + '\'' +
                '}';
    }

    public String getAssessreply() {
        return assessreply;
    }

    public void setAssessreply(String assessreply) {
        this.assessreply = assessreply;
    }
}
