package fr.perpetualmotion.airbnbback.listing.application.dto.sub;

import fr.perpetualmotion.airbnbback.listing.application.dto.vo.DescriptionVO;
import fr.perpetualmotion.airbnbback.listing.application.dto.vo.TitleVO;
import jakarta.validation.constraints.NotNull;

public record DescriptionDTO(
        @NotNull TitleVO title,
        @NotNull DescriptionVO description) {
}
