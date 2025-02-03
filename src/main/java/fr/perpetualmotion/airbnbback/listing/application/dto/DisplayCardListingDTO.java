package fr.perpetualmotion.airbnbback.listing.application.dto;

import fr.perpetualmotion.airbnbback.listing.application.dto.sub.PictureDTO;
import fr.perpetualmotion.airbnbback.listing.application.dto.vo.PriceVO;
import fr.perpetualmotion.airbnbback.listing.domain.BookingCategory;

import java.util.UUID;

public record DisplayCardListingDTO(PriceVO priceVo, String Location,
                                    PictureDTO cover, BookingCategory bookingCategory,
                                    UUID publicId) {

}
