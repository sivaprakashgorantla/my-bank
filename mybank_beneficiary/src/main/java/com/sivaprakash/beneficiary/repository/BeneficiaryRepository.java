package com.sivaprakash.beneficiary.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sivaprakash.beneficiary.entity.Beneficiary;

@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {

    
    @Query("SELECT b FROM Beneficiary b WHERE b.customerId = :customerId")
    //List<Beneficiary> findByCustomerId(@Param("customerId") Long customerId);
    Optional<Beneficiary> findByCustomerId(Long customerId); // Returns a single record

    // Fix the query for checking the account number
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN TRUE ELSE FALSE END FROM Beneficiary b WHERE b.beneficiaryAccountNumber = :accountNumber")
    boolean existsByBeneficiaryAccountNumber(@Param("accountNumber") String accountNumber);
    
    @Query("SELECT COUNT(b) > 0 FROM Beneficiary b WHERE b.beneficiaryId = :id")
    boolean existsByBeneficiaryId(@Param("id") Long id);
}
