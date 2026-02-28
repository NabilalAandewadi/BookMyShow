package com.BookMyShow.Payment_service.dto;

import java.util.UUID;

@Data
public class RefundCommand {
    private UUID bookingId;
}
