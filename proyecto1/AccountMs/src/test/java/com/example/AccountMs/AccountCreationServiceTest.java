package com.example.AccountMs;

import com.example.AccountMs.dto.AccountDTO;
import com.example.AccountMs.factory.AccountFactory;
import com.example.AccountMs.model.Account;
import com.example.AccountMs.model.Client;
import com.example.AccountMs.repository.AccountRepository;
import com.example.AccountMs.service.impl.AccountCreationServiceImpl;
import com.example.AccountMs.service.impl.AccountNumberGenerator;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountCreationServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountFactory accountFactory;

    @Mock
    private AccountNumberGenerator accountNumberGenerator;

    private AccountCreationServiceImpl accountCreationService;

    @BeforeEach
    public void setup() {
        accountCreationService = new AccountCreationServiceImpl(accountRepository, accountFactory, accountNumberGenerator);
    }

    @Test
    public void generateUniqueAccountNumber_ShouldReturnValidAccountNumber() throws Exception {
        Method method = AccountCreationServiceImpl.class.getDeclaredMethod("generateUniqueAccountNumber");
        method.setAccessible(true);

        String accountNumber = (String) method.invoke(accountCreationService);

        assertNotNull(accountNumber);
        assertEquals(10, accountNumber.length());
    }
    @Test
    public void createAccount_ShouldReturnAccount_WhenValidAccountDTO() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setClient_id(123L);
        accountDTO.setAccount_type(Account.AccountType.AHORROS);
        accountDTO.setBalance_amount(BigDecimal.valueOf(1000));

        // Crear una cuenta mockeada para simular lo que devuelve accountFactory
        Account mockedAccount = new Account();
        mockedAccount.setId(1L);
        mockedAccount.setAccountNumber("6da092686f");
        mockedAccount.setBalance_amount(BigDecimal.valueOf(1000));
        mockedAccount.setClient(new Client());

        when(accountFactory.createAccount(any(AccountDTO.class), anyString())).thenReturn(mockedAccount);
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> {
            Account savedAccount = invocation.getArgument(0);
            savedAccount.setId(1L);  // Asigna el id esperado antes de devolver el objeto
            return savedAccount;
        });

        Account createdAccount = accountCreationService.createAccount(accountDTO);

        // Verificar que la cuenta se haya creado correctamente
        assertNotNull(createdAccount);
        assertEquals(1L, createdAccount.getId());
        assertEquals("6da092686f", createdAccount.getAccountNumber());
        assertEquals(BigDecimal.valueOf(1000), createdAccount.getBalance_amount());
        assertNotNull(createdAccount.getClient());
    }
}
