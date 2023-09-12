package edu.vsu.putinpa.repository;

import edu.vsu.putinpa.model.Client;

import java.util.UUID;

public interface ClientRepository {
    void save(Client client);
    Client getByUUID(UUID uuid);
    Client getByName(String name);
}
