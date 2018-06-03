package com.fastfur.messaging.producer;

import com.fastfur.messaging.data.Identity;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class BaseProducer {

    protected Producer producer;


    protected  < K, T extends Identity> void produce(T record, String topic){

        producer.send(new ProducerRecord(topic, record.getKey(), record));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
