package com.example.AccountMs;

import com.example.AccountMs.model.Account;
import com.example.AccountMs.model.Client;
import com.example.AccountMs.repository.AccountRepository;
import com.example.AccountMs.service.impl.AccountDeletionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import java.util.NoSuchElementException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountDeletionServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private RestTemplate restTemplate;

    private AccountDeletionServiceImpl accountDeletionService;

    @BeforeEach
    public void setUp() {
        accountDeletionService = new AccountDeletionServiceImpl(accountRepository, restTemplate);
    }

    @Test
    public void deleteAccount_ShouldDeleteAccount_WhenAccountExists() {
        // Arrange: Crear un ID y una cuenta de ejemplo
        Long accountId = 1L;
        Client client = new Client();
        client.setId(123L);
        Account account = new Account();
        account.setId(accountId);
        account.setClient(client);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        accountDeletionService.deleteAccount(accountId);

        // Assert: Verificar que la cuenta fue eliminada y que el RestTemplate fue invocado
        verify(accountRepository).delete(account);
        verify(restTemplate).postForLocation(
                "http://localhost:8085/api/clients/decrementAccountCount/123", null
        );
    }

    @Test
    public void deleteAccount_ShouldThrowException_WhenAccountNotFound() {
        Long accountId = 1L;
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> accountDeletionService.deleteAccount(accountId));
    }
}
