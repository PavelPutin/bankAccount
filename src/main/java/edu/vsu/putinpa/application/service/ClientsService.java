package edu.vsu.putinpa.application.service;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ClientsService {
    Client register(String name, String password);
    List<Client> getAll();
    List<Account> getAccounts(Client client);
}
