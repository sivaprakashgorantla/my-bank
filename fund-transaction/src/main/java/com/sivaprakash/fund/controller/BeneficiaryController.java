package com.sivaprakash.fund.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sivaprakash.fund.dto.BeneficiaryRequestDTO;
import com.sivaprakash.fund.dto.BeneficiaryResponseDTO;
import com.sivaprakash.fund.service.BeneficiaryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/beneficiaries")

public class BeneficiaryController {

	@Autowired
    private BeneficiaryService beneficiaryService;

    

	@PostMapping
    public ResponseEntity<?> addBeneficiary(
            @Valid @RequestBody BeneficiaryRequestDTO request,
            BindingResult bindingResult) {
        
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .reduce("", (a, b) -> a + "; " + b);
            return ResponseEntity.badRequest().body(new ErrorResponse("Validation failed", errorMessage));
        }

        try {
            BeneficiaryResponseDTO response = beneficiaryService.addBeneficiary(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new ErrorResponse("Failed to add beneficiary", e.getMessage()));
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