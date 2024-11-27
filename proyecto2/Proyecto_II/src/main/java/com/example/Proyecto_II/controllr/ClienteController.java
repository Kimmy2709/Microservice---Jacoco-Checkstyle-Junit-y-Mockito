package com.example.Proyecto_II.controllr;

import com.example.Proyecto_II.model.Client;
import com.example.Proyecto_II.service.ClientQueryService;
import com.example.Proyecto_II.service.ManageAccountCountService;
import com.example.Proyecto_II.service.impl.AddClientServiceImpl;
import com.example.Proyecto_II.service.impl.ClientDeletionServiceImpl;
import com.example.Proyecto_II.service.impl.UpdateClientSrviceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class ClienteController {

    /** Servicio para gestionar el conteo de cuentas del cliente. */
    private final ManageAccountCountService clientService;

    /** Servicio para consultar información del cliente. */
    private final ClientQueryService clientQueryService;

    /** Servicio para eliminar clientes. */
    private final ClientDeletionServiceImpl clientDeletionService;

    /** Servicio para actualizar información del cliente. */
    private final UpdateClientSrviceImpl updateClientSrvice;

    /** Servicio para añadir un cliente. */
    private final AddClientServiceImpl addClientService;
    /**
     * Agrega un nuevo cliente al sistema.
     */
    @PostMapping("/clients")
    public Client addClient(@Validated @RequestBody final Client client) {
        return addClientService.addClient(client);
    }

    /**
     * Obtiene una lista de todos los clientes.
     */
    @GetMapping("/clients")
    public List<Client> listClients(@RequestHeader final  Map<String, String>
                                                headers) {
        return clientQueryService.listClients();
    }

    /**
     * Encuentra un cliente por su ID.
     */
    @GetMapping("/clients/{id}")
    public Client findAccount(@PathVariable("id") final Long id,
                              @RequestHeader final Map<String, String>
                                      headers) {
        return clientQueryService.getClientById(id);
    }

    /**
     * Actualiza los datos de un cliente existente.
     */
    @PutMapping("/clients/{id}")
    public Client  updatedAccount(@PathVariable("id") final Long id,
                                  @RequestBody final Client updatedClient) {
        return updateClientSrvice.updateClient(id, updatedClient);
    }

    /**
     * Elimina un cliente por su ID.

     */
    @DeleteMapping("/clients/{id}")
    public Client deleteClient(@PathVariable("id") final Long id) {
        return clientDeletionService.deleteClient(id);
    }

}
