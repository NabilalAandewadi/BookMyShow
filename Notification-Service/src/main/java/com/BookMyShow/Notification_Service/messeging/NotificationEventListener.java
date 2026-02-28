package com.BookMyShow.Notification_Service.messeging;



import com.BookMyShow.Notification_Service.service.NotificationService;
import lombok.RequiredArgsConstructor;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationService notificationService;

    @KafkaListener(topics = "booking-confirmed-topic")
    public void bookingConfirmed(var event) {
        notificationService.handleBookingConfirmed(event);
    }

    @KafkaListener(topics = "booking-cancelled-topic")
    public void bookingCancelled(var event) {
        notificationService.handleBookingCancelled(event);
    }

    @KafkaListener(topics = "payment-failed-topic")
    public void paymentFailed(var event) {
        notificationService.handlePaymentFailed(event);
    }
}
