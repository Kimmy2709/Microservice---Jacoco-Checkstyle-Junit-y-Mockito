package com.example.AccountMs;

import com.example.AccountMs.dto.AccountDTO;
import com.example.AccountMs.model.Account;
import com.example.AccountMs.model.Client;
import com.example.AccountMs.repository.AccountRepository;
import com.example.AccountMs.service.AccountQueryService;
import com.example.AccountMs.service.impl.DepositServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DepositServiceTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountQueryService accountQueryService;
    @InjectMocks
    private DepositServiceImpl depositService;

    private Account account;
    private AccountDTO accountDTO;

    @BeforeEach
    public void setUp() {
        Client client = new Client(1L, "Juan", "Perez", "12345678", "juan@example.com");
        account = new Account(1L, "123456", new BigDecimal("1000.0"), Account.AccountType.AHORROS, client);
        accountDTO = new AccountDTO();
    }

    @Test
    void makeDeposit_ShouldIncreaseBalance_WhenValidAmount() {
        BigDecimal depositAmount = new BigDecimal("500.0");
        Mockito.when(accountQueryService.getAccountById(1L)).thenReturn(account);
        Mockito.when(accountRepository.save(account)).thenReturn(account);
        Account result = depositService.makeDeposit(1L, depositAmount);
        assertNotNull(result);
        assertEquals(new BigDecimal("1500.0"), result.getBalance_amount());
        Mockito.verify(accountQueryService).getAccountById(1L); // Verificar que se llamó a este servicio
        Mockito.verify(accountRepository).save(account); // Verificar que se guardó la cuenta
    }

    @Test
    void makeDeposit_ShouldThrowException_WhenAmountIsZeroOrNegative() {
        BigDecimal invalidDepositAmount = new BigDecimal("0.0");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            depositService.makeDeposit(1L, invalidDepositAmount);
        });
        assertEquals("El monto del depósito debe ser mayor a cero.", exception.getMessage());
        Mockito.verify(accountQueryService, Mockito.never()).getAccountById(1L);
        Mockito.verify(accountRepository, Mockito.never()).save(account);
    }

    @Test
    void makeDeposit_ShouldThrowException_WhenAccountNotFound() {
        BigDecimal depositAmount = new BigDecimal("500.0");
        Mockito.when(accountQueryService.getAccountById(1L)).thenThrow(new NoSuchElementException("Cuenta no encontrada"));
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            depositService.makeDeposit(1L, depositAmount);
        });
        assertEquals("Cuenta no encontrada", exception.getMessage());
        Mockito.verify(accountRepository, Mockito.never()).save(account);
    }
}

