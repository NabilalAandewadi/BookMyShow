package Com.BookMyShow.controller;


import Com.BookMyShow.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public Object book(@RequestParam Long showId,
                       @RequestParam Long userId,
                       @RequestParam BigDecimal amount) {
        return bookingService.createBooking(showId, userId, amount);
    }
}
