package fr.perpetualmotion.airbnbback.user.repository;

import fr.perpetualmotion.airbnbback.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.expression.spel.support.ReflectivePropertyAccessor;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByEmail(String email);

    Optional<User> findOneByPublicId(UUID publicId);

}
