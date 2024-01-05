package edu.vsu.putinpa.application.service;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;

import java.util.List;

public interface ClientsService {
    Client register(String name, String password);
    List<Client> getAll();
    Client getByName(String name) throws ClientNotFoundException;
    List<Account> getAccounts(Client client);
}
