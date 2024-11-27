package com.example.AccountMs.service.impl;

import com.example.AccountMs.factory.AccountFactory;
import com.example.AccountMs.model.Account;
import com.example.AccountMs.repository.AccountRepository;
import com.example.AccountMs.service.AccountQueryService;
import com.example.AccountMs.service.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DepositServiceImpl implements DepositService {

    private final AccountRepository accountRepository;
    private final AccountQueryService accountQueryService;

    public DepositServiceImpl(AccountQueryService accountQueryService, AccountRepository accountRepository )
    {
        this.accountQueryService = accountQueryService;

        this.accountRepository = accountRepository;
    }
    public Account makeDeposit(Long id, BigDecimal balanceAmount) {
        if (balanceAmount == null || balanceAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto del depósito debe ser mayor a cero.");
        }
        Account account = accountQueryService.getAccountById(id);
        account.setBalance_amount(account.getBalance_amount().add(balanceAmount));
        return accountRepository.save(account);
    }
}
