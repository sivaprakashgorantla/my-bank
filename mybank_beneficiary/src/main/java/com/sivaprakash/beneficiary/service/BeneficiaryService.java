package com.sivaprakash.beneficiary.service;

import java.util.List;

import com.sivaprakash.beneficiary.dto.BeneficiaryRequestDTO;
import com.sivaprakash.beneficiary.dto.BeneficiaryResponseDTO;

public interface BeneficiaryService {
	public BeneficiaryResponseDTO addBeneficiary(BeneficiaryRequestDTO request);

	public List<BeneficiaryResponseDTO> getAllBeneficiaries();

	public List<BeneficiaryResponseDTO> getBeneficiariesByConstomerId(Long customerId);

	BeneficiaryResponseDTO getBeneficiaryById(Long beneficiaryId);
	
	public void deleteBeneficiaryById(Long beneficiaryId);
}
