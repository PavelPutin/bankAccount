package edu.vsu.putinpa.repository;

import edu.vsu.putinpa.model.Account;
import edu.vsu.putinpa.model.Client;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {
    Optional<Account> findByUUID(UUID uuid);
    Collection<Account> findByName(String name);
    Collection<Account> findByClient(Client client);
    Account save(Account account);
}
