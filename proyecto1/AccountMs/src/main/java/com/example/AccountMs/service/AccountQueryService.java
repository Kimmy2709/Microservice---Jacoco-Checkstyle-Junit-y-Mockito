package com.example.AccountMs.service;

import com.example.AccountMs.model.Account;

import java.util.List;

public interface AccountQueryService {
    public List<Account> listAccounts();

    public Account getAccountById(Long id);
}
