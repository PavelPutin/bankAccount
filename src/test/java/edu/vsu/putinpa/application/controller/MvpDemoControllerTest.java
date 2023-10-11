package edu.vsu.putinpa.application.controller;

import edu.vsu.putinpa.application.model.EntityWithUUID;
import edu.vsu.putinpa.application.repository.impl.InMemoryAccountsRepository;
import edu.vsu.putinpa.application.repository.impl.InMemoryClientsRepository;
import edu.vsu.putinpa.application.repository.impl.InMemoryOperationsRepository;
import edu.vsu.putinpa.application.service.impl.AccountsServiceImpl;
import edu.vsu.putinpa.application.service.impl.ClientsServiceImpl;
import edu.vsu.putinpa.application.service.impl.OperationsHistoryServiceImpl;
import edu.vsu.putinpa.application.service.impl.OperationsServiceImpl;
import edu.vsu.putinpa.infrastructure.di.api.Component;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

@Component
class MvpDemoControllerTest {
    private MvpDemoController controller;
    private List<String> generatedUUID = new ArrayList<>();

    @BeforeEach
    void setUp() {
        AccountsServiceImpl accountsService = new AccountsServiceImpl(new InMemoryAccountsRepository());
        OperationsHistoryServiceImpl operationsHistoryService = new OperationsHistoryServiceImpl(new InMemoryOperationsRepository());
        controller = new MvpDemoController(
                accountsService,
                new ClientsServiceImpl(new InMemoryAccountsRepository(), new InMemoryClientsRepository()),
                operationsHistoryService,
                new OperationsServiceImpl(accountsService, operationsHistoryService)
        );
        controller.setDebug(true);

        EntityWithUUID.setDefaultUUIDSupplier(new Supplier<>() {
            private String value = "00000000-0000-0000-0000-000000000000";
            private int toReplace = 0;
            @Override
            public UUID get() {
                UUID toReturn = UUID.fromString(value);
                String oldDigit = Integer.toHexString(toReplace);
                String newDigit = Integer.toHexString(++toReplace % 16);
                value = value.replaceFirst(oldDigit, newDigit);
                generatedUUID.add(toReturn.toString());
                return toReturn;
            }
        });
    }

    @Test
    public void demoControllerTest() {
        String source = """
                allclients
                register pavel qwerty
                register test test
                allclients
                login pavel 12345
                login pavel qwerty
                open acc1 ru
                replenish""" + " 20000000-0000-0000-0000-000000000000 " + """
                15 ru
                infoAllAcc
                history
                login hello ab
                login test test
                logout
                stop
                """;
        InputStream sourceStream = new ByteArrayInputStream(source.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream outStream = new PrintStream(out, true, StandardCharsets.UTF_8);
        controller.setSource(sourceStream);
        controller.setOut(outStream);
        controller.start();
        System.out.println(out);
    }
}