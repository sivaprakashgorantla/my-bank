package com.sivaprakash.account.service.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sivaprakash.account.service.dto.AccountDetailsDTO;


public interface AccountService {
    
	// Create a new account
	public List<AccountDetailsDTO> getAccountsByUserId(Long userId) ;
	
	public boolean isProfileComplete(Long userId);
	
}
