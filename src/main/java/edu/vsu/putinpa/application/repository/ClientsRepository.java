package edu.vsu.putinpa.application.repository;

import edu.vsu.putinpa.application.model.Client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientsRepository {
    Optional<Client> findByUUID(UUID uuid);
    List<Client> findByName(String name);
    List<Client> findAll();
    Client save(Client client);
}
