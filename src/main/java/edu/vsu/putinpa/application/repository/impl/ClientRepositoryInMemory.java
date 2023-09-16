package edu.vsu.putinpa.application.repository.impl;

import edu.vsu.putinpa.application.exception.SingletonInstanceNotInitializedException;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.repository.ClientRepository;

import java.util.*;

public class ClientRepositoryInMemory implements ClientRepository {
    private static ClientRepository INSTANCE;
    private final Map<UUID, Client> clients;

    private ClientRepositoryInMemory() {
        clients = new HashMap<>();
    }

    public synchronized static ClientRepository init() {
        INSTANCE = new ClientRepositoryInMemory();
        return INSTANCE;
    }

    public static ClientRepository getInstance() {
        if (INSTANCE == null) {
            throw new SingletonInstanceNotInitializedException(
                    "Class '%s' instance not initialized yet. You should invoke method 'init'."
                            .formatted(ClientRepositoryInMemory.class)
            );
        }

        return INSTANCE;
    }

    @Override
    public void save(Client client) {
        clients.put(client.getUuid(), client);
    }

    @Override
    public Optional<Client> getByUUID(UUID uuid) {
        return Optional.ofNullable(clients.get(uuid));
    }

    @Override
    public List<Client> getByName(String name) {
        return clients.values().stream().filter(client -> client.getName().equals(name)).toList();
    }

    @Override
    public Collection<Client> getAll() {
        return clients.values();
    }

    @Override
    public int count() {
        return clients.values().size();
    }
}
