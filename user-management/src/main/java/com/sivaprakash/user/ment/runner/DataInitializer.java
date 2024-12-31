package com.sivaprakash.user.ment.runner;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.sivaprakash.user.ment.entity.User;
import com.sivaprakash.user.ment.repository.UserRepository;

@Component
public class DataInitializer {

    @Bean
    public CommandLineRunner insertDefaultData(UserRepository userRepository) {
        return args -> {
            // Check if the database already contains data to avoid duplicate inserts
            System.out.println("----------------------------------------runner -------------------------");

            // Create new users
            User user1 = new User("ABC", "password123", "abc", "Doe", "abc@example.com", "1234567890");
            User user2 = new User("PQR", "password123", "pqr", "Smith", "pqr@example.com", "0987654321");
            User user3 = new User("XYZ", "password123", "xyz", "Brown", "xyz@example.com", "1112223333");
            User user4 = new User("MNO", "password123", "mno", "Watson", "mno@example.com", "4445556666");
            User user5 = new User("IJK", "password123", "ijk", "Clark", "ijk@example.com", "7778889999");

            // Check if the database already contains users
            if (userRepository.count() == 0) {
                // Save all users in the database
                userRepository.saveAll(Arrays.asList(user1, user2, user3, user4, user5));
                System.out.println("Dummy users have been added to the database.");
            } else {
                System.out.println("Database already contains data. Skipping default data insertion.");
            }

            System.out.println("Default customer data inserted successfully.");
        };
    }
}
