package com.example.Proyecto_II.service;

public interface ManageAccountCountService {


    void incrementAccountCount(Long  clientId);

    void decrementAccountCount(Long  clientId);
}
