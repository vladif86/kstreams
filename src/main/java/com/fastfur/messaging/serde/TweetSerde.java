package com.fastfur.messaging.serde;

import com.fastfur.messaging.data.Tweet;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class TweetSerde implements Serde<Tweet{
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    public void close() {

    }

    public Serializer<Tweet> serializer() {
        return new TweetSerializer();
    }

    public Deserializer<Tweet> deserializer() {
        return new TweetDeserializer();
    }
}
