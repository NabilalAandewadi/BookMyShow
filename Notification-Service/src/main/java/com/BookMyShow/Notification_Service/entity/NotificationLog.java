package com.BookMyShow.Notification_Service.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationLog {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID bookingId;

    private String type; // EMAIL / SMS

    private String status; // SENT / FAILED

    private LocalDateTime sentAt;
}
