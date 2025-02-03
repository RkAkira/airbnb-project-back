package fr.perpetualmotion.airbnbback.listing.application.service;

import fr.perpetualmotion.airbnbback.listing.application.dto.CreatedListingDTO;
import fr.perpetualmotion.airbnbback.listing.application.dto.DisplayCardListingDTO;
import fr.perpetualmotion.airbnbback.listing.application.dto.SaveListingDTO;
import fr.perpetualmotion.airbnbback.listing.domain.Listing;
import fr.perpetualmotion.airbnbback.listing.mapper.ListingMapper;
import fr.perpetualmotion.airbnbback.listing.repository.ListingRepository;
import fr.perpetualmotion.airbnbback.sharedkernet.service.State;
import fr.perpetualmotion.airbnbback.user.application.dto.ReadUserDTO;
import fr.perpetualmotion.airbnbback.user.application.service.Auth0Service;
import fr.perpetualmotion.airbnbback.user.application.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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
        listing.setLandlordPublicId(userConnected.publicId());

        Listing savedListing = listingRepository.saveAndFlush(listing);
        pictureService.saveAll(saveListingDTO.getPictures(), savedListing);
        auth0Service.addLandlordRoleToUser(userConnected);
        return listingMapper.listingToCreatedListingDTO(savedListing);
    }

    @Transactional(readOnly = true)
    public List<DisplayCardListingDTO> getAllProperties(ReadUserDTO landlord){
        List<Listing> properties = listingRepository.findAllByLandlordPublicIdFetchCoverPicture(landlord.publicId());
        return listingMapper.listingToDisplayCardListingDTOs(properties);
    }

    @Transactional
    public State<UUID, String> delete(UUID publicId, ReadUserDTO landlord){
       long deletedSuccessfully = listingRepository.deleteByPublicIdAndLandlordPublicId(publicId, landlord.publicId());
       if(deletedSuccessfully>0){
           return State.<UUID,String>builder().forSuccess(publicId);
       } else {
           return State.<UUID,String>builder().forUnauthorized("User not authorized");
       }
    }
}
