package com.sivaprakash.beneficiary.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.sivaprakash.beneficiary.dto.BeneficiaryRequestDTO;
import com.sivaprakash.beneficiary.dto.BeneficiaryResponseDTO;
import com.sivaprakash.beneficiary.service.BeneficiaryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/beneficiaries")
public class BeneficiaryController {

    private static final Logger logger = LoggerFactory.getLogger(BeneficiaryController.class);

    @Autowired
    private BeneficiaryService beneficiaryService;

    @PostMapping(value = "/addBeneficiary", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addBeneficiary(@Valid @RequestBody BeneficiaryRequestDTO request) {
        logger.info("Received request to add beneficiary: {}", request);
        try {
            BeneficiaryResponseDTO response = beneficiaryService.addBeneficiary(request);
            logger.info("Beneficiary added successfully: {}", response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Failed to add beneficiary: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ErrorResponse("Failed to add beneficiary", e.getMessage()));
        }
    }
    
    @GetMapping("/getAll")
    public ResponseEntity<List<BeneficiaryResponseDTO>> getAllBeneficiaries() {
        logger.info("Fetching all beneficiaries");
        try {
            List<BeneficiaryResponseDTO> beneficiaries = beneficiaryService.getAllBeneficiaries();
            logger.info("Retrieved {} beneficiaries", beneficiaries.size());
            return ResponseEntity.ok(beneficiaries);
        } catch (Exception e) {
            logger.error("Error retrieving beneficiaries: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(List.of(new BeneficiaryResponseDTO("ERROR", "Failed to retrieve beneficiaries")));
        }
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<BeneficiaryResponseDTO>> getBeneficiariesByCutstomerId(@PathVariable Long customerId) {
        logger.info("Fetching beneficiaries for customerId: {}", customerId);
        try {
            List<BeneficiaryResponseDTO> beneficiaries = beneficiaryService.getBeneficiariesByConstomerId(customerId);
            logger.info("Retrieved {} beneficiaries for customerId: {}", beneficiaries.size(), customerId);
            return ResponseEntity.ok(beneficiaries);
        } catch (Exception e) {
            logger.error("Error retrieving beneficiaries for customerId {}: {}", customerId, e.getMessage(), e);
            return ResponseEntity.badRequest().body(List.of(new BeneficiaryResponseDTO("ERROR", "Failed to retrieve beneficiaries")));
        }
    }

    @DeleteMapping("/{beneficiaryId}")
    public ResponseEntity<String> deleteBeneficiaryById(@PathVariable Long beneficiaryId) {
        logger.info("Deleting beneficiary with ID: {}", beneficiaryId);
        try {
            beneficiaryService.deleteBeneficiaryById(beneficiaryId);
            logger.info("Beneficiary deleted successfully: {}", beneficiaryId);
            return ResponseEntity.ok("Beneficiary deleted successfully");
        } catch (Exception e) {
            logger.error("Error deleting beneficiary {}: {}", beneficiaryId, e.getMessage(), e);
            return ResponseEntity.badRequest().body("Failed to delete beneficiary: " + e.getMessage());
        }
    }

    static class ErrorResponse {
        private final String error;
        private final String message;

        public ErrorResponse(String error, String message) {
            this.error = error;
            this.message = message;
        }

        public String getError() {
            return error;
        }

        public String getMessage() {
            return message;
        }
    }
}
