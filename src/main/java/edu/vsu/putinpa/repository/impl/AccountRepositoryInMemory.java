package edu.vsu.putinpa.repository.impl;

import edu.vsu.putinpa.exception.SingletonInstanceNotInitializedException;
import edu.vsu.putinpa.model.Account;
import edu.vsu.putinpa.model.Client;
import edu.vsu.putinpa.repository.AccountRepository;

import java.util.*;

public class AccountRepositoryInMemory implements AccountRepository {

    private static AccountRepository INSTANCE;
    private final Map<UUID, Account> accounts;

    private AccountRepositoryInMemory() {
        accounts = new HashMap<>();
    }

    public synchronized static AccountRepository init() {
        INSTANCE = new AccountRepositoryInMemory();
        return INSTANCE;
    }

    public static AccountRepository getInstance() {
        if (INSTANCE == null) {
            throw new SingletonInstanceNotInitializedException(
                    "Class '%s' instance not initialized yet. You should invoke method 'init'."
                            .formatted(ClientRepositoryInMemory.class)
            );
        }

        return INSTANCE;
    }

    @Override
    public Optional<Account> findByUUID(UUID uuid) {
        return Optional.ofNullable(accounts.get(uuid));
    }

    @Override
    public Collection<Account> findByName(String name) {
        return accounts.values().stream().filter(account -> account.getName().equals(name)).toList();
    }

    @Override
    public Collection<Account> findByCreator(Client creator) {
        return accounts.values().stream().filter(account -> account.getCreator().equals(creator)).toList();
    }

    @Override
    public Account save(Account account) {
        return accounts.put(account.getUuid(), account);
    }
}
