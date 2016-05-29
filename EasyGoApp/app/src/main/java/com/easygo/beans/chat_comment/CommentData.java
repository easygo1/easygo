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

    public CommentData(int comment_imageview, String fabiao_man, String fabiao_time, String browse, String dynamic_content, List<String> imgUrls) {
        this.comment_imageview = comment_imageview;
        this.fabiao_man = fabiao_man;
        this.fabiao_time = fabiao_time;
        this.browse = browse;
        this.dynamic_content = dynamic_content;
        this.imgUrls = imgUrls;
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


}
