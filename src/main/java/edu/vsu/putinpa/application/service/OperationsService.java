package edu.vsu.putinpa.application.service;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.repository.AccountsRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface OperationsService {
    void executeOperation(Operation<?> operation);
    OperationsHistoryService getOperationHistoryService();
    AccountsService getAccountsService();
}
