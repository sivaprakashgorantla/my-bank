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
	        User user1 = new User(
	            "ABC", "$2a$10$Vyzmq1kFzknARq/CPy1ksekedyrnWavrG6VZTs9E9odRFSMbhn5oC",
	            "abc", "Doe", "abc@example.com", "1234567890");
	        User user2 = new User("saanvi", "$2a$10$Vyzmq1kFzknARq/CPy1ksekedyrnWavrG6VZTs9E9odRFSMbhn5oC", "saanvi", "Gorantla", "saanvi@example.com", "0987654321");
	        User user3 = new User("samanvitha", "$2a$10$Vyzmq1kFzknARq/CPy1ksekedyrnWavrG6VZTs9E9odRFSMbhn5oC", "samanvitha", "Gorantla", "samanvitha@example.com", "1112223333");
	        User user4 = new User("aruna", "$2a$10$Vyzmq1kFzknARq/CPy1ksekedyrnWavrG6VZTs9E9odRFSMbhn5oC", "aruna", "Gorantla", "aruna@example.com", "4445556666");
	        User user5 = new User("siva", "$2a$10$Vyzmq1kFzknARq/CPy1ksekedyrnWavrG6VZTs9E9odRFSMbhn5oC", "siva", "Gorantla", "siva@example.com", "7778889999");

	        // Check if the database already contains users
	        if (userRepository.count() == 0) {
	            // Save all users first, to persist them in the database
	            userRepository.saveAll(Arrays.asList(user1, user2, user3, user4, user5));
	            System.out.println("Dummy users have been added to the database.");

	            // Create customers associated with the users
	            Customer customer1 = new Customer();
	            customer1.setUser(user1);  // user1 is now persisted, so this is fine
	            customer1.setProfileStatus(ProfileStatus.VERIFIED);

	            Customer customer2 = new Customer();
	            customer2.setUser(user2);
	            customer2.setProfileStatus(ProfileStatus.PENDING);

	            Customer customer3 = new Customer();
	            customer3.setUser(user3);
	            customer3.setProfileStatus(ProfileStatus.VERIFIED);

	            Customer customer4 = new Customer();
	            customer4.setUser(user4);
	            customer4.setProfileStatus(ProfileStatus.VERIFIED);

	            Customer customer5 = new Customer();
	            customer5.setUser(user5);
	            customer5.setProfileStatus(ProfileStatus.PENDING);

	            // Save all customers in the database
	        //    customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3, customer4, customer5));
	            System.out.println("Dummy customers have been added to the database.");
	        } else {
	            System.out.println("Database already contains data. Skipping default data insertion.");
	        }

	        System.out.println("Default user and customer data insertion completed successfully.");
	    };
	}
}
