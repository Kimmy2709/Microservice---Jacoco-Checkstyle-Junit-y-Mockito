package com.example.AccountMs;

import com.example.AccountMs.model.Account;
import com.example.AccountMs.model.Client;
import com.example.AccountMs.repository.AccountRepository;
import com.example.AccountMs.service.impl.AccountQueryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountQueryServiceTest {

    @Mock(lenient = true)
    private AccountRepository accountRepository;
    @InjectMocks
    private AccountQueryServiceImpl accountQueryService;

    private Account account1;
    private Account account2;

    @BeforeEach
    public void setUp() {
        Client client1 = new Client(1L, "Nombre", "Apellido", "76567876", "email@example.com");
        Client client2 = new Client(2L, "Elsa", "Estrella", "23456789", "elsa@example.com");

        account1 = new Account(1L, "123456", new BigDecimal("1000.0"), Account.AccountType.AHORROS, client1);
        account2 = new Account(2L, "76435646", new BigDecimal("1000.0"), Account.AccountType.CORRIENTE, client2);
    }

    @Test
    void listAccounts_ShouldReturnAllAccounts() {
        Mockito.when(accountRepository.findAll()).thenReturn(Arrays.asList(account1, account2));
        List<Account> result = accountQueryService.listAccounts();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("123456", result.get(0).getAccountNumber());
        assertEquals("76435646", result.get(1).getAccountNumber());
        Mockito.verify(accountRepository).findAll();
    }

    @Test
    void getAccountById_ShouldReturnAccount_WhenAccountExists() {
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account1));
        Account result = accountQueryService.getAccountById(1L);
        assertNotNull(result);
        assertEquals("123456", result.getAccountNumber());
        Mockito.verify(accountRepository).findById(1L);
    }

    @Test
    void getAccountById_ShouldThrowException_WhenAccountNotFound() {
        Mockito.when(accountRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchElementException.class,
                () -> accountQueryService.getAccountById(2L));

        assertEquals("cliente no encontrado", exception.getMessage());
        Mockito.verify(accountRepository).findById(2L);
    }
}

