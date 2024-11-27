package com.example.AccountMs.service.impl;

import com.example.AccountMs.repository.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AccountNumberGenerator {
    public String generateUniqueAccountNumber(AccountRepository accountRepository) {
        String account_number;
        do {
            account_number = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        } while (accountRepository.existsByAccountNumber(account_number));
        return account_number;
    }
}

