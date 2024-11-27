package com.example.AccountMs;

import static org.junit.jupiter.api.Assertions.*;

import com.example.AccountMs.dto.AccountDTO;
import com.example.AccountMs.factory.AccountFactory;
import com.example.AccountMs.model.Account;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class AccountFactoryTest {

    private final AccountFactory accountFactory = new AccountFactory();

    @Test
    public void testCreateAccount() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccount_type(Account.AccountType.AHORROS);
        accountDTO.setClient_id(1L);
        String accountNumber = "1234567890";

        Account account = accountFactory.createAccount(accountDTO, accountNumber);

        assertNotNull(account, "La cuenta no debería ser nula");
        assertEquals(accountNumber, account.getAccountNumber(), "El número de cuenta no coincide");
        assertEquals(BigDecimal.ZERO, account.getBalance_amount(), "El saldo inicial debería ser 0");
        assertEquals(Account.AccountType.AHORROS, account.getAccount_type(), "El tipo de cuenta no coincide");
        assertNotNull(account.getClient(), "El cliente no debería ser nulo");
        assertEquals(1L, account.getClient().getId(), "El ID del cliente no coincide");
    }
}
