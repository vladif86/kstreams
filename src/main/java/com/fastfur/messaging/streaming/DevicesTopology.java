package com.fastfur.messaging.streaming;

import com.fastfur.messaging.data.Tweet;
import com.fastfur.messaging.producer.TwittProducer;
import com.fastfur.messaging.serde.TweetSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

import java.util.Properties;

import static com.fastfur.messaging.streaming.BranchTopology.INPUT_TOPIC_NAME;

public class DevicesTopology {

    public DevicesTopology() {
    }

    public static void main(String[] args) throws Exception{
        TwittProducer tp = new TwittProducer();
        tp.produceTweets(INPUT_TOPIC_NAME, "q=realDonaldTrump");
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "my-first-tweet-ks1");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.70.57:9092");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, TweetSerde.class);


        StreamsBuilder builder = new StreamsBuilder();
        KStream<String,Tweet> stream = builder.stream(INPUT_TOPIC_NAME, Consumed.with(Serdes.String(), new TweetSerde()));
        KTable<Tweet.DEVICES,Long> kstream = stream
                .selectKey((k,v) -> v.getDevice())
                .groupByKey()
                .count();
        kstream.toStream().foreach( (k,v) -> System.out.println( "Device-> " + k + "number -> " + v ) );

        KafkaStreams streams = new KafkaStreams(builder.build(),config);
        streams.start();


    }

}
