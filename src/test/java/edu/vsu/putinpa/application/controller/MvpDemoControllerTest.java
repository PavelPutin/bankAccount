package edu.vsu.putinpa.application.controller;

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

@Component
class MvpDemoControllerTest {
    private MvpDemoController controller;

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
    }

    @Test
    public void demoControllerTest() {
        String source = """
                allclients
                registration pavel qwerty
                registration test test
                allclients
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