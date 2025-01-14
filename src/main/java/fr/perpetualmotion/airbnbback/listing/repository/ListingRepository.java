package fr.perpetualmotion.airbnbback.listing.repository;

import fr.perpetualmotion.airbnbback.listing.domain.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingRepository extends JpaRepository<Listing, Long> {

}
