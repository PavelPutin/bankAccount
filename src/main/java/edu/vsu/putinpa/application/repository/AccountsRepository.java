package edu.vsu.putinpa.application.repository;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.infrastructure.di.api.Component;
import edu.vsu.putinpa.infrastructure.orm.OrmRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public interface AccountsRepository extends OrmRepository<Account, UUID> {
    Optional<Account> findByUUID(UUID uuid);
    List<Account> findByName(String name);
    List<Account> findByCreator(Client creator);
    List<Account> findAll();
    Account save(Account account);
}
