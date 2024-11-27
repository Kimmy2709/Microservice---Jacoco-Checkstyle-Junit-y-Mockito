package com.example.Proyecto_II;

import com.example.Proyecto_II.model.Client;
import com.example.Proyecto_II.repository.ClientRepository;
import com.example.Proyecto_II.service.impl.ClientQueryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ClientQueryServiceTest {
    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientQueryServiceImpl clientQueryService;

    @Test
    void testListClients() {
        List<Client> mockClients = Arrays.asList(
                new Client(1L, "John", "Doe", "12345678", "john.doe@example.com", 2),
                new Client(2L, "Jane", "Doe", "87654321", "jane.doe@example.com", 0)
        );
        Mockito.when(clientRepository.findAll()).thenReturn(mockClients);

        // Act: Llamar al método que se está probando
        List<Client> result = clientQueryService.listClients();

        // Assert: Verificar los resultados
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.stream().allMatch(client -> client.getAccountCount() >= 0));
        Mockito.verify(clientRepository, Mockito.times(1)).findAll();
    }
    @Test
    void testGetClientById_ClientExists() {
        // Arrange: Crear un cliente de ejemplo
        Long clientId = 1L; // Cambiado a int
        Client mockClient = new Client( clientId, "John", "Doe", "12345678", "john.doe@example.com", 0);
        Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.of(mockClient));

        // Act: Llamar al método que se está probando
        Client result = clientQueryService.getClientById(clientId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("John", result.getFirst_name());
        Assertions.assertEquals("Doe", result.getLast_name());
        Assertions.assertEquals("12345678", result.getDni());
        Mockito.verify(clientRepository, Mockito.times(1)).findById((long)clientId);
    }

    @Test
    void testGetClientById_ClientDoesNotExist() {
        Long clientId = 99L;
        Mockito.when(clientRepository.findById((long)clientId)).thenReturn(Optional.empty());

        // Act & Assert: Verificar que se lanza la excepción esperada
        Exception exception = Assertions.assertThrows(NoSuchElementException.class, () -> {
            clientQueryService.getClientById(clientId);
        });

        Assertions.assertEquals("Customer not found", exception.getMessage());
        Mockito.verify(clientRepository, Mockito.times(1)).findById((long)clientId);
    }

}
