package com.example.AccountMs.controller;

import com.example.AccountMs.dto.AccountDTO;
import com.example.AccountMs.model.Account;
import com.example.AccountMs.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
public class AccountController {

    private final AccountCreationService accountService;
    private final AccountQueryService accountQueryService;
    private final AccountDeletionService accountDeletionService;
    private final DepositService depositService;
    private final WithdrawService withdrawService;

    @PostMapping("cuentas")
    public Account addAccount(@Validated @RequestBody AccountDTO accountDTO) {
        System.out.println("clientId recibido: " + accountDTO.getClient_id());
        return accountService.createAccount(accountDTO);
    }

    @GetMapping("cuentas")
    public List<Account> listAccounts(@RequestHeader Map<String, String> headers) {
        return accountQueryService.listAccounts();
    }

    @GetMapping("cuentas/{id}")
    public Account getAccountById(@RequestHeader Map<String, String> headers, @PathVariable("id") Long id)
    {
        return accountQueryService.getAccountById(id);
    }

    @PutMapping("cuentas/{id}/depositar")
    public Account deposit( @PathVariable("id") Long id, @RequestBody AccountDTO balanceRequest)
    {
        return depositService.makeDeposit(id, balanceRequest.getBalance_amount( ));
    }

    @PutMapping("cuentas/{id}/retirar")
    public Account withdraw(@PathVariable("id") Long id, @RequestBody AccountDTO balanceRequest)
    {
        return withdrawService.withDrawMoney(id, balanceRequest.getBalance_amount( ));
    }

    @DeleteMapping("/cuentas/{id}")
    public void deleteAccount(@PathVariable("id") Long id)
    {
        accountDeletionService.deleteAccount(id);
    }
}
