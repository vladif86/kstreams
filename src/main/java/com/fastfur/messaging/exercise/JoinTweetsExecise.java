package com.fastfur.messaging.exercise;

import com.fastfur.messaging.data.Tweet;
import com.fastfur.messaging.producer.twitter.TwitterTopics;
import com.fastfur.messaging.serde.TweetSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.omg.PortableInterceptor.LOCATION_FORWARD;

import java.time.Duration;
import java.util.Properties;



public class JoinTweetsExecise {


    /**
     * Get the most popular tweets in each language in the last 10 seconds. Take into account only tweets with more than 10 likes.
     Possible implementation includes: filter, group by, and reduce with timewindow.
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {


        Properties config = new Properties();
        config.put( StreamsConfig.APPLICATION_ID_CONFIG, "my-first-tweet-ks1" );
        config.put( StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092" );
        config.put( StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName() );
        config.put( StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, TweetSerde.class );


        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, Tweet> stream = builder.stream( TwitterTopics.TWITTERS_TOPIC, Consumed.with( Serdes.String(), new TweetSerde() ) );
        KStream<Long, Tweet> deviceStream = builder.stream( TwitterTopics.GOT_RESPONDED_TOPIC, Consumed.with( Serdes.Long(), new TweetSerde() ) );
       stream.filter( (k,v) -> v.getInReponseTo()!= 0 )
               .selectKey( (k,v) -> v.getInReponseTo() )
                .join( deviceStream, new ValueJoiner<Tweet, Tweet, Long>() {
                            @Override
                            public Long apply(Tweet tweet, Tweet vt) {
                                return tweet.getInReponseTo() - vt.getInReponseTo();
                            }
                        }, JoinWindows.of( Duration.ofDays( 30 ).toMillis() ) );



        KafkaStreams streams = new KafkaStreams( builder.build(), config );
        streams.start();







    }


}
