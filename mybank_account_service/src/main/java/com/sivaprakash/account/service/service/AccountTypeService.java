package com.sivaprakash.account.service.service;

import com.sivaprakash.account.service.entiry.AccountType;
import java.util.List;
import java.util.Optional;

public interface AccountTypeService {
    Optional<AccountType> findById(Long accountTypeId);
    List<AccountType> findAll();
    AccountType save(AccountType accountType);
    void deleteById(Long accountTypeId);
    AccountType findByTypeName(String typeName);  // Example custom query method
}
