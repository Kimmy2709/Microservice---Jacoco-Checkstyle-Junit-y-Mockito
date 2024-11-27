package com.example.AccountMs.service;

import com.example.AccountMs.model.Account;

import java.math.BigDecimal;

public interface WithdrawService {

    Account withDrawMoney(Long id, BigDecimal balance_amount);
}
