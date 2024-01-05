package edu.vsu.putinpa.application.service.impl;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.repository.AccountsRepository;
import edu.vsu.putinpa.application.repository.ClientsRepository;
import edu.vsu.putinpa.application.service.ClientNotFoundException;
import edu.vsu.putinpa.application.service.ClientsService;
import edu.vsu.putinpa.infrastructure.di.api.AutoInjected;
import edu.vsu.putinpa.infrastructure.di.api.Component;

import java.util.List;

@Component
public class ClientsServiceImpl implements ClientsService {
    private final AccountsRepository accountsRepository;
    private final ClientsRepository clientsRepository;

    @AutoInjected
    public ClientsServiceImpl(AccountsRepository accountsRepository, ClientsRepository clientsRepository) {
        this.accountsRepository = accountsRepository;
        this.clientsRepository = clientsRepository;
    }

    @Override
    public Client register(String name, String password) {
        if (clientsRepository.findByName(name).isPresent()) {
            throw new IllegalArgumentException("Client with name \"%s\" is already exist.".formatted(name));
        }
        Client newClient = new Client(name, password);
        return clientsRepository.save(newClient);
    }

    @Override
    public List<Client> getAll() {
        return clientsRepository.findAll();
    }

    @Override
    public Client getByName(String name) throws ClientNotFoundException {
        return clientsRepository.findByName(name)
                .orElseThrow(ClientNotFoundException::new);
    }

    @Override
    public List<Account> getAccounts(Client client) {
        return accountsRepository.findByCreator(client);
    }
}
