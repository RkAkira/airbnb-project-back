package fr.perpetualmotion.airbnbback.listing.application.dto;

import fr.perpetualmotion.airbnbback.listing.application.dto.vo.PriceVO;

import java.util.UUID;

public record ListingCreateBookingDTO(
        UUID listingPublicId, PriceVO price
) {
}
