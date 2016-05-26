package com.easygo.beans.chat_comment;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 王韶辉 on 2016/5/23.
 */
public class CommentData implements Serializable {
    private int comment_imageview;
    private String fabiao_man;
    private String fabiao_time;
    private String browse;
    private String dynamic_content;
    private List<String> imgUrls;
    private int zan_img;
    private String zan_text;
    private int comment_img;
    private String comment_text;
    private int forward_img;
    private String forward_text;
    private int comment_headimg;
    private String comment_name;
    private String comment_content;
    private String comment_time;

    public CommentData(int comment_imageview, String fabiao_man, String fabiao_time, String browse, String dynamic_content, List<String> imgUrls, int zan_img, String zan_text, int comment_img, String comment_text, int forward_img, String forward_text, int comment_headimg, String comment_name, String comment_content, String comment_time) {
        this.comment_imageview = comment_imageview;
        this.fabiao_man = fabiao_man;
        this.fabiao_time = fabiao_time;
        this.browse = browse;
        this.dynamic_content = dynamic_content;
        this.imgUrls = imgUrls;
        this.zan_img = zan_img;
        this.zan_text = zan_text;
        this.comment_img = comment_img;
        this.comment_text = comment_text;
        this.forward_img = forward_img;
        this.forward_text = forward_text;
        this.comment_headimg = comment_headimg;
        this.comment_name = comment_name;
        this.comment_content = comment_content;
        this.comment_time = comment_time;
    }

    public String getComment_time() {
        return comment_time;
    }

    public void setComment_time(String comment_time) {
        this.comment_time = comment_time;
    }

    public int getComment_imageview() {
        return comment_imageview;
    }

    public void setComment_imageview(int comment_imageview) {
        this.comment_imageview = comment_imageview;
    }

    public String getFabiao_man() {
        return fabiao_man;
    }

    public void setFabiao_man(String fabiao_man) {
        this.fabiao_man = fabiao_man;
    }

    public String getFabiao_time() {
        return fabiao_time;
    }

    public void setFabiao_time(String fabiao_time) {
        this.fabiao_time = fabiao_time;
    }

    public String getBrowse() {
        return browse;
    }

    public void setBrowse(String browse) {
        this.browse = browse;
    }

    public String getDynamic_content() {
        return dynamic_content;
    }

    public void setDynamic_content(String dynamic_content) {
        this.dynamic_content = dynamic_content;
    }

    public List<String> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        this.imgUrls = imgUrls;
    }

    public int getZan_img() {
        return zan_img;
    }

    public void setZan_img(int zan_img) {
        this.zan_img = zan_img;
    }

    public String getZan_text() {
        return zan_text;
    }

    public void setZan_text(String zan_text) {
        this.zan_text = zan_text;
    }

    public int getComment_img() {
        return comment_img;
    }

    public void setComment_img(int comment_img) {
        this.comment_img = comment_img;
    }

    public String getComment_text() {
        return comment_text;
    }

    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }

    public int getForward_img() {
        return forward_img;
    }

    public void setForward_img(int forward_img) {
        this.forward_img = forward_img;
    }

    public String getForward_text() {
        return forward_text;
    }

    public void setForward_text(String forward_text) {
        this.forward_text = forward_text;
    }

    public int getComment_headimg() {
        return comment_headimg;
    }

    public void setComment_headimg(int comment_headimg) {
        this.comment_headimg = comment_headimg;
    }

    public String getComment_name() {
        return comment_name;
    }

    public void setComment_name(String comment_name) {
        this.comment_name = comment_name;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }
}
