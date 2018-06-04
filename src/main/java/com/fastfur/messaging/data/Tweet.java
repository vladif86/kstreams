package com.fastfur.messaging.data;

import twitter4j.Status;

public class Tweet implements Identity {
    public String id;
    public Status status;

    public Tweet(String id, Status status){
        this.id = id;
        this.status = status;
    }


    public String getKey() {
        return id;
    }
}
