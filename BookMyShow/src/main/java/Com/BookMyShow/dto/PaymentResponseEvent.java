package Com.BookMyShow.dto;


import lombok.Data;

@Data
public class PaymentResponseEvent {
    private String eventId;
    private Long bookingId;
    private boolean success;
}
