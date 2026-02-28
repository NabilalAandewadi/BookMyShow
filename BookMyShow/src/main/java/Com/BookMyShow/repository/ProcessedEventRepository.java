package Com.BookMyShow.repository;

import Com.BookMyShow.entity.Booking;
import Com.BookMyShow.entity.ProcessedEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessedEventRepository extends JpaRepository<ProcessedEvent, String> {

}