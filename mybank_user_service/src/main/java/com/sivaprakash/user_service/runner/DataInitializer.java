package com.sivaprakash.user_service.runner;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.sivaprakash.user_service.entity.Customer;
import com.sivaprakash.user_service.entity.User;
import com.sivaprakash.user_service.enums.ProfileStatus;
import com.sivaprakash.user_service.repository.CustomerRepository;
import com.sivaprakash.user_service.repository.UserRepository;

@Component
public class DataInitializer {

    @Bean
    public CommandLineRunner insertDefaultData(UserRepository userRepository, CustomerRepository customerRepository) {
        return args -> {
            System.out.println("----------------------------------------runner -------------------------");

            // Create new users
            User user1 = new User("ABC", "$2a$10$Vyzmq1kFzknARq/CPy1ksekedyrnWavrG6VZTs9E9odRFSMbhn5oC", "abc", "Doe", "abc@example.com", "1234567890");
            User user2 = new User("PQR", "$2a$10$Vyzmq1kFzknARq/CPy1ksekedyrnWavrG6VZTs9E9odRFSMbhn5oC", "pqr", "Smith", "pqr@example.com", "0987654321");
            User user3 = new User("XYZ", "$2a$10$Vyzmq1kFzknARq/CPy1ksekedyrnWavrG6VZTs9E9odRFSMbhn5oC", "xyz", "Brown", "xyz@example.com", "1112223333");
            User user4 = new User("MNO", "$2a$10$Vyzmq1kFzknARq/CPy1ksekedyrnWavrG6VZTs9E9odRFSMbhn5oC", "mno", "Watson", "mno@example.com", "4445556666");
            User user5 = new User("jesse", "$2a$10$Vyzmq1kFzknARq/CPy1ksekedyrnWavrG6VZTs9E9odRFSMbhn5oC", "ijk", "Clark", "ijk@example.com", "7778889999");
System.out.println("userRepository.count()   :"+userRepository.count() );
            // Check if the database already contains users
            if (userRepository.count() == 0) {
                // Save all users in the database
                //userRepository.saveAll(Arrays.asList(user1, user2, user3, user4, user5));
                System.out.println("Dummy users have been added to the database.");

                // Create customers associated with the users
                Customer customer1 = new Customer();
                customer1.setCustomerId("CUST001");
                customer1.setUser(user1);
                customer1.setProfileStatus(ProfileStatus.VERIFIED);

                Customer customer2 = new Customer();
                customer2.setCustomerId("CUST002");
                customer2.setUser(user2);
                customer2.setProfileStatus(ProfileStatus.PENDING);

                Customer customer3 = new Customer();
                customer3.setCustomerId("CUST003");
                customer3.setUser(user3);
                customer3.setProfileStatus(ProfileStatus.VERIFIED);

                Customer customer4 = new Customer();
                customer4.setCustomerId("CUST004");
                customer4.setUser(user4);
                customer4.setProfileStatus(ProfileStatus.VERIFIED);

                Customer customer5 = new Customer();
                customer5.setCustomerId("CUST005");
                customer5.setUser(user5);
                customer5.setProfileStatus(ProfileStatus.PENDING);

                // Save all customers in the database
                //customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3, customer4, customer5));
                System.out.println("Dummy customers have been added to the database.");
            } else {
                System.out.println("Database already contains data. Skipping default data insertion.");
            }

            System.out.println("Default user and customer data insertion completed successfully.");
        };
    }
}
