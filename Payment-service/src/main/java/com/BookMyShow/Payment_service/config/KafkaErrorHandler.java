package com.BookMyShow.Payment_service.config;

package com.ticketing.payment.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.util.backoff.ExponentialBackOff;
import org.springframework.stereotype.Component;
import org.apache.kafka.common.TopicPartition;

@Slf4j
@Component
public class KafkaErrorHandler {

    public DefaultErrorHandler errorHandler(KafkaTemplate<Object, Object> kafkaTemplate) {

        DeadLetterPublishingRecoverer recoverer =
                new DeadLetterPublishingRecoverer(
                        kafkaTemplate,
                        (record, ex) -> {
                            log.error("Sending to DLT due to: {}", ex.getMessage());
                            return new TopicPartition(record.topic() + ".DLT",
                                    record.partition());
                        });

        ExponentialBackOff backOff = new ExponentialBackOff();
        backOff.setInitialInterval(1000L);   // 1 sec
        backOff.setMultiplier(2.0);          // exponential
        backOff.setMaxInterval(10000L);      // max 10 sec

        DefaultErrorHandler handler =
                new DefaultErrorHandler(recoverer, backOff);

        // Do not retry business logic exceptions
        handler.addNotRetryableExceptions(
                IllegalArgumentException.class,
                IllegalStateException.class
        );

        handler.setRetryListeners((record, ex, attempt) ->
                log.warn("Retry attempt {} for record {}",
                        attempt, record.value())
        );

        return handler;
    }
}
