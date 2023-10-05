package edu.vsu.putinpa.application.service.impl;

import edu.vsu.putinpa.application.service.AccountsService;
import edu.vsu.putinpa.application.service.Operation;
import edu.vsu.putinpa.application.service.OperationsHistoryService;
import edu.vsu.putinpa.application.service.OperationsService;
import edu.vsu.putinpa.infrastructure.di.api.AutoInjected;
import edu.vsu.putinpa.infrastructure.di.api.Component;

@Component
public class OperationsServiceImpl implements OperationsService {
    private AccountsService accountsService;
    private OperationsHistoryService operationsHistoryService;

    @AutoInjected
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
