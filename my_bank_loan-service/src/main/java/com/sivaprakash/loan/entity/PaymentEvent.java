package com.sivaprakash.loan.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import com.sivaprakash.loan.enums.*;
// PaymentEvent.java
@Entity
@Table(name = "payment_events")
public class PaymentEvent{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_event_seq")
	@SequenceGenerator(name = "payment_event_seq", sequenceName = "payment_event_seq", allocationSize = 1)
	private Long eventId;

	@Column(nullable = false)
	private Long paymentId;

	@Column(nullable = false)
	private String eventType;

	@Column(columnDefinition = "CLOB")
	private String eventData;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	private LocalDateTime processedAt;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EventStatus status;

	@Version
	private Long version;

	
	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
		if (status == null) {
			status = EventStatus.PENDING;
		}
	}

	public void markAsProcessed() {
		this.status = EventStatus.PROCESSED;
		this.processedAt = LocalDateTime.now();
	}

	public void markAsFailed(String errorDetails) {
		this.status = EventStatus.FAILED;
		this.eventData = this.eventData + "\nError: " + errorDetails;
		this.processedAt = LocalDateTime.now();
	}

	public PaymentEvent() {}
	
	public PaymentEvent(Long eventId, Long paymentId, String eventType, String eventData, LocalDateTime createdAt,
			LocalDateTime processedAt, EventStatus status, Long version) {
		super();
		this.eventId = eventId;
		this.paymentId = paymentId;
		this.eventType = eventType;
		this.eventData = eventData;
		this.createdAt = createdAt;
		this.processedAt = processedAt;
		this.status = status;
		this.version = version;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getEventData() {
		return eventData;
	}

	public void setEventData(String eventData) {
		this.eventData = eventData;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getProcessedAt() {
		return processedAt;
	}

	public void setProcessedAt(LocalDateTime processedAt) {
		this.processedAt = processedAt;
	}

	public EventStatus getStatus() {
		return status;
	}

	public void setStatus(EventStatus status) {
		this.status = status;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	
	
}