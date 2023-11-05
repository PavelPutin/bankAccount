package edu.vsu.putinpa.application.service.operation.mapping.processor;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.JournalOperation;
import edu.vsu.putinpa.application.model.Money;
import edu.vsu.putinpa.application.repository.impl.InMemoryAccountsRepository;
import edu.vsu.putinpa.application.repository.impl.InMemoryOperationsRepository;
import edu.vsu.putinpa.application.service.AccountsService;
import edu.vsu.putinpa.application.service.Operation;
import edu.vsu.putinpa.application.service.OperationsHistoryService;
import edu.vsu.putinpa.application.service.OperationsService;
import edu.vsu.putinpa.application.service.impl.AccountsServiceImpl;
import edu.vsu.putinpa.application.service.impl.OperationsHistoryServiceImpl;
import edu.vsu.putinpa.application.service.impl.OperationsServiceImpl;
import edu.vsu.putinpa.application.service.operation.impl.OpenAccount;
import edu.vsu.putinpa.application.service.operation.info.OpeningAccountInfo;
import edu.vsu.putinpa.application.service.operation.mapping.OperationMappingAnnotationProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class SenderInfoOperationMappingAnnotationProcessorTest {
    private Client creator;
    private AccountsService accountsService;
    private OperationsService operationsService;
    private OperationsHistoryService operationsHistoryService;
    @BeforeEach
    public void init() {
        creator = new Client("test", "test");
        accountsService = new AccountsServiceImpl(new InMemoryAccountsRepository());
        operationsHistoryService = new OperationsHistoryServiceImpl(new InMemoryOperationsRepository());
        operationsService = new OperationsServiceImpl(accountsService, operationsHistoryService);
    }

    @Test
    public void getFieldTest() {
        Account sender = new Account("sender", "ru", creator);
        sender.replenishment(new Money("ru", BigDecimal.TEN));

        accountsService = new AccountsServiceImpl(new InMemoryAccountsRepository(sender));
        operationsService = new OperationsServiceImpl(accountsService, operationsHistoryService);

        OpeningAccountInfo openInfo = new OpeningAccountInfo(
                creator,
                "created",
                "ru",
                sender,
                new Money("ru", BigDecimal.valueOf(6))
        );
        Operation<?> open = new OpenAccount(operationsService, openInfo);

        JournalOperation journalOperation = new JournalOperation(Instant.now(), creator, open.getType());

        OperationMappingAnnotationProcessor processor = new SenderOperationMappingAnnotationProcessor();
        processor.insertValueIntoJournalOperation(open, journalOperation);

        assertEquals(sender, journalOperation.getSender());
    }
}