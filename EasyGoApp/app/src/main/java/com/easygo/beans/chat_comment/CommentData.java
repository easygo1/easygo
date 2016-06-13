package com.easygo.beans.chat_comment;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 王韶辉 on 2016/5/23.
 */
public class CommentData implements Serializable {
    private int news_id;

    private String user_photo;
    private String user_nickname;
    private String news_content;
    private String news_time;
    private int news_stars;
    private int news_views;
    private List<String> photo_path;


    public CommentData(int news_id,String user_photo, String user_nickname, String news_content, String news_time, int news_stars, int news_views, List<String> photo_path) {
        this.news_id = news_id;
        this.user_photo = user_photo;
        this.user_nickname = user_nickname;
        this.news_content = news_content;
        this.news_time = news_time;
        this.news_stars = news_stars;
        this.news_views = news_views;
        this.photo_path = photo_path;
    }





    public int getNews_id() {
        return news_id;
    }

    public void setNews_id(int news_id) {
        this.news_id = news_id;
    }

    public String getUser_photo() {
        return user_photo;
    }

    public void setUser_photo(String user_photo) {
        this.user_photo = user_photo;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getNews_content() {
        return news_content;
    }

    public void setNews_content(String news_content) {
        this.news_content = news_content;
    }

    public String getNews_time() {
        return news_time;
    }

    public void setNews_time(String news_time) {
        this.news_time = news_time;
    }

    public int getNews_stars() {
        return news_stars;
    }

    public void setNews_stars(int news_stars) {
        this.news_stars = news_stars;
    }

    public int getNews_views() {
        return news_views;
    }

    public void setNews_views(int news_views) {
        this.news_views = news_views;
    }

    public List<String> getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(List<String> photo_path) {
        this.photo_path = photo_path;
    }

    @Override
    public String toString() {
        return "CommentData{" +
                "news_id=" + news_id +
                ", user_photo='" + user_photo + '\'' +
                ", user_nickname='" + user_nickname + '\'' +
                ", news_content='" + news_content + '\'' +
                ", news_time='" + news_time + '\'' +
                ", news_stars=" + news_stars +
                ", news_views=" + news_views +
                ", photo_path=" + photo_path +
                '}';
    }
}
