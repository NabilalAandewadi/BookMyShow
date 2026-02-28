package Com.BookMyShow.repository;



import Com.BookMyShow.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookingRepository extends JpaRepository<Booking, Long> {

}
