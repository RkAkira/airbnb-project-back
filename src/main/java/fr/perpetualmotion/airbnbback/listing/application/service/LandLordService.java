package fr.perpetualmotion.airbnbback.listing.application.service;

import fr.perpetualmotion.airbnbback.listing.application.dto.CreatedListingDTO;
import fr.perpetualmotion.airbnbback.listing.application.dto.SaveListingDTO;
import fr.perpetualmotion.airbnbback.listing.domain.Listing;
import fr.perpetualmotion.airbnbback.listing.mapper.ListingMapper;
import fr.perpetualmotion.airbnbback.listing.repository.ListingRepository;
import fr.perpetualmotion.airbnbback.user.application.dto.ReadUserDTO;
import fr.perpetualmotion.airbnbback.user.application.service.Auth0Service;
import fr.perpetualmotion.airbnbback.user.application.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class LandLordService {

    private final ListingRepository listingRepository;
    private final ListingMapper listingMapper;
    private final UserService userService;
    private final Auth0Service auth0Service;
    private final PictureService pictureService;

    public LandLordService(ListingRepository listingRepository, ListingMapper listingMapper, UserService userService, Auth0Service auth0Service, PictureService pictureService) {
        this.listingRepository = listingRepository;
        this.listingMapper = listingMapper;
        this.userService = userService;
        this.auth0Service = auth0Service;
        this.pictureService = pictureService;
    }

    public CreatedListingDTO create(SaveListingDTO saveListingDTO){
        Listing listing = listingMapper.saveListingDTOToListing(saveListingDTO);
        ReadUserDTO userConnected = userService.getAuthenticatedUserFromSecurityContext();
        listing.setLandlordPublicID(userConnected.publicId());

        Listing savedListing = listingRepository.saveAndFlush(listing);
        pictureService.saveAll(saveListingDTO.getPictures(), savedListing);
        auth0Service.addLandlordRoleToUser(userConnected);
        return listingMapper.listingToCreatedListingDTO(savedListing);
    }
}
