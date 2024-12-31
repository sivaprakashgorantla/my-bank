package com.sivaprakash.fund.dto;

import java.util.List;

public class TransactionResponseDTO {
	private List<TransactionDTO> transactions;
	private String message;
	private int totalElements;

	public TransactionResponseDTO() {
	}

	public TransactionResponseDTO(List<TransactionDTO> transactions, String message, int totalElements) {
		super();
		this.transactions = transactions;
		this.message = message;
		this.totalElements = totalElements;
	}

	public List<TransactionDTO> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TransactionDTO> transactions) {
		this.transactions = transactions;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
	}

	@Override
	public String toString() {
		return "TransactionResponseDTO [transactions=" + transactions + ", message=" + message + ", totalElements="
				+ totalElements + "]";
	}

}