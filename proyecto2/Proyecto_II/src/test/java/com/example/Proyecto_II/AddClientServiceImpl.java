package com.example.Proyecto_II;

import com.example.Proyecto_II.model.Client;
import com.example.Proyecto_II.repository.ClientRepository;
import com.example.Proyecto_II.service.impl.AddClientServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AddClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private AddClientServiceImpl addClientService;

    @Test
    void testAddClient_SuccessfulSave() {
        Client client = new Client();
        client.setId(1L);
        client.setDni("12345666");
        client.setFirst_name("Juan");
        client.setLast_name("Pérez");

        Mockito.when(clientRepository.existsByDni("12345666")).thenReturn(false);
        Mockito.when(clientRepository.save(client)).thenReturn(client);

        Client result = addClientService.addClient(client);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(client.getId(), result.getId());
        Mockito.verify(clientRepository).existsByDni("12345666");
        Mockito.verify(clientRepository).save(client);
    }

    @Test
    void testAddClient_DniAlreadyExists() {
        Client client = new Client();
        client.setDni("12345678");
        Mockito.when(clientRepository.existsByDni("12345678")).thenReturn(true);
        Client result = addClientService.addClient(client);
        Assertions.assertNull(result);
        Mockito.verify(clientRepository).existsByDni("12345678");
        Mockito.verify(clientRepository, Mockito.never()).save(Mockito.any(Client.class));
    }
}

