package fr.perpetualmotion.airbnbback.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories({"fr.perpetualmotion.airbnbback.user.repository",
        "fr.perpetualmotion.airbnbback.listing.repository", "fr.perpetualmotion.airbnbback.booking.domain" })
@EnableTransactionManagement
@EnableJpaAuditing
public class DatabaseConfiguration {
}
