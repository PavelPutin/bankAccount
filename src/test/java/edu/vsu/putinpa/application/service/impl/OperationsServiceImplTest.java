package edu.vsu.putinpa.application.service.impl;

import edu.vsu.putinpa.application.repository.impl.InMemoryAccountsRepository;
import edu.vsu.putinpa.application.repository.impl.InMemoryOperationsRepository;
import edu.vsu.putinpa.application.service.AccountsService;
import edu.vsu.putinpa.application.service.OperationsHistoryService;
import edu.vsu.putinpa.application.service.OperationsService;
import org.junit.jupiter.api.BeforeEach;

class OperationsServiceImplTest {
    private AccountsService accountsService;
    private OperationsService operationsService;
    private OperationsHistoryService operationsHistoryService;

    @BeforeEach
    public void init() {
        accountsService = new AccountsServiceImpl(new InMemoryAccountsRepository());
        operationsHistoryService = new OperationsHistoryServiceImpl(new InMemoryOperationsRepository());
        operationsService = new OperationsServiceImpl(accountsService, operationsHistoryService);
    }
}