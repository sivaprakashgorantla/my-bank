package com.sivaprakash.loan.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.sivaprakash.loan.entity.LoanApplication;

@Component

public class EventPublisher {
    
	@Autowired
	private  KafkaTemplate<String, Event> kafkaTemplate;
    
    public void publishLoanCreatedEvent(LoanApplication loan) {
        Event event = new Event("LOAN_CREATED", loan);
        kafkaTemplate.send("loan-events", event);
    }
    
    public void publishProcessingFeePaidEvent(LoanApplication loan) {
        Event event = new Event("PROCESSING_FEE_PAID", loan);
        kafkaTemplate.send("loan-events", event);
    }
    
    // Additional event publishing methods
}