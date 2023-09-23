package edu.vsu.putinpa.application.repository;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface AccountsRepository {
    Optional<Account> findByUUID(UUID uuid);
    Collection<Account> findByName(String name);
    Collection<Account> findByCreator(Client creator);
    Account save(Account account);
}
