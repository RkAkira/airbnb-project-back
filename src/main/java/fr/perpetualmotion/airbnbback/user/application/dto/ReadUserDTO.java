package fr.perpetualmotion.airbnbback.user.application.dto;

import fr.perpetualmotion.airbnbback.user.domain.Authority;

import java.util.Set;
import java.util.UUID;

public record ReadUserDTO(UUID publicId, String firstName, String lastName, String email, String imageUrl, Set<Authority> authorities) {

}
