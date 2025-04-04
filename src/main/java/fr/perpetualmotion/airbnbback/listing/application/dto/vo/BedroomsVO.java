package fr.perpetualmotion.airbnbback.listing.application.dto.vo;

import jakarta.validation.constraints.NotNull;

public record BedroomsVO(@NotNull(message = "Bedrooms value must be present") int value) {
}
