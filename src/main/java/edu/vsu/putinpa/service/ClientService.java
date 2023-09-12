package edu.vsu.putinpa.service;

import edu.vsu.putinpa.model.Client;

import java.util.Collection;

public interface ClientService {
    Client register(String name);
    Collection<Client> getAll();
}
