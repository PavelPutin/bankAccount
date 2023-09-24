package edu.vsu.putinpa.application.service;

import edu.vsu.putinpa.application.repository.AccountsRepository;
import edu.vsu.putinpa.application.repository.OperationsRepository;

public interface OperationService {
    void executeOperation(Operation<?> operation);
    OperationHistoryService getOperationHistoryService();
    AccountsRepository getAccountRepository();
}
