package fr.perpetualmotion.airbnbback.listing.application.service;

import fr.perpetualmotion.airbnbback.listing.application.dto.DisplayCardListingDTO;
import fr.perpetualmotion.airbnbback.listing.domain.BookingCategory;
import fr.perpetualmotion.airbnbback.listing.domain.Listing;
import fr.perpetualmotion.airbnbback.listing.mapper.ListingMapper;
import fr.perpetualmotion.airbnbback.listing.repository.ListingRepository;
import fr.perpetualmotion.airbnbback.user.application.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TenantService {

    private final ListingRepository listingRepository;

    private final ListingMapper listingMapper;

    private final UserService userService;

    public TenantService(ListingRepository listingRepository, ListingMapper listingMapper, UserService userService) {
        this.listingRepository = listingRepository;
        this.listingMapper = listingMapper;
        this.userService = userService;
    }

    public Page<DisplayCardListingDTO> getAllByCategory(Pageable pageable, BookingCategory category) {
        Page<Listing> allOrBookingCategory;
        if (category == BookingCategory.ALL) {
            allOrBookingCategory = listingRepository.findAllWithCoverOnly(pageable);
        } else {
            allOrBookingCategory = listingRepository.findAllByBookingCategoryWithCoverOnly(pageable, category);
        }
        return allOrBookingCategory.map(listingMapper::listingToDisplayCardListingDTO);
    }
}
