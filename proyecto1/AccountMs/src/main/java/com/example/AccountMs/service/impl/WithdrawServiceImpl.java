package com.example.AccountMs.service.impl;

import com.example.AccountMs.model.Account;
import com.example.AccountMs.repository.AccountRepository;
import com.example.AccountMs.service.AccountQueryService;
import com.example.AccountMs.service.WithdrawService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.function.Predicate;

@Service
public class WithdrawServiceImpl implements WithdrawService {
    private final AccountRepository accountRepository;
    private final AccountQueryService accountQueryService;

    public WithdrawServiceImpl(AccountQueryService accountQueryService, AccountRepository accountRepository )
    {
        this.accountQueryService = accountQueryService;
        this.accountRepository = accountRepository;
    }

    public Account withDrawMoney(Long id, BigDecimal balanceAmount) {
        Account account = accountQueryService.getAccountById(id);

        if (account.getBalance_amount().compareTo(balanceAmount) < 0) {
            throw new IllegalArgumentException("Fondos insuficientes.");
        }

        account.setBalance_amount(account.getBalance_amount().subtract(balanceAmount));
        return accountRepository.save(account);
    }
}
