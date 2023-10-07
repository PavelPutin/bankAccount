package edu.vsu.putinpa.application.service.impl;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.repository.AccountsRepository;
import edu.vsu.putinpa.application.repository.ClientsRepository;
import edu.vsu.putinpa.application.service.ClientsService;
import edu.vsu.putinpa.infrastructure.di.api.AutoInjected;
import edu.vsu.putinpa.infrastructure.di.api.Component;

import java.util.Collection;
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
        Client newClient = new Client(name, password);
        return clientsRepository.save(newClient);
    }

    @Override
    public Collection<Client> getAll() {
        return null;
    }

    @Override
    public List<Account> getAccounts(Client client) {
        return accountsRepository.findByCreator(client);
    }
}
