package com.easygo.beans.chat_comment;

import java.io.Serializable;

/**
 * Created by 王韶辉 on 2016/5/26.
 */
public class CommentDynamicData implements Serializable {

    private String userphoto;
    private String nickname;
    private int comment_news_id;
    private int comment_user_id;
    private String comment_content;
    private String comment_time;
    private int news_stars;
    private int news_views;

    public CommentDynamicData(int comment_news_id, int comment_user_id, String comment_content) {
        this.comment_news_id = comment_news_id;
        this.comment_user_id = comment_user_id;
        this.comment_content = comment_content;
    }

    public CommentDynamicData( String userphoto,String nickname, String comment_content, String comment_time) {
        this.userphoto = userphoto;
        this.nickname = nickname;
        this.comment_content = comment_content;
        this.comment_time = comment_time;
    }

    public int getNews_views() {
        return news_views;
    }

    public void setNews_views(int news_views) {
        this.news_views = news_views;
    }

    public int getNews_stars() {
        return news_stars;
    }

    public void setNews_stars(int news_stars) {
        this.news_stars = news_stars;
    }

    public String getComment_time() {
        return comment_time;
    }

    public void setComment_time(String comment_time) {
        this.comment_time = comment_time;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserphoto() {
        return userphoto;
    }

    public void setUserphoto(String userphoto) {
        this.userphoto = userphoto;
    }

    public int getComment_news_id() {
        return comment_news_id;
    }

    public void setComment_news_id(int comment_news_id) {
        this.comment_news_id = comment_news_id;
    }

    public int getComment_user_id() {
        return comment_user_id;
    }

    public void setComment_user_id(int comment_user_id) {
        this.comment_user_id = comment_user_id;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }
}
