package com.BookMyShow.Notification_Service.dto;



import lombok.Data;
import java.util.UUID;

@Data
public class BookingConfirmedEvent {
    private UUID bookingId;
    private String userEmail;
}
