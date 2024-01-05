package edu.vsu.putinpa.application.repository.impl;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.repository.AccountsRepository;

import java.util.*;

//@Component
public class InMemoryAccountsRepository implements AccountsRepository {
    private final Map<UUID, Account> data = new HashMap<>();

    public InMemoryAccountsRepository() {}

    public InMemoryAccountsRepository(Account... accounts) {
        for (Account account : accounts) {
            data.put(account.getUuid(), account);
        }
    }
    @Override
    public Optional<Account> findByUUID(UUID uuid) {
        return Optional.ofNullable(data.get(uuid));
    }

    @Override
    public List<Account> findByName(String name) {
        return data.values().stream().filter(account -> account.getName().equals(name)).toList();
    }

    @Override
    public List<Account> findByCreator(Client creator) {
        return data.values().stream().filter(account -> account.getCreator().equals(creator)).toList();
    }

    @Override
    public Account save(Account account) {
        return data.put(account.getUuid(), account);
    }

    @Override
    public List<Account> findAll() {
        return List.copyOf(data.values());
    }
}
