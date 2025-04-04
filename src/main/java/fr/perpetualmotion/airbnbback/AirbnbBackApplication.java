package fr.perpetualmotion.airbnbback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("fr.perpetualmotion.airbnbback.booking.repository")
public class AirbnbBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirbnbBackApplication.class, args);
    }

}
