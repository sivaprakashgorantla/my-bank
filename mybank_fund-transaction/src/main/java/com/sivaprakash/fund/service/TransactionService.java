package com.sivaprakash.fund.service;

import java.util.List;

import com.sivaprakash.fund.dto.TransactionDTO;
import com.sivaprakash.fund.dto.TransactionSearchCriteria;
import com.sivaprakash.fund.entity.Transaction;

public interface TransactionService {
	List<TransactionDTO> getLastTenTransactions(String accountId) ;
	List<TransactionDTO> searchTransactions(TransactionSearchCriteria criteria);
}
