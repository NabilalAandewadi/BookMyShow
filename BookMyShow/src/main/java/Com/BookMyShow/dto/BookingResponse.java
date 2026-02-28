package Com.BookMyShow.dto;

import Com.BookMyShow.entity.BookingStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {

    private Long bookingId;
    private Long userId;
    private Long showId;
    private BookingStatus status;
    private LocalDateTime createdAt;
}
