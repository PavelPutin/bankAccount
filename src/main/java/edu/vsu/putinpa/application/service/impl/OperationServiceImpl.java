package edu.vsu.putinpa.application.service.impl;

import edu.vsu.putinpa.application.repository.AccountsRepository;
import edu.vsu.putinpa.application.repository.OperationsRepository;
import edu.vsu.putinpa.application.service.Operation;
import edu.vsu.putinpa.application.service.OperationHistoryService;
import edu.vsu.putinpa.application.service.OperationService;

public class OperationServiceImpl implements OperationService {
    private AccountsRepository accountsRepository;
    private OperationHistoryService operationHistoryService;

    public OperationServiceImpl(AccountsRepository accountsRepository, OperationHistoryService operationHistoryService) {
        this.accountsRepository = accountsRepository;
        this.operationHistoryService = operationHistoryService;
    }

    @Override
    public void executeOperation(Operation<?> operation) {
        operation.execute();
        operationHistoryService.add(operation);
    }

    @Override
    public OperationHistoryService getOperationHistoryService() {
        return operationHistoryService;
    }

    @Override
    public AccountsRepository getAccountRepository() {
        return accountsRepository;
    }
}
