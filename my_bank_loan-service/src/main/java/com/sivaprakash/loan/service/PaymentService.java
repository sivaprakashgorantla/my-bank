package com.sivaprakash.loan.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sivaprakash.loan.entity.LoanApplication;
import com.sivaprakash.loan.entity.LoanPayment;
import com.sivaprakash.loan.entity.PaymentEvent;
import com.sivaprakash.loan.enums.EventStatus;
import com.sivaprakash.loan.enums.PaymentStatus;
import com.sivaprakash.loan.repository.PaymentEventRepository;
import com.sivaprakash.loan.repository.PaymentRepository;
import com.sivaprakash.loan.request.PaymentRequest;
import com.sivaprakash.loan.request.ProcessingFeePaymentRequest;
import com.sivaprakash.loan.response.PaymentResponse;

@Service

public class PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private PaymentEventRepository eventRepository;

	public PaymentResponse processPayment(PaymentRequest request) {
		LoanPayment payment = new LoanPayment();
		payment.setLoanId(request.getLoanId());
		payment.setAmount(request.getAmount());
		payment.setPaymentMethod(request.getPaymentMethod());
		payment.setStatus(PaymentStatus.PENDING);

		payment = paymentRepository.save(payment);

		PaymentEvent event = createPaymentEvent(payment, request);
		eventRepository.save(event);

		// Simulate payment processing
		boolean success = processPaymentWithProvider(request);

		if (success) {
			payment.setStatus(PaymentStatus.COMPLETED);
			event.setStatus(EventStatus.PROCESSED);
		} else {
			payment.setStatus(PaymentStatus.FAILED);
			event.setStatus(EventStatus.FAILED);
		}

		paymentRepository.save(payment);
		eventRepository.save(event);

		return new PaymentResponse(payment.getPaymentId(), success);
	}

	private boolean processPaymentWithProvider(PaymentRequest request) {
		// Integration with payment gateway would go here
		return true; // Simulated success
	}

	private PaymentEvent createPaymentEvent(LoanPayment payment, PaymentRequest request) {
		PaymentEvent event = new PaymentEvent();
		event.setPaymentId(payment.getPaymentId());
		event.setEventType("PAYMENT_INITIATED"); // Event type could be payment initiation or others depending on the
													// business logic
		event.setEventData("Payment of amount " + request.getAmount() + " for loan ID " + request.getLoanId());
		event.setStatus(EventStatus.PENDING); // Set initial status as PENDING
		event.setCreatedAt(LocalDateTime.now()); // Set the creation time
		return event;
	}

	public PaymentResponse payProcessingFee(LoanApplication loan, ProcessingFeePaymentRequest request) {
		// Create a payment for the processing fee
		LoanPayment payment = new LoanPayment();
		payment.setLoanId(loan.getLoanId());
		payment.setAmount(loan.getProcessingFee()); // Amount is the processing fee
		payment.setPaymentMethod(request.getPaymentMethod());
		payment.setStatus(PaymentStatus.PENDING); // Initially set to PENDING

		payment = paymentRepository.save(payment); // Save the payment

		// Create a payment event for the processing fee payment
		/*
		 * PaymentEvent event = createPaymentEvent(payment, request); // You can reuse
		 * the existing createPaymentEvent // method eventRepository.save(event);
		 * 
		 * // Simulate payment processing (can integrate with a real payment gateway
		 * here) boolean success = processPaymentWithProvider(request);
		 * 
		 		if (success) {
			payment.setStatus(PaymentStatus.COMPLETED);
			event.setStatus(EventStatus.PROCESSED);
		} else {
			payment.setStatus(PaymentStatus.FAILED);
			event.setStatus(EventStatus.FAILED);
		}

		// Save the updated payment and event
		paymentRepository.save(payment);
		eventRepository.save(event);
*/
		// Return the payment response
		return new PaymentResponse(payment.getPaymentId(), true);
	}

}