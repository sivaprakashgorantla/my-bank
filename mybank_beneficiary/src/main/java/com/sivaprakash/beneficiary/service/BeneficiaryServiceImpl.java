package com.sivaprakash.beneficiary.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sivaprakash.beneficiary.dto.BeneficiaryRequestDTO;
import com.sivaprakash.beneficiary.dto.BeneficiaryResponseDTO;
import com.sivaprakash.beneficiary.entity.Beneficiary;
import com.sivaprakash.beneficiary.repository.BeneficiaryRepository;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {

	@Autowired
    private BeneficiaryRepository beneficiaryRepository;

    @Override
    @Transactional
    public BeneficiaryResponseDTO addBeneficiary(BeneficiaryRequestDTO request) {
        // Validate if beneficiary already exists
        if (beneficiaryRepository.existsByBeneficiaryAccountNumber(request.getBeneficiaryAccountNumber())) {
            throw new IllegalArgumentException("Beneficiary with this account number already exists");
        }

        // Create new beneficiary
        Beneficiary beneficiary = new Beneficiary();
        //setting temp values 
        beneficiary.setUserId(10000L);
        beneficiary.setBeneficiaryBankName(request.getBeneficiaryBankName());
        beneficiary.setRelationship(request.getRelationship());
        beneficiary.setBeneficiaryName(request.getBeneficiaryName());
        beneficiary.setBeneficiaryAccountNumber(request.getBeneficiaryAccountNumber());
        beneficiary.setBeneficiaryBankCode(request.getBeneficiaryBankCode());
        beneficiary.setBeneficiaryEmail(request.getBeneficiaryEmail());
        //beneficiary.setAccountId(request.getAccountId());
        beneficiary.setBeneficiaryType(Beneficiary.BeneficiaryType.EXTERNAL); // Default to external
        beneficiary.setStatus(Beneficiary.BeneficiaryStatus.ACTIVE);

        try {
			// Save beneficiary
			beneficiary = beneficiaryRepository.save(beneficiary);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Create response
        BeneficiaryResponseDTO response = new BeneficiaryResponseDTO();
        response.setBeneficiaryId(beneficiary.getBeneficiaryId());
        response.setBeneficiaryName(beneficiary.getBeneficiaryName());
        response.setBeneficiaryAccountNumber(beneficiary.getBeneficiaryAccountNumber());
        response.setBeneficiaryBankCode(beneficiary.getBeneficiaryBankCode());
        response.setBeneficiaryEmail(beneficiary.getBeneficiaryEmail());
        response.setStatus(beneficiary.getStatus().name());
        response.setReferenceNumber(generateReferenceNumber());
        response.setMessage("Beneficiary added successfully");

        return response;
    }

    private String generateReferenceNumber() {
        return "BEN" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<BeneficiaryResponseDTO> getAllBeneficiaries() {
        
    	List<Beneficiary> beneficiaries = null;
		try {
			beneficiaries = beneficiaryRepository.findAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Map entities to DTOs
        return beneficiaries.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private BeneficiaryResponseDTO convertToDTO(Beneficiary beneficiary) {
        BeneficiaryResponseDTO response = new BeneficiaryResponseDTO();
        response.setBeneficiaryId(beneficiary.getBeneficiaryId());
        response.setBeneficiaryName(beneficiary.getBeneficiaryName());
        response.setBeneficiaryAccountNumber(beneficiary.getBeneficiaryAccountNumber());
        response.setBeneficiaryBankCode(beneficiary.getBeneficiaryBankCode());
        response.setBeneficiaryEmail(beneficiary.getBeneficiaryEmail());
        response.setStatus(beneficiary.getStatus().name());
        response.setReferenceNumber(generateReferenceNumber()); // Optional
        response.setMessage("Retrieved successfully");
        return response;
    }

	@Override
	public List<BeneficiaryResponseDTO> getBeneficiariesByUserId(Long userId) {
		// TODO Auto-generated method stub
		System.out.println("getBeneficiariesByUserId........................");
    	List<Beneficiary> beneficiaries = null;
		try {
			beneficiaries = beneficiaryRepository.findByUserId(userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Map entities to DTOs
        return beneficiaries.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
	}

	
	public void deleteBeneficiaryById(Long beneficiaryId) {
	    Beneficiary beneficiary = beneficiaryRepository.findById(beneficiaryId)
	            .orElseThrow(() -> new IllegalArgumentException("Beneficiary not found with ID: " + beneficiaryId));
	    beneficiaryRepository.delete(beneficiary);
	}

	
}