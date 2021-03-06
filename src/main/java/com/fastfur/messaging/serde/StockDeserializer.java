package com.fastfur.messaging.serde;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastfur.messaging.data.Tweet;
import com.fastfur.messaging.producer.stocks.DailyStockData;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class StockDeserializer implements Deserializer<DailyStockData>{

    private static final Logger log = LoggerFactory.getLogger(StockDeserializer.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    public void configure(Map configs, boolean isKey) {

    }

    public DailyStockData deserialize(String topic, byte[] data) {
        DailyStockData retVal = null;

        try {
            if(data!=null){
                retVal = objectMapper.readValue(data, DailyStockData.class);
            }
        } catch (Exception e) {
            log.error("failed to deserialize to child:" , e);
            throw new RuntimeException(e);
        }
        return retVal;
    }

    public void close() {

    }
}
