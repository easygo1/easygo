package com.easygo.beans.chat_comment;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 王韶辉 on 2016/6/7.
 */
public class CommentDynamic implements Serializable {
    private int news_sender_id;// 发送人id
    private String news_content;// 发送内容
    private List<String> photo_path;

    public CommentDynamic(int news_sender_id, String news_content, List<String> photo_path) {
        this.news_sender_id = news_sender_id;
        this.news_content = news_content;
        this.photo_path = photo_path;
    }

    public int getNews_sender_id() {
        return news_sender_id;
    }

    public void setNews_sender_id(int news_sender_id) {
        this.news_sender_id = news_sender_id;
    }

    public String getNews_content() {
        return news_content;
    }

    public void setNews_content(String news_content) {
        this.news_content = news_content;
    }

    public List<String> getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(List<String> photo_path) {
        this.photo_path = photo_path;
    }
}
