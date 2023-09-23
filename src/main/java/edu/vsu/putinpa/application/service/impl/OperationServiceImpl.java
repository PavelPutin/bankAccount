package edu.vsu.putinpa.application.service.impl;

import edu.vsu.putinpa.application.repository.AccountsRepository;
import edu.vsu.putinpa.application.repository.OperationsRepository;
import edu.vsu.putinpa.application.service.Operation;
import edu.vsu.putinpa.application.service.OperationService;

public class OperationServiceImpl implements OperationService {
    private AccountsRepository accountsRepository;

    public OperationServiceImpl(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    @Override
    public void executeOperation(Operation<?> operation) {
        operation.execute();
    }

    @Override
    public OperationsRepository getOperationsRepository() {
        return null;
    }

    @Override
    public AccountsRepository getAccountRepository() {
        return accountsRepository;
    }
}
