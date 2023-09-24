package edu.vsu.putinpa.application.repository;

import edu.vsu.putinpa.application.model.Client;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface ClientsRepository {
    Optional<Client> findByUUID(UUID uuid);
    Collection<Client> findByName(String name);
    Collection<Client> findAll();
    Client save(Client client);
}
