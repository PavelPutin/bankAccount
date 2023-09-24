package edu.vsu.putinpa.application.service.impl;

import edu.vsu.putinpa.application.dto.ClosingAccountInfoDto;
import edu.vsu.putinpa.application.dto.OpeningAccountInfoDto;
import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.Money;
import edu.vsu.putinpa.application.repository.OperationsRepository;
import edu.vsu.putinpa.application.repository.impl.InMemoryAccountsRepository;
import edu.vsu.putinpa.application.repository.impl.InMemoryOperationsRepository;
import edu.vsu.putinpa.application.service.AccountsService;
import edu.vsu.putinpa.application.service.Operation;
import edu.vsu.putinpa.application.service.OperationsHistoryService;
import edu.vsu.putinpa.application.service.OperationsService;
import edu.vsu.putinpa.application.service.operation.CloseAccount;
import edu.vsu.putinpa.application.service.operation.OpenAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

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