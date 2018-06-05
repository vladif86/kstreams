package com.fastfur.messaging.producer;

import com.fastfur.messaging.data.Tweet;
import org.apache.kafka.clients.producer.KafkaProducer;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;
import java.util.Properties;

public class TwittProducer extends BaseProducer{

    private Twitter twitter;

    public TwittProducer(){
        TwitterFactory tf= init();
        this.twitter = tf.getInstance();
        initProps();
        producer = new KafkaProducer(properties);


    }

    public void produceTweets(String topic,String query) throws Exception{
        for(Status status: searchTwitts(query)){
            produce( new Tweet(createUUID(), status), topic );
        }

    }




    public List<Status> searchTwitts(String queryString) throws Exception{
        Query query = new Query(queryString);
        QueryResult result = twitter.search(query);
        return result.getTweets();
    }

    public static void main(String[] args) throws Exception{
        TwittProducer tp = new TwittProducer();
        tp.searchTwitts("q=@realDonaldTrump");
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

    public Properties initProps(){
        super.initProps();
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "com.fastfur.messaging.serde.TweetSerializer");
        return properties;

    }






}
