package com.BookMyShow.Payment_service.dto;

@Data
public class PaymentCommand {
    private UUID bookingId;
    private BigDecimal amount;
}
