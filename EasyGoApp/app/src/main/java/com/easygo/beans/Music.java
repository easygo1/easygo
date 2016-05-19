package com.easygo.beans;

/**
 * Created by Gemptc on 2016/4/27.
 */
public class Music {
    private String title;
    private String singer;

    public Music(String title, String singer) {
        this.title = title;
        this.singer = singer;
    }
    public Music(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }
}
