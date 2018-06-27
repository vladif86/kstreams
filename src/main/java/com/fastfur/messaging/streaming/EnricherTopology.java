package com.fastfur.messaging.streaming;

public class EnricherTopology {
    public static void main(String[] args) {
        //        TweetProducer tp = new TweetProducer();
//        tp.produceTweets( INPUT_TOPIC_NAME, Queries.getQueries() );

//        Properties config = new Properties();
//        config.put( StreamsConfig.APPLICATION_ID_CONFIG, "my-first-tweet-ks1" );
//        config.put( StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092" );
//        config.put( StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName() );
//        config.put( StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, TweetSerde.class );
//
//
//        StreamsBuilder builder = new StreamsBuilder();
//        KStream<String, Tweet> stream = builder.stream( INPUT_TOPIC_NAME, Consumed.with( Serdes.String(), new TweetSerde() ) );
//
//        stream.filter( (k,v)-> v.getInReponseTo()!= null )
//
//
//        KTable<String, Long> deviceKtable = deviceStream.groupByKey().count();
//
//
//        deviceKtable.foreach( (k, v) -> System.out.println( "Device-> " + k + "  number -> " + v ) );
//
//        KafkaStreams streams = new KafkaStreams( builder.build(), config );
//        streams.start();

    }
}
