package com.sivaprakash.fund.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sivaprakash.fund.dto.TransactionDTO;
import com.sivaprakash.fund.dto.TransactionSearchCriteria;
import com.sivaprakash.fund.entity.Transaction;
import com.sivaprakash.fund.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	@Transactional(readOnly = true)
	public List<TransactionDTO> getLastTenTransactions(String accountNumber) {
		List<Transaction> transactions = transactionRepository
				.findTop10ByFromAccountIdOrToAccountIdOrderByTransactionDateDesc(accountNumber, accountNumber);
		return transactions.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<TransactionDTO> searchTransactions(TransactionSearchCriteria criteria) {
		List<Transaction> transactions;

		if (criteria.getStartDate() != null && criteria.getEndDate() != null) {
			transactions = transactionRepository.findByAccountAndDateRange(criteria.getAccountId(),
					criteria.getStartDate(), criteria.getEndDate());
		} else if (criteria.getStartDate() != null) {
			transactions = transactionRepository.findByAccountAndDateFrom(criteria.getAccountId(),
					criteria.getStartDate());
		} else if (criteria.getEndDate() != null) {
			transactions = transactionRepository.findByAccountAndDateTo(criteria.getAccountId(), criteria.getEndDate());
		} else {
			transactions = transactionRepository.findByFromAccountNumberOrToAccountNumber(criteria.getAccountId(),
					criteria.getAccountId());
		}

		return transactions.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	private TransactionDTO convertToDTO(Transaction transaction) {
		TransactionDTO dto = new TransactionDTO();
		dto.setTransactionId(transaction.getTransactionId());
		dto.setTransactionType(transaction.getTransactionType().name());
		dto.setTransactionDate(transaction.getTransactionDate());
		dto.setAmount(transaction.getAmount());
		dto.setCurrencyCode(transaction.getCurrencyCode());
		dto.setStatus(transaction.getStatus().name());
		dto.setDescription(transaction.getDescription());

		// Set counterparty details based on transaction type
		if (transaction.getTransactionType() == Transaction.TransactionType.DEPOSIT
				|| transaction.getTransactionType() == Transaction.TransactionType.TRANSFER) {
			dto.setCounterpartyAccountId(transaction.getFromAccountNumber().toString());
		} else {
			dto.setCounterpartyAccountId(transaction.getToAccountNumber().toString());
		}

		return dto;
	}
}