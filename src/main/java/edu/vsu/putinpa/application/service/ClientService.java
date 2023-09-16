package edu.vsu.putinpa.application.service;

import edu.vsu.putinpa.application.model.Client;

import java.util.Collection;

public interface ClientService {
    Client register(String name);
    Collection<Client> getAll();
}
