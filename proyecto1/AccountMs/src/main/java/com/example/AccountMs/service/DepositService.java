package com.example.AccountMs.service;

import com.example.AccountMs.model.Account;

import java.math.BigDecimal;

public interface DepositService {
    Account makeDeposit(Long id, BigDecimal balance_amount);
}
