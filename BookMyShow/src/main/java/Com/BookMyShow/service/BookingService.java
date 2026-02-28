package Com.BookMyShow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ticketing.booking.entity.*;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public Booking createBooking(Long showId, Long userId, BigDecimal amount) {


        Booking booking = new Booking();
        booking.setShowId(showId);
        booking.setUserId(userId);
        booking.setTotalAmount(amount);
        booking.setStatus(BookingStatus.PENDING);

        bookingRepository.save(booking);

        kafkaTemplate.send("booking-created", booking);

        return booking;
    }
}
