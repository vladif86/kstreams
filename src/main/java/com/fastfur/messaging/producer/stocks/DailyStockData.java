package com.fastfur.messaging.producer.stocks;


import com.fastfur.messaging.data.Identity;
import com.google.gson.annotations.SerializedName;



public class DailyStockData implements Identity {
    private String day;
    @SerializedName("1. open")
    private double open;
    @SerializedName("2. high")
    private double high;
    @SerializedName("3. low")
    private double low;
    @SerializedName("4. close")
    private double close;
    @SerializedName("5. volume")
    private double volume;


    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public Object getKey() {
        return day;
    }

    @Override
    public String toString() {
        return "DailyStockData{" +
                "day='" + day + '\'' +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", close=" + close +
                ", volume=" + volume +
                '}';
    }
}

/*
"2018-06-15": {
        "1. open": "23.1500",
        "2. high": "23.4500",
        "3. low": "23.0250",
        "4. close": "23.4500",
        "5. volume": "838560"
        },*/

