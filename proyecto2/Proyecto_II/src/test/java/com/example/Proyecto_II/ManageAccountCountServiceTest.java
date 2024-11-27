package com.example.Proyecto_II;

import com.example.Proyecto_II.model.Client;
import com.example.Proyecto_II.repository.ClientRepository;
import com.example.Proyecto_II.service.impl.ClientQueryServiceImpl;
import com.example.Proyecto_II.service.impl.ManageAccountCounttServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ManageAccountCountServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientQueryServiceImpl clientQueryService;

    @InjectMocks
    private ManageAccountCounttServiceImpl manageAccountCountService;

    @Test
    void testIncrementAccountCount() {
        Long clientId = 1L;
        Client client = new Client();
        client.setId(clientId);
        client.setAccountCount(0); // Inicialmente sin cuentas

        Mockito.when(clientQueryService.getClientById(clientId)).thenReturn(client);

        manageAccountCountService.incrementAccountCount(clientId);

        Mockito.verify(clientRepository).save(client); // Verifica que se haya guardado
        Assertions.assertEquals(1, client.getAccountCount()); // Comprueba que el contador se incrementó
    }

    @Test
    void testDecrementAccountCount_WhenCountIsGreaterThanZero() {
        Long clientId = 1L;
        Client client = new Client();
        client.setId(clientId);
        client.setAccountCount(2); // Contador inicial en 2

        Mockito.when(clientQueryService.getClientById(clientId)).thenReturn(client);

        manageAccountCountService.decrementAccountCount(clientId);

        Mockito.verify(clientRepository).save(client); // Verifica que se haya guardado
        Assertions.assertEquals(1, client.getAccountCount()); // Comprueba que el contador se decrementó
    }

    @Test
    void testDecrementAccountCount_WhenCountIsZero() {
        Long clientId = 1L;
        Client client = new Client();
        client.setId(clientId);
        client.setAccountCount(0); // Contador inicial en 0

        Mockito.when(clientQueryService.getClientById(clientId)).thenReturn(client);

        manageAccountCountService.decrementAccountCount(clientId);

        Mockito.verify(clientRepository, Mockito.never()).save(client); // Verifica que no se guardó
        Assertions.assertEquals(0, client.getAccountCount()); // Comprueba que el contador no cambió
    }
}

