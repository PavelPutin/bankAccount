package edu.vsu.putinpa.application.service;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;

import java.util.Collection;
import java.util.List;

public interface ClientsService {
    Client register(String name);
    Collection<Client> getAll();
    List<Account> getAccounts(Client client);
}
