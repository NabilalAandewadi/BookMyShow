package com.BookMyShow.Payment_service.kafka;

@Component
@RequiredArgsConstructor
public class PaymentEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishEvent(Payment payment) {
        PaymentResponseEvent event = new PaymentResponseEvent(
                payment.getBookingId(),
                payment.getStatus().name()
        );

        kafkaTemplate.send("payment.response", event);
    }
}
