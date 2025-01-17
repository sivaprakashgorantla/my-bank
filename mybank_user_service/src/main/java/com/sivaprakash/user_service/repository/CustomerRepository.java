package com.sivaprakash.user_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sivaprakash.user_service.entity.Customer;
import com.sivaprakash.user_service.entity.User;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Add custom query methods if needed
	Optional<Customer> findByUser(User user);
    Optional<Customer> findByCustomerId(String customerId);
}
