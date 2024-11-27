package com.example.Proyecto_II.service.impl;

import com.example.Proyecto_II.model.Client;
import com.example.Proyecto_II.repository.ClientRepository;
import com.example.Proyecto_II.service.AddClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddClientServiceImpl implements AddClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public AddClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client addClient(Client client) {
        if (clientRepository.existsByDni(client.getDni())) {
            System.out.println("El DNI ya se encuentra registrado");
            return null;
        }
        return clientRepository.save(client);
    }
}
