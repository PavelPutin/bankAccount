package edu.vsu.putinpa.application.service;

public interface OperationsService {
    void executeOperation(Operation<?> operation);
    OperationsHistoryService getOperationHistoryService();
    AccountsService getAccountsService();
}
