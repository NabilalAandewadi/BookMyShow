package Com.BookMyShow.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class BookingRequest {
    private Long showId;
    private BigDecimal amount;
}
