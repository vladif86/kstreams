package com.fastfur.messaging.serde;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastfur.messaging.data.Tweet;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class TweetSerializer implements Serializer<Tweet> {
    private static final Logger log = LoggerFactory.getLogger(TweetSerializer.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    public byte[] serialize(String topic, Tweet data) {

        byte[] retVal = null;
        try {
            retVal = objectMapper.writeValueAsString(data).getBytes();
        } catch (Exception e) {
            log.error("failed to serialize child" , e);
            throw new RuntimeException(e);
        }
        return retVal;
    }

    public void close() {

    }
}
