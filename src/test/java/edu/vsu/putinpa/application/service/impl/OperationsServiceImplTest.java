package edu.vsu.putinpa.application.service.impl;

import edu.vsu.putinpa.application.dto.OpeningAccountInfoDto;
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

    @Test
    public void notGivenSender_whenOpeningAccount_thenOpenAccountWithZeroBalance() {
        Client creator = new Client("test", "test");
        OpeningAccountInfoDto openInfo = new OpeningAccountInfoDto(creator, "test account", "ru", null, null);
        Operation<?> open = new OpenAccount(operationsService, openInfo);
        operationsService.executeOperation(open);

        Account created = accountsService.getBy(creator).stream().findFirst().get();
        JournalOperation journalOperation = operationsHistoryService.getAll().stream().findFirst().get();
        assertAll(
                () -> assertNotNull(created),
                () -> assertNotNull(journalOperation),
                () -> assertEquals("test account", created.getName()),
                () -> assertEquals(new Money("ru", BigDecimal.ZERO), created.getBalance()),
                () -> assertNull(journalOperation.getSender()),
                () -> assertNull(journalOperation.getMoney()),
                () -> assertEquals(created, journalOperation.getRecipient()),
                () -> assertEquals(creator, journalOperation.getClient())
        );
    }

    @Test
    public void givenSender_whenOpeningAccount_thenOpenAccountAndUpdateBalanceInBothAccounts() {
        Client creator = new Client("test", "test");
        Account sender = new Account("sender", "ru", creator);
        sender.replenishment(new Money("ru", BigDecimal.TEN));

        accountsService = new AccountsServiceImpl(new InMemoryAccountsRepository(sender));
        operationsService = new OperationsServiceImpl(accountsService, operationsHistoryService);

        OpeningAccountInfoDto openInfo = new OpeningAccountInfoDto(
                creator,
                "created",
                "ru",
                sender,
                new Money("ru", BigDecimal.valueOf(6))
        );
        Operation<?> open = new OpenAccount(operationsService, openInfo);
        operationsService.executeOperation(open);

        Account created = accountsService.getBy("created").stream().findFirst().get();
        assertAll(
                () -> assertNotNull(created),
                () -> assertEquals(new Money("ru", BigDecimal.valueOf(6)), created.getBalance()),
                () -> assertEquals(new Money("ru", BigDecimal.valueOf(4)), sender.getBalance())
        );
    }
}