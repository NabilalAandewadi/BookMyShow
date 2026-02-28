package com.BookMyShow.Notification_Service.dto;



import lombok.Data;
import java.util.UUID;

@Data
public class BookingCancelledEvent {
    private UUID bookingId;
    private String userEmail;
}
