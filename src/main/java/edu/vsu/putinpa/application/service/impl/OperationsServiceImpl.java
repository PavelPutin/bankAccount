package edu.vsu.putinpa.application.service.impl;

import edu.vsu.putinpa.application.service.AccountsService;
import edu.vsu.putinpa.application.service.Operation;
import edu.vsu.putinpa.application.service.OperationsHistoryService;
import edu.vsu.putinpa.application.service.OperationsService;

public class OperationsServiceImpl implements OperationsService {
    private AccountsService accountsService;
    private OperationsHistoryService operationsHistoryService;

    public OperationsServiceImpl(AccountsService accountsService, OperationsHistoryService operationsHistoryService) {
        this.accountsService = accountsService;
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
    public AccountsService getAccountsService() {
        return accountsService;
    }
}
