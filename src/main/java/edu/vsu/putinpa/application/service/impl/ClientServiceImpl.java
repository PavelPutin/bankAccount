package edu.vsu.putinpa.application.service.impl;

import edu.vsu.putinpa.application.exception.SingletonInstanceNotInitializedException;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.repository.ClientRepository;
import edu.vsu.putinpa.application.service.ClientService;

import java.util.Collection;

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
