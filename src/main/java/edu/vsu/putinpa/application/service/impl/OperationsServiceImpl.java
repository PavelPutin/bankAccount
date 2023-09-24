package edu.vsu.putinpa.application.service.impl;

import edu.vsu.putinpa.application.repository.AccountsRepository;
import edu.vsu.putinpa.application.service.Operation;
import edu.vsu.putinpa.application.service.OperationsHistoryService;
import edu.vsu.putinpa.application.service.OperationsService;

public class OperationsServiceImpl implements OperationsService {
    private AccountsRepository accountsRepository;
    private OperationsHistoryService operationsHistoryService;

    public OperationsServiceImpl(AccountsRepository accountsRepository, OperationsHistoryService operationsHistoryService) {
        this.accountsRepository = accountsRepository;
        this.operationsHistoryService = operationsHistoryService;
    }

    @Override
    public void executeOperation(Operation<?> operation) {
        operation.execute();
        operationsHistoryService.add(operation);
    }

    @Override
    public OperationsHistoryService getOperationHistoryService() {
        return operationsHistoryService;
    }

    @Override
    public AccountsRepository getAccountRepository() {
        return accountsRepository;
    }
}
