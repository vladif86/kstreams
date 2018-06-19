package com.fastfur.messaging.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import twitter4j.Status;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.UUID;
import java.util.function.Predicate;



public class Tweet implements Identity {

    @JsonProperty
    private String id;
    private int favoriteCount;
    private int retweetCount;
    private String text;
    private Date createdAt;
    private String language;
    private String source;

    private String rawTweetJson;
    private long tweet_id;
    private static final JsonPath TWEET_PARSER = JsonPath.compile("$");

    /**
     * This consturcture for using Twitter client without Twitter4j
     * @param rawTweetJson
     */
    public Tweet(String rawTweetJson) {
        this.rawTweetJson = rawTweetJson;
        this.id = UUID.randomUUID().toString();

        //Naive get of values:
        LinkedHashMap tweet = TWEET_PARSER.read(rawTweetJson);
        this.tweet_id = (long) tweet.getOrDefault("id",-1);
        this.text = (String) tweet.getOrDefault("text","");
        this.source = (String) tweet.getOrDefault("source","");
        this.language = (String) tweet.getOrDefault("lang","");
        this.favoriteCount = (int) tweet.getOrDefault("favorite_count",-1);
        this.retweetCount = (int) tweet.getOrDefault("retweet_count",-1);
        DateFormat df = new SimpleDateFormat("EEE MMM d HH:mm:ss Z yyyy");
        try {
            this.createdAt = df.parse((String) tweet.getOrDefault("created_at",""));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    protected String createUUID() {
        String uuid  = UUID.randomUUID().toString();
        return uuid;
    }

    public Tweet(){}

    public Tweet(String id, Status status){
        this.id = id;
        this.favoriteCount = status.getFavoriteCount();
        this.retweetCount  = status.getRetweetCount();
        this.text      = status.getText();
        this.createdAt = status.getCreatedAt();
        this.language  = status.getLang();
        this.source    = status.getSource();
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


    @Override
    public String toString() {
        return "Tweet{" +
                "id='" + id + '\'' +
                ", favoriteCount=" + favoriteCount +
                ", retweetCount=" + retweetCount +
                ", text='" + text + '\'' +
                ", createdAt=" + createdAt +
                ", language='" + language + '\'' +
                ", source='" + source + '\'' +
                '}';
    }

    @JsonIgnore
    public String getKey() {
        return id;
    }

    public static Predicate<Tweet> iphoneSource() {
        return tweet -> tweet.getSource().contains("iPhone");
    }

    public static Predicate<Tweet> androidSource() {
        return tweet -> tweet.getSource().contains("Android");
    }

    public  Predicate<Tweet> ipadSource() {
        return tweet -> tweet.getSource().contains("Ipad");
    }


    public  Predicate<Tweet> webSource() {
        return tweet -> tweet.getSource().contains("Web");
    }


}
