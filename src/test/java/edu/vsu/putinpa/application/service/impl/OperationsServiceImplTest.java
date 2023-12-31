package edu.vsu.putinpa.application.service.impl;

import edu.vsu.putinpa.application.service.operation.info.OpeningAccountInfo;
import edu.vsu.putinpa.application.service.operation.info.ReplenishmentInfo;
import edu.vsu.putinpa.application.service.operation.info.TransferInfo;
import edu.vsu.putinpa.application.service.operation.info.WithdrawalInfo;
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
import edu.vsu.putinpa.application.service.operation.impl.OpenAccount;
import edu.vsu.putinpa.application.service.operation.impl.Replenishment;
import edu.vsu.putinpa.application.service.operation.impl.Transfer;
import edu.vsu.putinpa.application.service.operation.impl.Withdraw;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OperationsServiceImplTest {
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
    public void notGivenSender_whenOpeningAccount_thenOpenAccountWithZeroBalance() {
        OpeningAccountInfo openInfo = new OpeningAccountInfo(creator, "test account", "ru", null, null);
        Operation<?> open = new OpenAccount(operationsService, openInfo);
        operationsService.executeOperation(open);

        Account created = accountsService.getBy(creator).stream().findFirst().get();
        JournalOperation journalOperation = getFirstJournalOperation();
        assertAll(
                () -> assertNotNull(created),
                () -> assertNotNull(journalOperation),
                () -> assertEquals("test account", created.getName()),
                () -> assertEquals(new Money("ru", BigDecimal.ZERO), created.getBalance()),
                () -> assertEquals(creator, journalOperation.getClient()),
                () -> assertNull(journalOperation.getSender()),
                () -> assertEquals(created, journalOperation.getRecipient()),
                () -> assertNull(journalOperation.getMoney())
        );
    }

    @Test
    public void givenSender_whenOpeningAccount_thenOpenAccountAndUpdateBalanceInBothAccounts() {
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
        operationsService.executeOperation(open);

        Account created = accountsService.getBy("created").stream().findFirst().get();
        JournalOperation journalOperation = getFirstJournalOperation();
        assertAll(
                () -> assertNotNull(created),
                () -> assertEquals(new Money("ru", BigDecimal.valueOf(6)), created.getBalance()),
                () -> assertEquals(new Money("ru", BigDecimal.valueOf(4)), sender.getBalance()),
                () -> assertEquals(creator, journalOperation.getClient()),
                () -> assertEquals(sender, journalOperation.getSender()),
                () -> assertEquals(created, journalOperation.getRecipient()),
                () -> assertEquals(new Money("ru", BigDecimal.valueOf(6)), journalOperation.getMoney())
        );
    }

    @Test
    public void givenSenderAndRecipient_whenTransfer_thenUpdateBalanceInBothAccounts() {
        Account sender = new Account("sender", "ru", creator);
        sender.replenishment(new Money("ru", BigDecimal.TEN));
        Account recipient = new Account("recipient", "ru", creator);
        recipient.replenishment(new Money("ru", BigDecimal.ONE));

        accountsService = new AccountsServiceImpl(new InMemoryAccountsRepository(sender, recipient));
        operationsService = new OperationsServiceImpl(accountsService, operationsHistoryService);

        TransferInfo transferInfo = new TransferInfo(creator, sender, recipient, new Money("ru", BigDecimal.TWO));
        Operation<?> operation = new Transfer(operationsService, transferInfo);
        operationsService.executeOperation(operation);

        JournalOperation journalOperation = getFirstJournalOperation();
        assertAll(
                () -> assertEquals(new Money("ru", BigDecimal.valueOf(8)), sender.getBalance()),
                () -> assertEquals(new Money("ru", BigDecimal.valueOf(3)), recipient.getBalance()),
                () -> assertEquals(creator, journalOperation.getClient()),
                () -> assertEquals(sender, journalOperation.getSender()),
                () -> assertEquals(recipient, journalOperation.getRecipient()),
                () -> assertEquals(new Money("ru", BigDecimal.TWO), journalOperation.getMoney())
        );
    }

    @Test
    public void givenMoney_whenReplenishment_thenAddMoneyToBalance() {
        Account target = new Account("target", "ru", creator);
        target.replenishment(new Money("ru", BigDecimal.TEN));

        accountsService = new AccountsServiceImpl(new InMemoryAccountsRepository(target));
        operationsService = new OperationsServiceImpl(accountsService, operationsHistoryService);

        ReplenishmentInfo info = new ReplenishmentInfo(creator, target, new Money("ru", BigDecimal.TWO));
        Operation<?> operation = new Replenishment(operationsService, info);
        operationsService.executeOperation(operation);

        JournalOperation journalOperation = getFirstJournalOperation();
        assertAll(
                () -> assertEquals(new Money("ru", BigDecimal.valueOf(12)), target.getBalance()),
                () -> assertEquals(creator, journalOperation.getClient()),
                () -> assertEquals(target, journalOperation.getRecipient()),
                () -> assertNull(journalOperation.getSender()),
                () -> assertEquals(new Money("ru", BigDecimal.TWO), journalOperation.getMoney())
        );
    }

    @Test
    public void givenMoney_whenWithdraw_thenSubtractMoneyFromBalance() {
        Account target = new Account("target", "ru", creator);
        target.replenishment(new Money("ru", BigDecimal.TEN));

        accountsService = new AccountsServiceImpl(new InMemoryAccountsRepository(target));
        operationsService = new OperationsServiceImpl(accountsService, operationsHistoryService);

        WithdrawalInfo info = new WithdrawalInfo(creator, target, new Money("ru", BigDecimal.TWO));
        Operation<?> operation = new Withdraw(operationsService, info);
        operationsService.executeOperation(operation);

        JournalOperation journalOperation = getFirstJournalOperation();
        assertAll(
                () -> assertEquals(new Money("ru", BigDecimal.valueOf(8)), target.getBalance()),
                () -> assertEquals(creator, journalOperation.getClient()),
                () -> assertEquals(target, journalOperation.getSender()),
                () -> assertNull(journalOperation.getRecipient()),
                () -> assertEquals(new Money("ru", BigDecimal.TWO), journalOperation.getMoney())
        );
    }

    private JournalOperation getFirstJournalOperation() {
        return operationsHistoryService.getAll().stream().findFirst().get();
    }
}