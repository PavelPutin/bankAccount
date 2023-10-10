package edu.vsu.putinpa.application.service;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.Money;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountsService {
    Optional<Account> getBy(UUID uuid);
    List<Account> getBy(String name);
    List<Account> getBy(Client creator);
    List<Account> getAll();
    Account save(Account account);
    Money getBalance(UUID uuid);
}
