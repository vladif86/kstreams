package com.fastfur.messaging.serde;

import com.fastfur.messaging.data.Tweet;
import com.fastfur.messaging.producer.stocks.DailyStockData;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class StockSerde implements Serde<DailyStockData>{
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    public void close() {

    }

    public Serializer<DailyStockData> serializer() {
        return new StockSerializer();
    }

    public Deserializer<DailyStockData> deserializer() {
        return new StockDeserializer();
    }
}
