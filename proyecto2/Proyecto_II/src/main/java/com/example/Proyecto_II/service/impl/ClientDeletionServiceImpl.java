package com.example.Proyecto_II.service.impl;

import com.example.Proyecto_II.model.Client;
import com.example.Proyecto_II.repository.ClientRepository;
import com.example.Proyecto_II.service.ClientDeletionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientDeletionServiceImpl implements ClientDeletionService {
    private final ClientRepository clientRepository;
    private final ClientQueryServiceImpl clientQueryService;

    @Autowired
    public ClientDeletionServiceImpl(ClientRepository clientRepository, ClientQueryServiceImpl clientQueryService) {

        this.clientRepository = clientRepository;
        this.clientQueryService = clientQueryService;
    }

    public Client deleteClient(Long  clientId) {
        Client client = clientQueryService.getClientById(clientId);

        if (client.getAccountCount() > 0) {
            throw new IllegalStateException("No se puede eliminar el cliente porque tiene cuentas activas.");
        }
        clientRepository.delete(client);
        return client;
    }
}
