package com.fastfur.messaging.producer;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class TwittProducer {

    private Twitter twitter;

    public TwittProducer(){
        TwitterFactory tf= init();
        this.twitter = tf.getInstance();

    }

    public void searchTwitts() throws Exception{
        Query query = new Query("check");
        QueryResult result = twitter.search(query);
        for (Status status : result.getTweets()) {
            System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
        }

    }

    public static void main(String[] args) throws Exception{
        TwittProducer tp = new TwittProducer();
        tp.searchTwitts();
    }
    public TwitterFactory init(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("syLHmMpyVbhkMEq4ua0xqTEEW")
                .setOAuthConsumerSecret("rWbfUOtAT3osmF1ACcJ4gs4AO3NuLPop9PNvdtbv4CZHW3Ccij")
                .setOAuthAccessToken("4537889248-WFl9VLppfc30BxKfjF9BY8y7kVXPuCRp4tSIHGD")
                .setOAuthAccessTokenSecret("N4lWvSFfO2hMdNK1NpzgT6q3GUEd6ai28zz938QxYs5kX");
        TwitterFactory tf = new TwitterFactory( cb.build() );
        return tf;




    }






}
