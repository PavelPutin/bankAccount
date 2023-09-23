package edu.vsu.putinpa.application.repository.impl;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.repository.AccountsRepository;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryAccountRepository implements AccountsRepository {
    private final Map<UUID, Account> data = new HashMap<>();
    @Override
    public Optional<Account> findByUUID(UUID uuid) {
        return Optional.ofNullable(data.get(uuid));
    }

    @Override
    public Collection<Account> findByName(String name) {
        return data.values().stream().filter(account -> account.getName().equals(name)).collect(Collectors.toSet());
    }

    @Override
    public Collection<Account> findByCreator(Client creator) {
        return data.values().stream().filter(account -> account.getCreator().equals(creator)).collect(Collectors.toSet());
    }

    @Override
    public Account save(Account account) {
        return data.put(account.getUuid(), account);
    }

    @Override
    public Collection<Account> findAll() {
        return data.values();
    }
}
