package edu.vsu.putinpa.application.repository;

import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.infrastructure.di.api.Component;
import edu.vsu.putinpa.infrastructure.orm.api.OrmRepository;
import edu.vsu.putinpa.infrastructure.orm.api.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public interface ClientsRepository extends OrmRepository<Client, UUID> {
    @Query("select * from client where id = ?;")
    Optional<Client> findByUUID(UUID uuid);

    @Query("select * from client where name = ?;")
    Optional<Client> findByName(String name);

    @Query("select * from client;")
    List<Client> findAll();
    Client save(Client client);
}
