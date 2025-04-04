package fr.perpetualmotion.airbnbback.listing.presentation;

import fr.perpetualmotion.airbnbback.listing.application.dto.DisplayCardListingDTO;
import fr.perpetualmotion.airbnbback.listing.application.dto.DisplayListingDTO;
import fr.perpetualmotion.airbnbback.listing.application.service.TenantService;
import fr.perpetualmotion.airbnbback.listing.domain.BookingCategory;
import fr.perpetualmotion.airbnbback.sharedkernet.service.State;
import fr.perpetualmotion.airbnbback.sharedkernet.service.StatusNotification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/tenant-listing")
public class TenantResource {

    private final TenantService tenantService;

    public TenantResource(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping("/get-all-by-category")
    public ResponseEntity<Page<DisplayCardListingDTO>> findAllByBookingCategory(Pageable pageable, @RequestParam BookingCategory category) {
        return ResponseEntity.ok(tenantService.getAllByCategory(pageable, category));
    }

    @GetMapping("/get-one")
    public ResponseEntity<DisplayListingDTO> getOne(@RequestParam UUID publicId){
        State<DisplayListingDTO, String> displayListingState = tenantService.getOne(publicId);
        if(displayListingState.getStatus().equals(StatusNotification.OK)){
            return ResponseEntity.ok(displayListingState.getValue());
        } else {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, displayListingState.getError());
            return ResponseEntity.of(problemDetail).build();
        }

    }

}
