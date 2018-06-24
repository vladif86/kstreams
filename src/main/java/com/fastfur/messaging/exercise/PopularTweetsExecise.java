package com.fastfur.messaging.exercise;

import com.fastfur.messaging.data.Tweet;
import com.fastfur.messaging.serde.TweetSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.kstream.internals.TimeWindow;

import java.util.Properties;

import static com.fastfur.messaging.streaming.DevicesTopology.DEVICE_TOPIC_NAME;
import static com.fastfur.messaging.streaming.DevicesTopology.INPUT_TOPIC_NAME;

public class PopularTweetsExecise {








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

        stream.filter(
                new Predicate<String, Tweet>() {
                    @Override
                    public boolean test(String key, Tweet value) {
                        return value.getFavoriteCount() == 0;
                    }
                });

        KTable<Windowed<String>, Tweet> longSums  =  stream.groupBy( (k,v) -> v.getLanguage())
                .windowedBy( TimeWindows.of( 60000L ).until( 60000L*10 ) )
                .reduce( (v1,v2 ) -> v1.getFavoriteCount() > v2.getFavoriteCount()? v1 : v2 );
        longSums.foreach( (k, v) -> System.out.println( "start -> " + k.window().start() +  "  key -> " + k.key() ) );

        KafkaStreams streams = new KafkaStreams( builder.build(), config );
        streams.start();


    }






}
