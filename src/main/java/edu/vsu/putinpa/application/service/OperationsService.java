package edu.vsu.putinpa.application.service;

import edu.vsu.putinpa.application.repository.AccountsRepository;

public interface OperationsService {
    void executeOperation(Operation<?> operation);
    OperationsHistoryService getOperationHistoryService();
    AccountsRepository getAccountRepository();
}
