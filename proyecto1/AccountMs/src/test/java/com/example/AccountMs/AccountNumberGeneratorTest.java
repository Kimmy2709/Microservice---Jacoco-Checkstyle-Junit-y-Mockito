package com.example.AccountMs;

import com.example.AccountMs.repository.AccountRepository;
import com.example.AccountMs.service.impl.AccountNumberGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountNumberGeneratorTest {

    @Test
    public void testGenerateUniqueAccountNumber() {
        AccountRepository accountRepository = mock(AccountRepository.class);
        AccountNumberGenerator accountNumberGenerator = new AccountNumberGenerator();
        String existingAccountNumber = "1234567890";
        when(accountRepository.existsByAccountNumber(existingAccountNumber)).thenReturn(true);
        String newAccountNumber = "9876543210";
        when(accountRepository.existsByAccountNumber(newAccountNumber)).thenReturn(false);
        String generatedAccountNumber = accountNumberGenerator.generateUniqueAccountNumber(accountRepository);
        assertNotNull(generatedAccountNumber);
        assertNotEquals(existingAccountNumber, generatedAccountNumber);

        assertEquals(10, generatedAccountNumber.length());
    }
}
