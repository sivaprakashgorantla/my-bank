package com.sivaprakash.account.service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sivaprakash.account.service.entiry.AccountType;

public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {
    // Custom queries if needed
	Optional<AccountType> findByTypeName(String typeName);
}