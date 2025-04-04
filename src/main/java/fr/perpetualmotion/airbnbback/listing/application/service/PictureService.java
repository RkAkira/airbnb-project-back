package fr.perpetualmotion.airbnbback.listing.application.service;


import fr.perpetualmotion.airbnbback.listing.application.dto.sub.PictureDTO;
import fr.perpetualmotion.airbnbback.listing.domain.Listing;
import fr.perpetualmotion.airbnbback.listing.domain.ListingPicture;
import fr.perpetualmotion.airbnbback.listing.mapper.ListingMapper;
import fr.perpetualmotion.airbnbback.listing.mapper.ListingPictureMapper;
import fr.perpetualmotion.airbnbback.listing.repository.ListingPictureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PictureService {

    private final ListingPictureRepository listingPictureRepository;

    private final ListingPictureMapper listingPictureMapper;

    public PictureService(ListingPictureRepository listingPictureRepository, ListingPictureMapper listingPictureMapper) {
        this.listingPictureRepository = listingPictureRepository;
        this.listingPictureMapper = listingPictureMapper;
    }

    public List<PictureDTO> saveAll(List<PictureDTO> pictures, Listing listing){
        Set<ListingPicture> listingPictures = listingPictureMapper.pictureDTOToListingPicture(pictures);
        boolean isFirst = true;
        for (ListingPicture listingPicture: listingPictures){
            listingPicture.setCover(isFirst);
            listingPicture.setListing(listing);
            isFirst = false;
        }
        listingPictureRepository.saveAll(listingPictures);
        return listingPictureMapper.listingPictureToPictureDTO(listingPictures.stream().toList());
    }
}
