package fr.perpetualmotion.airbnbback.listing.repository;

import fr.perpetualmotion.airbnbback.listing.domain.ListingPicture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingPictureRepository extends JpaRepository<ListingPicture, Long> {
}
