package com.BookMyShow.Notification_Service.service;





import com.BookMyShow.Notification_Service.entity.NotificationLog;
import com.BookMyShow.Notification_Service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final EmailService emailService;
    private final SmsService smsService;
    private final NotificationRepository repository;

    public void handleBookingConfirmed(var event) {

        emailService.sendEmail(event.getUserEmail(),
                "Booking Confirmed",
                "Your booking is successful.");

        repository.save(NotificationLog.builder()
                .bookingId(event.getBookingId())
                .type("EMAIL")
                .status("SENT")
                .sentAt(LocalDateTime.now())
                .build());
    }

    public void handleBookingCancelled(var event) {

        emailService.sendEmail(event.getUserEmail(),
                "Booking Cancelled",
                "Your booking was cancelled.");

        repository.save(NotificationLog.builder()
                .bookingId(event.getBookingId())
                .type("EMAIL")
                .status("SENT")
                .sentAt(LocalDateTime.now())
                .build());
    }

    public void handlePaymentFailed(var event) {

        smsService.sendSms(event.getPhoneNumber(),
                "Payment failed for booking " + event.getBookingId());
    }
}
