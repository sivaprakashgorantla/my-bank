package com.sivaprakash.beneficiary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sivaprakash.beneficiary.dto.BeneficiaryRequestDTO;
import com.sivaprakash.beneficiary.dto.BeneficiaryResponseDTO;
import com.sivaprakash.beneficiary.service.BeneficiaryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/beneficiaries")

public class BeneficiaryController {

	@Autowired
	private BeneficiaryService beneficiaryService;

	@PostMapping("/addBeneficiary")
	public ResponseEntity<?> addBeneficiary(@Valid @RequestBody BeneficiaryRequestDTO request,
			BindingResult bindingResult) {

		try {
			BeneficiaryResponseDTO response = beneficiaryService.addBeneficiary(request);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ErrorResponse("Failed to add beneficiary", e.getMessage()));
		}
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<BeneficiaryResponseDTO>> getAllBeneficiaries() {
		System.out.println("getAllBeneficiaries ...................");
		try {
			List<BeneficiaryResponseDTO> beneficiaries = beneficiaryService.getAllBeneficiaries();
			return ResponseEntity.ok(beneficiaries);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(List.of(new BeneficiaryResponseDTO("ERROR", "Failed to retrieve beneficiaries")));
		}
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<List<BeneficiaryResponseDTO>> getBeneficiariesByUserId(@PathVariable Long userId) {
		System.out.println("getAllBeneficiaries ...................");
		try {
			List<BeneficiaryResponseDTO> beneficiaries = beneficiaryService.getBeneficiariesByUserId(userId);
			return ResponseEntity.ok(beneficiaries);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(List.of(new BeneficiaryResponseDTO("ERROR", "Failed to retrieve beneficiaries")));
		}
	}
	@DeleteMapping("/{beneficiaryId}")
	public ResponseEntity<String> deleteBeneficiaryById(@PathVariable Long beneficiaryId) {
	    System.out.println("Deleting beneficiary with ID: " + beneficiaryId);
	    try {
	        beneficiaryService.deleteBeneficiaryById(beneficiaryId);
	        return ResponseEntity.ok("Beneficiary deleted successfully");
	    } catch (Exception e) {
	        return ResponseEntity.badRequest().body("Failed to delete beneficiary: " + e.getMessage());
	    }
	}

	static class ErrorResponse {
		private final String error;
		private final String message;

		public ErrorResponse(String error, String message) {
			super();
			this.error = error;
			this.message = message;
		}

	}
}