package fr.perpetualmotion.airbnbback.listing.domain;

import fr.perpetualmotion.airbnbback.sharedkernet.domain.AbstractAuditingEntity;
import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "listing_picture")
public class ListingPicture extends AbstractAuditingEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "listingPictureSequenceGenerator")
    @SequenceGenerator(name = "listingPictureSequenceGenerator", sequenceName = "listingPicture_generator", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "listing_fk", referencedColumnName = "id")
    private Listing listing;

    @Lob
    @Column(name = "file", nullable = false)
    private byte[] file;

    @Column(name = "is_cover", nullable = false)
    private boolean isCover;

    @Column(name = "file_content_type", nullable = false)
    private String fileContentType;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public boolean isCover() {
        return isCover;
    }

    public void setCover(boolean cover) {
        isCover = cover;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListingPicture that = (ListingPicture) o;
        return isCover == that.isCover && Arrays.equals(file, that.file) && Objects.equals(fileContentType, that.fileContentType);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(isCover, fileContentType);
        result = 31 * result + Arrays.hashCode(file);
        return result;
    }

    @Override
    public String toString() {
        return "ListingPicture{" +
                "file=" + Arrays.toString(file) +
                ", isCover=" + isCover +
                ", fileContentType='" + fileContentType + '\'' +
                '}';
    }
}
