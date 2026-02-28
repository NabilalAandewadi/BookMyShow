package com.BookMyShow.Notification_Service.config;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.backoff.FixedBackOff;

@Component
public class KafkaErrorHandler extends DefaultErrorHandler {

    public KafkaErrorHandler() {
        super(new FixedBackOff(2000L, 3));
    }

    @Override
    public void handleRemaining(Exception thrownException,
                                ConsumerRecord<?, ?> record) {

        System.out.println("Kafka Error occurred");
        System.out.println("Topic: " + record.topic());
        System.out.println("Message: " + record.value());
        System.out.println("Exception: " + thrownException.getMessage());

        super.handleRemaining(thrownException, record);
    }
}
