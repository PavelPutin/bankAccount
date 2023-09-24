package edu.vsu.putinpa.application.service.impl;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.repository.AccountsRepository;
import edu.vsu.putinpa.application.service.AccountsService;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public class AccountsServiceImpl implements AccountsService {
    private AccountsRepository accountsRepository;

    public AccountsServiceImpl(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    @Override
    public Optional<Account> getBy(UUID uuid) {
        return accountsRepository.findByUUID(uuid);
    }

    @Override
    public Collection<Account> getBy(String name) {
        return accountsRepository.findByName(name);
    }

    @Override
    public Collection<Account> getBy(Client creator) {
        return accountsRepository.findByCreator(creator);
    }

    @Override
    public Collection<Account> getAll() {
        return accountsRepository.findAll();
    }

    @Override
    public Account save(Account account) {
        return accountsRepository.save(account);
    }
}
