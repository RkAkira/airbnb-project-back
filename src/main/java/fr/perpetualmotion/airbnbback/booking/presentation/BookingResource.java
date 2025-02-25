package fr.perpetualmotion.airbnbback.booking.presentation;

import fr.perpetualmotion.airbnbback.booking.application.dto.BookedDateDTO;
import fr.perpetualmotion.airbnbback.booking.application.dto.NewBookingDTO;
import fr.perpetualmotion.airbnbback.booking.application.service.BookingService;
import fr.perpetualmotion.airbnbback.sharedkernet.service.State;
import fr.perpetualmotion.airbnbback.sharedkernet.service.StatusNotification;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/booking")
public class BookingResource {

    private final BookingService bookingService;

    public BookingResource(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> create(@Valid @RequestBody NewBookingDTO newBookingDTO) {
        State<Void, String> createState = bookingService.create(newBookingDTO);
        if(createState.getStatus().equals(StatusNotification.ERROR)){
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, createState.getError());
            return ResponseEntity.of(problemDetail).build();
        }
        return ResponseEntity.ok(true);
    }

    @GetMapping("/check_availability")
    public ResponseEntity<List<BookedDateDTO>> checkAvailability(@RequestParam UUID listingPublicId) {
        return ResponseEntity.ok(bookingService.checkAvailability(listingPublicId));
    }
}
