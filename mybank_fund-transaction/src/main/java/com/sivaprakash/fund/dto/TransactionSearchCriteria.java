package com.sivaprakash.fund.dto;

import java.time.LocalDateTime;

public class TransactionSearchCriteria {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String accountId;
	
    public TransactionSearchCriteria() {
		super();
	}

	public TransactionSearchCriteria(LocalDateTime startDate, LocalDateTime endDate, String accountId) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.accountId = accountId;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	@Override
	public String toString() {
		return "TransactionSearchCriteria [startDate=" + startDate + ", endDate=" + endDate + ", accountId=" + accountId
				+ "]";
	}
    
}

