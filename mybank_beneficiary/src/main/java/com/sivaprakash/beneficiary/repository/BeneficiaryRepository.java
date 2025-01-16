package com.sivaprakash.beneficiary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sivaprakash.beneficiary.entity.Beneficiary;

@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {

    boolean existsByBeneficiaryId(Long beneficiaryId);

    @Query("SELECT b FROM Beneficiary b WHERE b.userId = :userId")
    List<Beneficiary> findByUserId(@Param("userId") Long userId);

    // Fix the query for checking the account number
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN TRUE ELSE FALSE END FROM Beneficiary b WHERE b.beneficiaryAccountNumber = :accountNumber")
    boolean existsByBeneficiaryAccountNumber(@Param("accountNumber") String accountNumber);
}
