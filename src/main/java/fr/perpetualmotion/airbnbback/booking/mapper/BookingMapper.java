package fr.perpetualmotion.airbnbback.booking.mapper;

import fr.perpetualmotion.airbnbback.booking.application.dto.BookedDateDTO;
import fr.perpetualmotion.airbnbback.booking.application.dto.NewBookingDTO;
import fr.perpetualmotion.airbnbback.booking.domain.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(target = "fkListing", source = "listingPublicId")
    Booking newBookingToBooking(NewBookingDTO newBookingDTO);

    BookedDateDTO bookingToCheckAvailability(Booking booking);
}
