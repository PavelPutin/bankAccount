package edu.vsu.putinpa.application.repository.impl;

import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.repository.ClientsRepository;
import edu.vsu.putinpa.infrastructure.di.api.Component;

import java.util.*;

@Component
public class InMemoryClientsRepository implements ClientsRepository {

    private final Map<UUID, Client> data = new HashMap<>();

    @Override
    public Optional<Client> findByUUID(UUID uuid) {
        return Optional.ofNullable(data.get(uuid));
    }

    @Override
    public List<Client> findByName(String name) {
        return data.values().stream().filter(client -> client.getName().equals(name)).toList();
    }

    @Override
    public List<Client> findAll() {
        return List.copyOf(data.values());
    }

    @Override
    public Client save(Client client) {
        return data.put(client.getUuid(), client);
    }
}
