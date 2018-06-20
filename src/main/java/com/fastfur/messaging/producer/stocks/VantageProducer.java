package com.fastfur.messaging.producer.stocks;

import com.fastfur.messaging.producer.BaseProducer;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Map;
import java.util.Properties;

public class VantageProducer extends BaseProducer {

    private static final String VANTAGE_ORIGINAL_TOPIC="vantage";
    private VantageConnector vgConn;

    public VantageProducer(){
        initProps();
        producer = new KafkaProducer(properties);
        vgConn = new VantageConnector();

    }

    public void produceDailyStockValyes (String stock) throws Exception{

        String jsonData = vgConn.getData("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + stock);
//        https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=LPSN&apikey=QZZNHVR9BNNVZW01
        Gson gson = new Gson();


        JsonObject timeSeries = gson.fromJson(jsonData, JsonElement.class).getAsJsonObject().get("Time Series (Daily)").getAsJsonObject();

        for (Map.Entry<String, JsonElement> time : timeSeries.entrySet()) {
            DailyStockData dailyStockData = gson.fromJson(time.getValue().toString(), DailyStockData.class);
            dailyStockData.setDay(time.getKey());
            produce(dailyStockData, VANTAGE_ORIGINAL_TOPIC );
            System.out.println(dailyStockData.toString());
        }

    }


    public static void main(String[] args) throws Exception{
        VantageProducer vantageProducer = new VantageProducer();
        vantageProducer.produceDailyStockValyes("LPSN");


    }



    public Properties initProps(){
        super.initProps();
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "com.fastfur.messaging.serde.StockSerializer");
        return properties;

    }

}
