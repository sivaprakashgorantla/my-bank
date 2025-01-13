package com.sivaprakash.user_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sivaprakash.user_service.dto.CustomerProfileDTO;
import com.sivaprakash.user_service.dto.CustomerProfileUpdateRequest;
import com.sivaprakash.user_service.dto.OtpValidationRequest;
import com.sivaprakash.user_service.entity.Customer;
import com.sivaprakash.user_service.entity.User;
import com.sivaprakash.user_service.enums.ProfileStatus;
import com.sivaprakash.user_service.exception.ResourceNotFoundException;
import com.sivaprakash.user_service.repository.CustomerRepository;
import com.sivaprakash.user_service.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.ValidationException;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OtpService otpService;
	
	/*
	 * @Autowired private BankService bankService;
	 */
//    @Autowired
//    public CustomerService(CustomerRepository customerRepository, 
//                         UserRepository userRepository,
//                         OtpService otpService,
//                         BankService bankService) {
//        this.customerRepository = customerRepository;
//        this.userRepository = userRepository;
//        this.otpService = otpService;
//        this.bankService = bankService;
//    }

	@Transactional(readOnly = true)
	public CustomerProfileDTO getCustomerProfile(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		Customer customer = customerRepository.findByUser(user)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

		CustomerProfileDTO dto = new CustomerProfileDTO();
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		dto.setMobileNo(maskMobileNumber(user.getPhoneNumber()));
		dto.setEmailId(maskEmail(user.getEmail()));
		dto.setCustomerId(customer.getCustomerId());
		dto.setProfileStatus(customer.getProfileStatus());

		return dto;
	}

	@Transactional
	public void updateCustomerId(CustomerProfileUpdateRequest request) {
		User user = userRepository.findByUsername(request.getUsername())
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		Customer customer = customerRepository.findByUser(user)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

		// Validate with bank backend
//		boolean isValidCustomer = bankService.validateCustomerDetails(request.getCustomerId(), user.getPhoneNumber(),
//				user.getEmail());
//
//		if (!isValidCustomer) {
//			throw new ValidationException("Invalid customer details");
//		}

		// Generate and send OTP
		String otp = otpService.generateOtp(user.getUsername());
		otpService.sendOtp(user.getPhoneNumber(), user.getEmail(), otp);

		// Update customer ID (will be confirmed after OTP validation)
		customer.setCustomerId(request.getCustomerId());
		customerRepository.save(customer);
	}

	@Transactional
	public void validateOtpAndUpdateProfile(OtpValidationRequest request) {
		User user = userRepository.findByUsername(request.getUsername())
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		Customer customer = customerRepository.findByUser(user)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

		boolean isValidOtp = otpService.validateOtp(request.getUsername(), request.getOtp());

		if (!isValidOtp) {
			throw new ValidationException("Invalid OTP");
		}

		customer.setProfileStatus(ProfileStatus.VERIFIED);
		customerRepository.save(customer);
	}

	private String maskMobileNumber(String mobileNo) {
		return "XXXXXX" + mobileNo.substring(mobileNo.length() - 4);
	}

	private String maskEmail(String email) {
		String[] parts = email.split("@");
		String name = parts[0];
		String maskedName = name.substring(0, 2) + "XXXX" + name.substring(name.length() - 2);
		return maskedName + "@" + parts[1];
	}
}