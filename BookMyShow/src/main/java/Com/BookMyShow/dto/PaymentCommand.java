package Com.BookMyShow.dto;


import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCommand {

    private String eventId;
    private Long bookingId;
    private BigDecimal amount;
}
