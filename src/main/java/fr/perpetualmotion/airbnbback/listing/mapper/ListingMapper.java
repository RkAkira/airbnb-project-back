package fr.perpetualmotion.airbnbback.listing.mapper;

import fr.perpetualmotion.airbnbback.listing.domain.ListingPicture;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ListingPictureMapper.class})
public interface ListingMapper {
}
