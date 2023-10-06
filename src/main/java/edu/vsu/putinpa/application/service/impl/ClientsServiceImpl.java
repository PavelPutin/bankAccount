package edu.vsu.putinpa.application.service.impl;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.repository.AccountsRepository;
import edu.vsu.putinpa.application.service.AccountsService;
import edu.vsu.putinpa.application.service.ClientsService;
import edu.vsu.putinpa.infrastructure.di.api.AutoInjected;

import java.util.Collection;
import java.util.List;

public class ClientsServiceImpl implements ClientsService {
    private final AccountsRepository accountsRepository;

    @AutoInjected
    public ClientsServiceImpl(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    @Override
    public Client register(String name) {
        return null;
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
