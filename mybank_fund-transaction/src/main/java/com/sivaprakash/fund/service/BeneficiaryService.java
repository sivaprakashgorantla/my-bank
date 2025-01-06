package com.sivaprakash.fund.service;

import com.sivaprakash.fund.dto.BeneficiaryRequestDTO;
import com.sivaprakash.fund.dto.BeneficiaryResponseDTO;

public interface BeneficiaryService {
	public BeneficiaryResponseDTO addBeneficiary(BeneficiaryRequestDTO request);
}
