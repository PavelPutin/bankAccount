package edu.vsu.putinpa.application.repository;

import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.infrastructure.di.api.Component;
import edu.vsu.putinpa.infrastructure.orm.api.OrmRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public interface ClientsRepository extends OrmRepository<Client, UUID> {
    Optional<Client> findByUUID(UUID uuid);
    Optional<Client> findByName(String name);
    List<Client> findAll();
    Client save(Client client);
}
