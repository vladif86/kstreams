package com.fastfur.messaging.topologys;

import com.fastfur.messaging.data.Tweet;
import com.fastfur.messaging.producer.TwittProducer;
import com.fastfur.messaging.serde.TweetSerde;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.KeyValueMapper;

import java.util.Properties;

import static com.fastfur.messaging.ExampleTopology1.INPUT_TOPIC_NAME;
import static org.omg.IOP.TAG_ORB_TYPE.value;

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
        KTable<String,Long> kstream = stream.peek( (k,v) -> System.out.println( k + "!!!" + v ) )
                .selectKey((k,v) -> v.getSource())
                .peek( (k,v) -> System.out.println( k + "!!!" + v ) )
                .groupByKey().count();
        kstream.toStream().foreach( (k,v) -> System.out.println( k + "!!!" + v ) );

        KafkaStreams streams = new KafkaStreams(builder.build(),config);
        streams.start();








    }




}
