package com.example.Proyecto_II.service.impl;

import com.example.Proyecto_II.model.Client;
import com.example.Proyecto_II.repository.ClientRepository;
import com.example.Proyecto_II.service.UpdateClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateClientSrviceImpl implements UpdateClientService {

    private final ClientRepository clientRepository;
    private final ClientQueryServiceImpl clientQueryService;

    @Autowired
    public UpdateClientSrviceImpl(ClientRepository clientRepository, ClientQueryServiceImpl clientQueryService) {

        this.clientRepository = clientRepository;
        this.clientQueryService = clientQueryService;
    }
    public Client updateClient(Long id, Client updatedClient) {
        Client foundClient = clientQueryService.getClientById(id);
        foundClient.setFirst_name(updatedClient.getFirst_name());
        foundClient.setLast_name(updatedClient.getLast_name());
        foundClient.setEmail(updatedClient.getEmail());
        return clientRepository.save(foundClient);
    }
}
