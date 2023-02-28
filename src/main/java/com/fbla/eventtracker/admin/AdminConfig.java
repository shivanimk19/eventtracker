package com.fbla.eventtracker.admin;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
/**
 * This class configures the admin database table with some default records.
 */
@Configuration
public class AdminConfig {
    @Bean
    CommandLineRunner commandLineRunner2(AdminRepository repository){
        return args -> {
            Admin admin = new Admin(
                    "Admin FN",
                    "Admin LN",
                    "1234",
                    "admin@commack.k12.ny.us"
            );

            repository.saveAll(
                    List.of(admin)
            );
        };
    }
}
