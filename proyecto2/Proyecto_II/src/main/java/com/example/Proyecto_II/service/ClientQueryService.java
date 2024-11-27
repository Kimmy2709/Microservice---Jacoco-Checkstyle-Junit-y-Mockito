package com.example.Proyecto_II.service;

import com.example.Proyecto_II.model.Client;

import java.util.List;

public interface ClientQueryService {
    public List<Client> listClients();

    public Client getClientById(Long  id);
}
