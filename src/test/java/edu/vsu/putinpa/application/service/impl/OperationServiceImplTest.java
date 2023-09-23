package edu.vsu.putinpa.application.service.impl;

import edu.vsu.putinpa.application.dto.OpeningAccountingInfoDto;
import edu.vsu.putinpa.application.dto.OperationInfoDto;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.Money;
import edu.vsu.putinpa.application.repository.AccountsRepository;
import edu.vsu.putinpa.application.repository.impl.InMemoryAccountRepository;
import edu.vsu.putinpa.application.service.Operation;
import edu.vsu.putinpa.application.service.OperationService;
import edu.vsu.putinpa.application.service.operation.OpenAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OperationServiceImplTest {
    private AccountsRepository accountsRepository;
    private OperationService operationService;

    @BeforeEach
    public void init() {
        accountsRepository = new InMemoryAccountRepository();
        operationService = new OperationServiceImpl(accountsRepository);
    }

    @Test
    public void testAccountOpening() {
        Client client = new Client("test", "test");
        OpeningAccountingInfoDto info = new OpeningAccountingInfoDto(
                client, "test account", "ru", null, null
        );
        Operation<?> operation = new OpenAccount(operationService, info);
        operationService.executeOperation(operation);
        assertEquals(1, accountsRepository.findByName("test account").size());
    }

}