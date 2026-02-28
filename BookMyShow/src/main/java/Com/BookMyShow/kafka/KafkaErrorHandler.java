package Com.BookMyShow.kafka;



import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.util.backoff.ExponentialBackOff;

@Slf4j
@Configuration
public class KafkaErrorHandler {

    @Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate<Object, Object> kafkaTemplate) {

        // Dead Letter Topic logic
        DeadLetterPublishingRecoverer recoverer =
                new DeadLetterPublishingRecoverer(kafkaTemplate,
                        (record, ex) -> {

                            log.error("Sending message to DLT due to error: {}",
                                    ex.getMessage());

                            return new org.apache.kafka.common.TopicPartition(
                                    record.topic() + ".DLT",
                                    record.partition()
                            );
                        });

        // Exponential Retry Strategy
        ExponentialBackOff backOff = new ExponentialBackOff();
        backOff.setInitialInterval(1000L);   // 1 second
        backOff.setMultiplier(2.0);          // double every retry
        backOff.setMaxInterval(10000L);      // max 10 sec

        DefaultErrorHandler errorHandler =
                new DefaultErrorHandler(recoverer, backOff);

        // Do NOT retry business exceptions
        errorHandler.addNotRetryableExceptions(
                IllegalArgumentException.class,
                IllegalStateException.class
        );

        // Log retry attempts
        errorHandler.setRetryListeners((record, ex, deliveryAttempt) ->
                log.warn("Retry attempt {} for record {} due to {}",
                        deliveryAttempt,
                        record.value(),
                        ex.getMessage())
        );

        return errorHandler;
    }
}
