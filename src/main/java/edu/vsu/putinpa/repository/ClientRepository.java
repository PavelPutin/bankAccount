package edu.vsu.putinpa.repository;

import edu.vsu.putinpa.model.Client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepository {
    void save(Client client);
    Optional<Client> getByUUID(UUID uuid);
    List<Client> getByName(String name);
}
