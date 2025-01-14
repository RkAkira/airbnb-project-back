package fr.perpetualmotion.airbnbback.booking.repository;

import fr.perpetualmotion.airbnbback.booking.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
