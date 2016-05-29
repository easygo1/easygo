package com.easygo.beans.chat_comment;

import java.io.Serializable;

/**
 * Created by 王韶辉 on 2016/5/26.
 */
public class CommentDynamicData implements Serializable {
    private int headImgUrl;
    private String commentName;
    private String commentContent;
    private String commentTime;

    public CommentDynamicData(int headImgUrl, String commentName, String commentContent, String commentTime) {
        this.headImgUrl = headImgUrl;
        this.commentName = commentName;
        this.commentContent = commentContent;
        this.commentTime = commentTime;
    }

    public int getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(int headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getCommentName() {
        return commentName;
    }

    public void setCommentName(String commentName) {
        this.commentName = commentName;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }
}
