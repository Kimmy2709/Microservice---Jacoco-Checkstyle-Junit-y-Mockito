package com.example.AccountMs.service.impl;

import com.example.AccountMs.model.Account;
import com.example.AccountMs.repository.AccountRepository;
import com.example.AccountMs.service.AccountDeletionService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;

@Service
public class AccountDeletionServiceImpl implements AccountDeletionService {
    private final AccountRepository accountRepository;
    private final RestTemplate restTemplate;

    public AccountDeletionServiceImpl(AccountRepository accountRepository, RestTemplate restTemplate) {
        this.accountRepository = accountRepository;
        this.restTemplate = restTemplate;
    }
    public void  deleteAccount(Long id)
    {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cuenta no encontrada"));
        accountRepository.delete(account);
        restTemplate.postForLocation("http://localhost:8085/api/clients/decrementAccountCount/" + account.getClient().getId(), null);
    }
}
