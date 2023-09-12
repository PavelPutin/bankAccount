package edu.vsu.putinpa.service.impl;

import edu.vsu.putinpa.exception.SingletonInstanceNotInitializedException;
import edu.vsu.putinpa.model.Account;
import edu.vsu.putinpa.model.Client;
import edu.vsu.putinpa.model.Money;
import edu.vsu.putinpa.repository.AccountRepository;
import edu.vsu.putinpa.service.AccountsService;

public class AccountsServiceImpl implements AccountsService {
    private static AccountsServiceImpl INSTANCE;
    private final AccountRepository accountRepository;

    private AccountsServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public synchronized static AccountsServiceImpl init(AccountRepository accountRepository) {
        INSTANCE = new AccountsServiceImpl(accountRepository);
        return INSTANCE;
    }

    public static AccountsServiceImpl getInstance() {
        if (INSTANCE == null) {
            throw new SingletonInstanceNotInitializedException(
                    "Class '%s' instance not initialized yet. You should invoke method 'init'."
                            .formatted(ClientServiceImpl.class)
            );
        }

        return INSTANCE;
    }

    @Override
    public Account open(String name, Client creator, Money initialValue) {
        Account account = new Account(name, creator);
        accountRepository.save(account);
        return account;
    }

    @Override
    public Account close(Account account, Account recipient) {
        return null;
    }
}
