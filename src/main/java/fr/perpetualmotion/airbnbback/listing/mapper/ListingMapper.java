package fr.perpetualmotion.airbnbback.listing.mapper;

import fr.perpetualmotion.airbnbback.listing.application.dto.*;
import fr.perpetualmotion.airbnbback.listing.application.dto.sub.ListingInfoDTO;
import fr.perpetualmotion.airbnbback.listing.application.dto.vo.PriceVO;
import fr.perpetualmotion.airbnbback.listing.domain.Listing;
import fr.perpetualmotion.airbnbback.listing.domain.ListingPicture;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ListingPictureMapper.class})
public interface ListingMapper {

    @Mapping(target = "landlordPublicId", ignore = true )
    @Mapping(target = "publicId", ignore = true )
    @Mapping(target = "lastModifiedDate", ignore = true )
    @Mapping(target = "id", ignore = true )
    @Mapping(target = "createdDate", ignore = true )
    @Mapping(target = "pictures", ignore = true )
    @Mapping(target = "title", source = "description.title.value")
    @Mapping(target = "description", source = "description.description.value")
    @Mapping(target = "bedrooms", source = "infos.bedrooms.value")
    @Mapping(target = "guest", source = "infos.guests.value")
    @Mapping(target = "bookingCategory", source = "category")
    @Mapping(target = "beds", source = "infos.beds.value")
    @Mapping(target = "bathrooms", source = "infos.baths.value")
    @Mapping(target = "price", source = "price.value")
    Listing saveListingDTOToListing(SaveListingDTO saveListingDTO);

    CreatedListingDTO listingToCreatedListingDTO(Listing listing);

    @Mapping(target="cover", source = "pictures")
    List<DisplayCardListingDTO> listingToDisplayCardListingDTOs(List<Listing> listings);

    @Mapping(target="cover", source = "pictures", qualifiedByName = "extract-cover")
    @Mapping(target = "price", source = "listing.price")
    @Mapping(target = "location", source = "listing.location")
    DisplayCardListingDTO listingToDisplayCardListingDTO(Listing listing);

    default PriceVO mapPriceToPriceVO(int price){
        return new PriceVO(price);
    }

    @Mapping(target="landlord", ignore = true )
    @Mapping(target = "description.title.value", source = "title")
    @Mapping(target = "description.description.value", source = "description")
    @Mapping(target = "info.bedrooms.value", source = "bedrooms")
    @Mapping(target = "info.guests.value", source = "guest")
    @Mapping(target = "info.beds.value", source = "beds")
    @Mapping(target = "info.baths.value", source = "bathrooms")
    @Mapping(target = "category", source = "bookingCategory")
    @Mapping(target = "price.value", source = "price")
    DisplayListingDTO listingToDisplayListingDTO(Listing listing);

    @Mapping(target = "listingPublicId", source = "publicId")
    @Mapping(target = "price.value", source = "price")
    ListingCreateBookingDTO mapListingToListingCreateBookingDTO(Listing listing);

}
