package com.example.AccountMs.service;

import com.example.AccountMs.dto.AccountDTO;
import com.example.AccountMs.model.Account;

public interface AccountCreationService {
    public Account createAccount(AccountDTO accountDTO);

}
