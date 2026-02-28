package Com.BookMyShow.dto;


import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingCreatedEvent {
    private String eventId;
    private Long bookingId;
    private Long userId;
    private Long showId;
    private BigDecimal amount;
}
