package com.sivaprakash.fund.service;

import java.util.UUID;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.sivaprakash.fund.dto.BeneficiaryRequestDTO;
//import com.sivaprakash.fund.dto.BeneficiaryResponseDTO;
//import com.sivaprakash.fund.entity.Beneficiary;
//import com.sivaprakash.fund.repository.BeneficiaryRepository;

//@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {

//	@Autowired
//    private BeneficiaryRepository beneficiaryRepository;
//
//    @Override
//    @Transactional
//    public BeneficiaryResponseDTO addBeneficiary(BeneficiaryRequestDTO request) {
//        // Validate if beneficiary already exists
//        if (beneficiaryRepository.existsByBeneficiaryAccountNumber(request.getBeneficiaryAccountNumber())) {
//            throw new IllegalArgumentException("Beneficiary with this account number already exists");
//        }
//
//        // Create new beneficiary
//        Beneficiary beneficiary = new Beneficiary();
//        beneficiary.setBeneficiaryName(request.getBeneficiaryName());
//        beneficiary.setBeneficiaryAccountNumber(request.getBeneficiaryAccountNumber());
//        beneficiary.setBeneficiaryBankCode(request.getBeneficiaryBankCode());
//        beneficiary.setBeneficiaryEmail(request.getBeneficiaryEmail());
//        beneficiary.setAccountId(request.getAccountId());
//        beneficiary.setBeneficiaryType(Beneficiary.BeneficiaryType.EXTERNAL); // Default to external
//        beneficiary.setStatus(Beneficiary.BeneficiaryStatus.ACTIVE);
//
//        // Save beneficiary
//        beneficiary = beneficiaryRepository.save(beneficiary);
//
//        // Create response
//        BeneficiaryResponseDTO response = new BeneficiaryResponseDTO();
//        response.setBeneficiaryId(beneficiary.getBeneficiaryId());
//        response.setBeneficiaryName(beneficiary.getBeneficiaryName());
//        response.setBeneficiaryAccountNumber(beneficiary.getBeneficiaryAccountNumber());
//        response.setBeneficiaryBankCode(beneficiary.getBeneficiaryBankCode());
//        response.setBeneficiaryEmail(beneficiary.getBeneficiaryEmail());
//        response.setStatus(beneficiary.getStatus().name());
//        response.setReferenceNumber(generateReferenceNumber());
//        response.setMessage("Beneficiary added successfully");
//
//        return response;
//    }
//
//    private String generateReferenceNumber() {
//        return "BEN" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
//    }
}