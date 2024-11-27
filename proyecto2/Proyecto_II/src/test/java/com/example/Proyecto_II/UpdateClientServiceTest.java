package com.example.Proyecto_II;

import com.example.Proyecto_II.model.Client;
import com.example.Proyecto_II.repository.ClientRepository;
import com.example.Proyecto_II.service.impl.ClientQueryServiceImpl;
import com.example.Proyecto_II.service.impl.UpdateClientSrviceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;

@ExtendWith(MockitoExtension.class)
public class UpdateClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientQueryServiceImpl clientQueryService;

    @InjectMocks
    private UpdateClientSrviceImpl updateClientService;

    @Test
    void testUpdateClient_SuccessfulUpdate() {
        Long clientId = 1L;

        Client existingClient = new Client();
        existingClient.setId(clientId);
        existingClient.setFirst_name("John");
        existingClient.setLast_name("Doe");
        existingClient.setEmail("john.doe@example.com");

        Client updatedClient = new Client();
        updatedClient.setFirst_name("Jane");
        updatedClient.setLast_name("Smith");
        updatedClient.setEmail("jane.smith@example.com");

        Mockito.when(clientQueryService.getClientById(clientId)).thenReturn(existingClient);
        Mockito.when(clientRepository.save(existingClient)).thenReturn(existingClient);

        Client result = updateClientService.updateClient(clientId, updatedClient);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Jane", result.getFirst_name());
        Assertions.assertEquals("Smith", result.getLast_name());
        Assertions.assertEquals("jane.smith@example.com", result.getEmail());
        Mockito.verify(clientRepository).save(existingClient);
    }

    @Test
    void testUpdateClient_ClientNotFound() {
        Long clientId = 1L;

        Client updatedClient = new Client();
        updatedClient.setFirst_name("Jane");
        updatedClient.setLast_name("Smith");
        updatedClient.setEmail("jane.smith@example.com");

        Mockito.when(clientQueryService.getClientById(clientId))
                .thenThrow(new NoSuchElementException("Customer not found"));

        NoSuchElementException exception = Assertions.assertThrows(
                NoSuchElementException.class,
                () -> updateClientService.updateClient(clientId, updatedClient)
        );

        Assertions.assertEquals("Customer not found", exception.getMessage());
        Mockito.verify(clientRepository, Mockito.never()).save(Mockito.any(Client.class));
    }
}
