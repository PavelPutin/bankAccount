package edu.vsu.putinpa.application.service.impl;

import edu.vsu.putinpa.application.repository.AccountsRepository;
import edu.vsu.putinpa.application.repository.OperationsRepository;
import edu.vsu.putinpa.application.service.Operation;
import edu.vsu.putinpa.application.service.OperationService;

public class OperationServiceImpl implements OperationService {
    private AccountsRepository accountsRepository;
    private OperationsRepository operationsRepository;

    public OperationServiceImpl(AccountsRepository accountsRepository, OperationsRepository operationsRepository) {
        this.accountsRepository = accountsRepository;
        this.operationsRepository = operationsRepository;
    }

    @Override
    public void executeOperation(Operation<?> operation) {
        operation.execute();
        operationsRepository.save(operation.log());
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
