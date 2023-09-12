package edu.vsu.putinpa.service;

import edu.vsu.putinpa.model.Client;

import java.util.Collection;
import java.util.List;

public interface ClientService {
    Client register(String name);
    Collection<Client> getAll();
}
