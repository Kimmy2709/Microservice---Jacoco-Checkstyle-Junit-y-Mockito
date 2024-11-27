package com.example.Proyecto_II.service.impl;

import com.example.Proyecto_II.model.Client;
import com.example.Proyecto_II.repository.ClientRepository;
import com.example.Proyecto_II.service.ManageAccountCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManageAccountCounttServiceImpl implements ManageAccountCountService {

    private final ClientRepository clientRepository;
    private final ClientQueryServiceImpl clientQueryService;

    @Autowired
    public ManageAccountCounttServiceImpl(ClientRepository clientRepository, ClientQueryServiceImpl clientQueryService) {
        this.clientRepository = clientRepository;
        this.clientQueryService = clientQueryService;
    }


    public void incrementAccountCount(Long  clientId) {
        Client client = clientQueryService.getClientById(clientId);
        client.setAccountCount(client.getAccountCount() + 1);
        clientRepository.save(client);
    }


    public void decrementAccountCount(Long  clientId) {
        Client client = clientQueryService.getClientById(clientId);
        if (client.getAccountCount() > 0) {
            client.setAccountCount(client.getAccountCount() - 1);
            clientRepository.save(client);
        }
    }
}
