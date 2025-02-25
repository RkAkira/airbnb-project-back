package fr.perpetualmotion.airbnbback.booking.application.service;

import fr.perpetualmotion.airbnbback.booking.application.dto.BookedDateDTO;
import fr.perpetualmotion.airbnbback.booking.application.dto.NewBookingDTO;
import fr.perpetualmotion.airbnbback.booking.domain.Booking;
import fr.perpetualmotion.airbnbback.booking.mapper.BookingMapper;
import fr.perpetualmotion.airbnbback.booking.repository.BookingRepository;
import fr.perpetualmotion.airbnbback.listing.application.dto.ListingCreateBookingDTO;
import fr.perpetualmotion.airbnbback.listing.application.service.LandLordService;
import fr.perpetualmotion.airbnbback.sharedkernet.service.State;
import fr.perpetualmotion.airbnbback.user.application.dto.ReadUserDTO;
import fr.perpetualmotion.airbnbback.user.application.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final UserService userService;
    private final LandLordService landLordService;

    public BookingService(BookingRepository bookingRepository, BookingMapper bookingMapper, UserService userService, LandLordService landLordService) {
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
        this.userService = userService;
        this.landLordService = landLordService;
    }

    @Transactional
    public State<Void, String> create(NewBookingDTO newBookingDTO){
        Booking booking = bookingMapper.newBookingToBooking(newBookingDTO);
        Optional<ListingCreateBookingDTO> listingOpt = landLordService.getListingPublicId(newBookingDTO.listingPublicId());

        if (listingOpt.isEmpty()) {
            return State.<Void, String>builder().forError("Landlord PUBLIC ID not found");
        }
        boolean alreadyBooked = bookingRepository.bookingExistAtInterval(newBookingDTO.startDate(), newBookingDTO.endDate(), newBookingDTO.listingPublicId());
        if (alreadyBooked) {
            return State.<Void, String>builder().forError("Booking already exists");
        }
        ListingCreateBookingDTO listingCreateBookingDTO =  listingOpt.get();

        booking.setFkListing(listingCreateBookingDTO.listingPublicId());

        ReadUserDTO connectedUser = userService.getAuthenticatedUserFromSecurityContext();
        booking.setFkTenant(connectedUser.publicId());
        booking.setNbOfTravelers(1);

        long numberOfNights = ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate());
        booking.setTotalPrice((int) numberOfNights*listingCreateBookingDTO.price().value());
        bookingRepository.save(booking);
        return State.<Void, String>builder().forSuccess();
    }

    public List<BookedDateDTO> checkAvailability(UUID publicId){
        return bookingRepository.findAllByFkListing(publicId)
                .stream()
                .map(bookingMapper::bookingToCheckAvailability).toList();
    }
}
