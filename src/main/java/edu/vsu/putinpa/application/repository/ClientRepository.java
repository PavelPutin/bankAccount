package edu.vsu.putinpa.application.repository;

import edu.vsu.putinpa.application.model.Client;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepository {
    void save(Client client);
    Optional<Client> getByUUID(UUID uuid);
    List<Client> getByName(String name);

    Collection<Client> getAll();
    int count();
}
