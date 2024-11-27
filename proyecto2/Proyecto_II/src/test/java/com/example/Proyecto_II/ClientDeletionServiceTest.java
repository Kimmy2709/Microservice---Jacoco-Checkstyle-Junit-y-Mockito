package com.example.Proyecto_II;

import com.example.Proyecto_II.model.Client;
import com.example.Proyecto_II.repository.ClientRepository;
import com.example.Proyecto_II.service.impl.ClientDeletionServiceImpl;
import com.example.Proyecto_II.service.impl.ClientQueryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ClientDeletionServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientQueryServiceImpl clientQueryService;

    @InjectMocks
    private ClientDeletionServiceImpl clientDeletionService;

    @Test
    void testDeleteClient_SuccessfulDeletion() {
        Long clientId = 1L;
        Client client = new Client();
        client.setId(clientId);
        client.setAccountCount(0); // Sin cuentas activas
        Mockito.when(clientQueryService.getClientById(clientId)).thenReturn(client);
        Client result = clientDeletionService.deleteClient(clientId);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(clientId, result.getId());
        Mockito.verify(clientRepository).delete(client);
    }

    @Test
    void testDeleteClient_HasActiveAccounts() {
        Long clientId = 1L;
        Client client = new Client();
        client.setId((long) clientId);
        client.setAccountCount(2); // Con cuentas activas
        Mockito.when(clientQueryService.getClientById(clientId)).thenReturn(client);
        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, () -> {
            clientDeletionService.deleteClient(clientId);
        });
        Assertions.assertEquals("No se puede eliminar el cliente porque tiene cuentas activas.", exception.getMessage());
        Mockito.verify(clientRepository, Mockito.never()).delete(Mockito.any(Client.class));
    }
}