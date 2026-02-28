package com.BookMyShow.Payment_service.kafka;

@Component
@RequiredArgsConstructor
public class PaymentCommandConsumer {

    private final PaymentService paymentService;

    @KafkaListener(topics = "payment.command")
    public void consume(PaymentCommand command) {
        paymentService.processPayment(command);
    }
}
