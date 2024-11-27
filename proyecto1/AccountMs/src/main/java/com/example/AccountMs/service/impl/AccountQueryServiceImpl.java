package com.example.AccountMs.service.impl;

import com.example.AccountMs.model.Account;
import com.example.AccountMs.repository.AccountRepository;
import com.example.AccountMs.service.AccountQueryService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AccountQueryServiceImpl implements AccountQueryService {
    private final AccountRepository accountRepository;

    public AccountQueryServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> listAccounts()
    {

        return  accountRepository.findAll();
    }

    public Account getAccountById(Long id)
    {
        Optional<Account> account = accountRepository.findById(id);
        return account.orElseThrow(() -> new NoSuchElementException("cliente no encontrado"));
    }
}
