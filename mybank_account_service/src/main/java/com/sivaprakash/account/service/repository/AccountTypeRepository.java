package com.sivaprakash.account.service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sivaprakash.account.service.entiry.AccountType;

public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {
    // Custom queries if needed
	Optional<AccountType> findByTypeName(String typeName);
	
	 // OR using JPQL
    @Query("SELECT a FROM AccountType a WHERE a.typeName = :typeName")
    AccountType getAccountsByTypeName(@Param("typeName") String typeName);
}