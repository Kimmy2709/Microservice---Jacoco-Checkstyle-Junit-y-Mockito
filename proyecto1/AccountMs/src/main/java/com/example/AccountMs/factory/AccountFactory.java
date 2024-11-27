package com.example.AccountMs.factory;

import com.example.AccountMs.dto.AccountDTO;
import com.example.AccountMs.model.Account;
import com.example.AccountMs.model.Client;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountFactory {
    public Account createAccount(AccountDTO accountDTO, String accountNumber) {
        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setBalance_amount(BigDecimal.ZERO); // Saldo inicial en 0
        account.setAccount_type(accountDTO.getAccount_type());

        Client client = new Client();
        client.setId(accountDTO.getClient_id());
        account.setClient(client);

        return account;
    }
}
