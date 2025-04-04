package fr.perpetualmotion.airbnbback.listing.repository;

import fr.perpetualmotion.airbnbback.listing.domain.BookingCategory;
import fr.perpetualmotion.airbnbback.listing.domain.Listing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ListingRepository extends JpaRepository<Listing, Long> {

    @Query("SELECT listing FROM Listing listing LEFT JOIN FETCH listing.pictures picture"+
    " WHERE listing.landlordPublicId = :landlordPublicId AND picture.isCover = true")
    List<Listing> findAllByLandlordPublicIdFetchCoverPicture(UUID landlordPublicId);

    long deleteByPublicIdAndLandlordPublicId(UUID publicId, UUID landlordPublicId);

    @Query("SELECT listing FROM Listing listing LEFT JOIN FETCH listing.pictures picture"+
    " WHERE picture.isCover = true AND listing.bookingCategory = :category")
    Page<Listing> findAllByBookingCategoryWithCoverOnly(Pageable pageable, BookingCategory
                                                         category);

    @Query("SELECT listing FROM Listing listing LEFT JOIN FETCH listing.pictures picture"+
            " WHERE picture.isCover = true")
    Page<Listing> findAllWithCoverOnly(Pageable pageable);

    Optional<Listing> findByPublicId(UUID publicId);


}
