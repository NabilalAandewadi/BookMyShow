package Com.BookMyShow.kafka;


import Com.BookMyShow.dto.PaymentResponseEvent;
import Com.BookMyShow.entity.Booking;
import Com.BookMyShow.entity.BookingStatus;
import Com.BookMyShow.entity.ProcessedEvent;
import Com.BookMyShow.repository.BookingRepository;
import Com.BookMyShow.repository.ProcessedEventRepository;

import Com.BookMyShow.service.SeatLockService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class BookingEventConsumer {

    private final BookingRepository bookingRepository;
    private final ProcessedEventRepository processedRepo;
    private final SeatLockService seatLockService;

    @KafkaListener(topics = "payment-response")
    @Transactional
    public void handlePaymentResponse(PaymentResponseEvent event) {

        if (processedRepo.existsById(event.getEventId())) return;


        Booking booking = bookingRepository.findById(event.getBookingId())
                .orElseThrow();

        if (event.isSuccess()) {
            booking.setStatus(BookingStatus.CONFIRMED);
        } else {
            booking.setStatus(BookingStatus.CANCELLED);
            seatLockService.releaseSeat(String.valueOf(booking.getShowId()));
        }

        bookingRepository.save(booking);
        processedRepo.save(new ProcessedEvent(event.getEventId()));
    }
}
