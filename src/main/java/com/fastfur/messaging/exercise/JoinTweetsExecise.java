package com.fastfur.messaging.exercise;

import com.fastfur.messaging.data.Tweet;
import com.fastfur.messaging.serde.TweetSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.ValueJoiner;
import org.omg.PortableInterceptor.LOCATION_FORWARD;

import java.util.Properties;

import static com.fastfur.messaging.streaming.DevicesTopology.DEVICE_TOPIC_NAME;
import static com.fastfur.messaging.streaming.DevicesTopology.INPUT_TOPIC_NAME;

public class JoinTweetsExecise {


    public static void main(String[] args) throws Exception {
//        TweetProducer tp = new TweetProducer();
//        tp.produceTweets( INPUT_TOPIC_NAME, Queries.getQueries() );

        Properties config = new Properties();
        config.put( StreamsConfig.APPLICATION_ID_CONFIG, "my-first-tweet-ks1" );
        config.put( StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092" );
        config.put( StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName() );
        config.put( StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, TweetSerde.class );


        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, Tweet> stream = builder.stream( INPUT_TOPIC_NAME, Consumed.with( Serdes.String(), new TweetSerde() ) );
        KStream<String, Tweet> deviceStream = builder.stream( DEVICE_TOPIC_NAME, Consumed.with( Serdes.String(), new TweetSerde() ) );
        KStream<String, Tweet> d= stream
                .join( deviceStream, new ValueJoiner<Tweet, Tweet, Long>() {
                            @Override
                            public Long apply(Tweet tweet, Tweet vt) {
                                return 5l;
                            }
                        });
//        (leftVal,rightVal) -> (leftVal.getCreatedAt() - rightVal)



        KafkaStreams streams = new KafkaStreams( builder.build(), config );
        streams.start();






    }


}
