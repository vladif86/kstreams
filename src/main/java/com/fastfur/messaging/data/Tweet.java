package com.fastfur.messaging.data;

import twitter4j.Status;

import java.util.Date;

public class Tweet implements Identity {
    private String id;
    private int favoriteCount;
    private int retweetCount;
    private String text;
    private Date createdAt;
    private String language;
    private String source;

    public Tweet(String id, Status status){
        this.id = id;
        this.favoriteCount = status.getFavoriteCount();
        this.retweetCount =status.getRetweetCount();
        this.text = status.getText();
        this.createdAt = status.getCreatedAt();
        this.language = status.getLang();
        this.source= status.getSource();


    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }




    public String getKey() {
        return id;
    }


}
