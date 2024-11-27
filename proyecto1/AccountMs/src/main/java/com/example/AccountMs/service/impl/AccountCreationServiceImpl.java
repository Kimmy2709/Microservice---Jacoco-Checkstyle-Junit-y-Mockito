package com.example.AccountMs.service.impl;
import com.example.AccountMs.dto.AccountDTO;
import com.example.AccountMs.factory.AccountFactory;
import com.example.AccountMs.model.Account;
import com.example.AccountMs.model.Client;
import com.example.AccountMs.repository.AccountRepository;
import com.example.AccountMs.service.AccountCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class AccountCreationServiceImpl implements AccountCreationService {

    private final AccountRepository accountRepository;
    private final AccountNumberGenerator accountNumberGenerator;

    @Autowired
    private AccountFactory accountFactory;

    public AccountCreationServiceImpl(AccountRepository accountRepository , AccountFactory accountFactory, AccountNumberGenerator accountNumberGenerator)
    {

        this.accountRepository = accountRepository;

        this.accountFactory = accountFactory;

        this.accountNumberGenerator = accountNumberGenerator;
    }

    private String generateUniqueAccountNumber() {
        String account_number;
        do {
            account_number = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        } while (accountRepository.existsByAccountNumber(account_number));
        return account_number;
    }

    public Account createAccount(AccountDTO accountDTO) {
        String accountNumber = generateUniqueAccountNumber();
        Account account = accountFactory.createAccount(accountDTO, accountNumber);  // Crear la cuenta

        if (account != null) {
            Client client = new Client();
            client.setId(accountDTO.getClient_id());
            account.setClient(client);
            return accountRepository.save(account);
        } else {
            throw new IllegalStateException("Failed to create account");
        }

    }
}
