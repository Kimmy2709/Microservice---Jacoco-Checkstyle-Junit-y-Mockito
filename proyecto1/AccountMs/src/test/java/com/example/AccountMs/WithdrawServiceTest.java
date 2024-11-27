package com.example.AccountMs;

import com.example.AccountMs.dto.AccountDTO;
import com.example.AccountMs.model.Account;
import com.example.AccountMs.model.Client;
import com.example.AccountMs.repository.AccountRepository;
import com.example.AccountMs.service.AccountQueryService;
import com.example.AccountMs.service.impl.WithdrawServiceImpl;
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
class WithdrawServiceTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountQueryService accountQueryService;
    @InjectMocks
    private WithdrawServiceImpl withdrawService;

    private Account account;
    private AccountDTO accountDTO;

    @BeforeEach
    public void setUp() {
        Client client = new Client(1L, "Juan", "Perez", "12345678", "juan@example.com");
        account = new Account(1L, "123456", new BigDecimal("1000.0"), Account.AccountType.AHORROS, client);
        accountDTO = new AccountDTO();
    }

    @Test
    void withDrawMoney_ShouldDecreaseBalance_WhenSufficientFunds() {
        BigDecimal withdrawAmount = new BigDecimal("500.0");
        Mockito.when(accountQueryService.getAccountById(1L)).thenReturn(account);
        Mockito.when(accountRepository.save(account)).thenReturn(account);

        Account result = withdrawService.withDrawMoney(1L, withdrawAmount);

        assertNotNull(result);
        assertEquals(new BigDecimal("500.0"), result.getBalance_amount());
        Mockito.verify(accountQueryService).getAccountById(1L);
        Mockito.verify(accountRepository).save(account);
    }

    @Test
    void withDrawMoney_ShouldThrowException_WhenInsufficientFunds() {
        // Intentar retirar más de lo que hay en la cuenta
        BigDecimal withdrawAmount = new BigDecimal("1500.0");

        Mockito.when(accountQueryService.getAccountById(1L)).thenReturn(account);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            withdrawService.withDrawMoney(1L, withdrawAmount);
        });
        assertEquals("Fondos insuficientes.", exception.getMessage());

        Mockito.verify(accountRepository, Mockito.never()).save(account);
    }

    @Test
    void withDrawMoney_ShouldThrowException_WhenAccountNotFound() {
        // Para devolver una cuenta vacía
        BigDecimal withdrawAmount = new BigDecimal("500.0");
        Mockito.when(accountQueryService.getAccountById(1L)).thenThrow(new NoSuchElementException("Cuenta no encontrada"));

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            withdrawService.withDrawMoney(1L, withdrawAmount);
        });
        assertEquals("Cuenta no encontrada", exception.getMessage());

        Mockito.verify(accountRepository, Mockito.never()).save(account);
    }
}

