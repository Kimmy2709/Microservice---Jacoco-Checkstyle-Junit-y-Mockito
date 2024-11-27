package com.example.Proyecto_II.service.impl;

import com.example.Proyecto_II.model.Client;
import com.example.Proyecto_II.repository.ClientRepository;
import com.example.Proyecto_II.service.ClientQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class ClientQueryServiceImpl implements ClientQueryService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientQueryServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> listClients() {
        return clientRepository.findAll().stream()
                .filter(client -> client.getAccountCount() >= 0)
                .collect(Collectors.toList());
    }

    public Client getClientById(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        return client.orElseThrow(() -> new NoSuchElementException("Customer not found"));
    }
}
