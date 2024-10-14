package com.example.fitlove.services;

import com.example.fitlove.models.Clients;
import com.example.fitlove.repositories.ClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ClientsService {

    private final ClientsRepository clientsRepository;

    @Autowired
    public ClientsService(ClientsRepository clientsRepository) {
        this.clientsRepository = clientsRepository;
    }

    // Получение всех клиентов
    public List<Clients> getAllClients() {
        return clientsRepository.findAll();
    }

    // Получение клиента по ID
    public Optional<Clients> getClientById(int id) {
        return clientsRepository.findById(id);
    }

    // Сохранение нового клиента
    @Transactional
    public Clients saveClient(Clients client) {
        return clientsRepository.save(client);
    }

    // Обновление существующего клиента
    @Transactional
    public Clients updateClient(Clients client) {
        return clientsRepository.findById(client.getId())
                .map(existingClient -> {
                    existingClient.setName(client.getName());
                    existingClient.setEmail(client.getEmail());
                    existingClient.setPassword(client.getPassword());
                    existingClient.setPhone(client.getPhone());
                    return clientsRepository.save(existingClient);
                })
                .orElseThrow(() -> new RuntimeException("Client not found with id " + client.getId()));
    }

    // Удаление клиента по ID
    @Transactional
    public void deleteClient(int clientId) {
        clientsRepository.deleteById(clientId);
    }


}
