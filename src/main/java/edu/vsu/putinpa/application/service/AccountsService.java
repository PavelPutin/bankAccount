package edu.vsu.putinpa.application.service;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface AccountsService {
    Optional<Account> getBy(UUID uuid);
    Collection<Account> getBy(String name);
    Collection<Account> getBy(Client creator);
    Collection<Account> getAll();
    Account save(Account account);
}
