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
import com.sivaprakash.beneficiary.entity.Beneficiary.BeneficiaryStatus;
import com.sivaprakash.beneficiary.entity.Beneficiary.BeneficiaryType;
import com.sivaprakash.beneficiary.entity.BeneficiaryId;
import com.sivaprakash.beneficiary.repository.BeneficiaryRepository;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {

    @Autowired
    private BeneficiaryRepository beneficiaryRepository;

    @Override
    @Transactional
    public BeneficiaryResponseDTO addBeneficiary(BeneficiaryRequestDTO request) {
        // Create BeneficiaryId
        // Validate if beneficiary already exists
        if (beneficiaryRepository.existsByBeneficiaryId(request.getBeneficiaryId())) {
            throw new IllegalArgumentException("Beneficiary with this account number and bank code already exists");
        }

        // Create new Beneficiary entity
        Beneficiary beneficiary = new Beneficiary();
        //beneficiary.setBeneficiaryId(request.getBeneficiaryId());
        beneficiary.setBeneficiaryName(request.getBeneficiaryName());
        beneficiary.setBeneficiaryBankName(request.getBeneficiaryBankName());
        beneficiary.setBeneficiaryEmail(request.getBeneficiaryEmail());
        beneficiary.setBeneficiaryType(BeneficiaryType.EXTERNAL); // Default type
        beneficiary.setRelationship(request.getRelationship());
        beneficiary.setStatus(BeneficiaryStatus.ACTIVE);

        // Save the entity
        beneficiary = beneficiaryRepository.save(beneficiary);

        // Return response DTO
        return convertToDTO(beneficiary, "Beneficiary added successfully");
    }

    @Override
    @Transactional(readOnly = true)
    public List<BeneficiaryResponseDTO> getAllBeneficiaries() {
        List<Beneficiary> beneficiaries = beneficiaryRepository.findAll();
        return beneficiaries.stream()
                .map(b -> convertToDTO(b, "Retrieved successfully"))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BeneficiaryResponseDTO> getBeneficiariesByUserId(Long userId) {
        List<Beneficiary> beneficiaries = beneficiaryRepository.findByUserId(userId);
        return beneficiaries.stream()
                .map(b -> convertToDTO(b, "Retrieved successfully"))
                .collect(Collectors.toList());
    }

    private BeneficiaryResponseDTO convertToDTO(Beneficiary beneficiary, String message) {
        BeneficiaryResponseDTO response = new BeneficiaryResponseDTO();
        response.setBeneficiaryId(beneficiary.getBeneficiaryId());
        response.setUserId(beneficiary.getUserId());
        response.setBeneficiaryAccountNumber(beneficiary.getBeneficiaryAccountNumber());
        response.setBeneficiaryBankCode(beneficiary.getBeneficiaryBankCode());
        response.setBeneficiaryBankName(beneficiary.getBeneficiaryBankName());
        response.setBeneficiaryEmail(beneficiary.getBeneficiaryEmail());
        response.setBeneficiaryName(beneficiary.getBeneficiaryName());
        response.setStatus(beneficiary.getStatus().name());
        response.setReferenceNumber(generateReferenceNumber());
        response.setMessage(message);
        return response;
    }

    private String generateReferenceNumber() {
        return "BEN" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public void deleteBeneficiaryById(Long beneficiaryId) {
	    Beneficiary beneficiary = beneficiaryRepository.findById(beneficiaryId)
	            .orElseThrow(() -> new IllegalArgumentException("Beneficiary not found with ID: " + beneficiaryId));
	    beneficiaryRepository.delete(beneficiary);
	}

}
