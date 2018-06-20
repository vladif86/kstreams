package com.fastfur.messaging.serde;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastfur.messaging.producer.stocks.DailyStockData;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class StockSerializer implements Serializer<DailyStockData> {
    private static final Logger log = LoggerFactory.getLogger(StockSerializer.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    public byte[] serialize(String topic, DailyStockData data) {

        byte[] retVal;
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
