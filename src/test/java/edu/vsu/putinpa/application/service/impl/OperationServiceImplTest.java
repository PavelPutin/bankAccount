package edu.vsu.putinpa.application.service.impl;

import edu.vsu.putinpa.application.dto.ClosingAccountInfoDto;
import edu.vsu.putinpa.application.dto.OpeningAccountInfoDto;
import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.Money;
import edu.vsu.putinpa.application.repository.AccountsRepository;
import edu.vsu.putinpa.application.repository.OperationsRepository;
import edu.vsu.putinpa.application.repository.impl.InMemoryAccountRepository;
import edu.vsu.putinpa.application.repository.impl.InMemoryOperationRepository;
import edu.vsu.putinpa.application.service.Operation;
import edu.vsu.putinpa.application.service.OperationService;
import edu.vsu.putinpa.application.service.operation.CloseAccount;
import edu.vsu.putinpa.application.service.operation.OpenAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OperationServiceImplTest {
    private AccountsRepository accountsRepository;
    private OperationsRepository operationsRepository;
    private OperationService operationService;

    @BeforeEach
    public void init() {
        accountsRepository = new InMemoryAccountRepository();
        operationsRepository = new InMemoryOperationRepository();
        operationService = new OperationServiceImpl(accountsRepository, operationsRepository);
    }

    @Test
    public void testAccountOpening() {
        Client client = new Client("test", "test");
        OpeningAccountInfoDto info = new OpeningAccountInfoDto(
                client, "test account", "ru", null, null
        );
        Operation<?> operation = new OpenAccount(operationService, info);
        operationService.executeOperation(operation);
        assertEquals(1, accountsRepository.findByName("test account").size());
    }

    @Test
    public void testAccountClosing() {
        Client client = new Client("test", "test");
        Account[] accounts = {
                new Account("test1", "ru", client),
                new Account("test2", "ru", client)
        };
        accounts[0].setBalance(new Money("ru", BigDecimal.TEN));

        AccountsRepository mock = new InMemoryAccountRepository(accounts);
        OperationService service = new OperationServiceImpl(mock, operationsRepository);
        ClosingAccountInfoDto info = new ClosingAccountInfoDto(client, accounts[0], accounts[1]);
        service.executeOperation(new CloseAccount(service, info));

        assertAll(
                () -> assertNotNull(mock.findByUUID(accounts[0].getUuid()), "Первый счёт не добавлен"),
                () -> assertNotNull(mock.findByUUID(accounts[1].getUuid()), "Второй счёт не добавлен"),
                () -> {
                    Account test1 = mock.findByUUID(accounts[0].getUuid()).get();
                    Account test2 = mock.findByUUID(accounts[1].getUuid()).get();

                    assertAll(
                            () -> assertNotNull(test1.getWhenClosed(), "Время закрытия не установлено"),
                            () -> assertEquals(
                                    new Money("ru", BigDecimal.ZERO),
                                    test1.getBalance(),
                                    "У закрываемого счёта не пустой баланс"),
                            () -> assertEquals(
                                    new Money("ru", BigDecimal.TEN),
                                    test2.getBalance(),
                                    "Деньги не переведены на счёт заместитель")
                    );
                }
        );


    }

}