package edu.vsu.putinpa.service.impl;

import edu.vsu.putinpa.exception.SingletonInstanceNotInitializedException;
import edu.vsu.putinpa.model.Client;
import edu.vsu.putinpa.repository.ClientRepository;
import edu.vsu.putinpa.service.ClientService;

import java.util.Collection;
import java.util.List;

public class ClientServiceImpl implements ClientService {
    private static ClientService INSTANCE;
    private final ClientRepository clientRepository;

    private ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public synchronized static ClientService init(ClientRepository clientRepository) {
        INSTANCE = new ClientServiceImpl(clientRepository);
        return INSTANCE;
    }

    public static ClientService getInstance() {
        if (INSTANCE == null) {
            throw new SingletonInstanceNotInitializedException(
                    "Class '%s' instance not initialized yet. You should invoke method 'init'."
                            .formatted(ClientServiceImpl.class)
            );
        }

        return INSTANCE;
    }

    @Override
    public Client register(String name) {
        Client client = new Client(name);
        clientRepository.save(client);
        return client;
    }

    @Override
    public Collection<Client> getAll() {
        return clientRepository.getAll();
    }
}
