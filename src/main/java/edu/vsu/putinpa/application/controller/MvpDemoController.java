package edu.vsu.putinpa.application.controller;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.Money;
import edu.vsu.putinpa.application.service.*;
import edu.vsu.putinpa.application.service.operation.impl.OpenAccount;
import edu.vsu.putinpa.application.service.operation.impl.Replenishment;
import edu.vsu.putinpa.application.service.operation.info.OpeningAccountInfo;
import edu.vsu.putinpa.application.service.operation.info.ReplenishmentInfo;
import edu.vsu.putinpa.infrastructure.di.api.AutoInjected;
import edu.vsu.putinpa.infrastructure.di.api.Component;

import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@Component
public class MvpDemoController {
    private Client loggedInClient = null;
    private final AccountsService accountsService;
    private final ClientsService clientsService;
    private final OperationsHistoryService operationsHistoryService;
    private final OperationsService operationsService;

    private InputStream source = System.in;
    private PrintStream out = System.out;
    private boolean debug = false;

    @AutoInjected
    public MvpDemoController(AccountsService accountsService, ClientsService clientsService, OperationsHistoryService operationsHistoryService, OperationsService operationsService) {
        this.accountsService = accountsService;
        this.clientsService = clientsService;
        this.operationsHistoryService = operationsHistoryService;
        this.operationsService = operationsService;
    }

    public void setSource(InputStream source) {
        this.source = source;
    }

    public void setOut(PrintStream out) {
        this.out = out;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     * запуск контроллера
     */
    public void start() {
        Scanner input = new Scanner(source);
        while (true) {
            out.printf("%s>> ", loggedInClient == null ? "!Unauth!" : loggedInClient.getName());
            String inputLine = input.nextLine();
            if (debug) {
                out.println(inputLine);
            }
            String[] tokens = inputLine.split(" ");
            try {
                switch (tokens[0]) {
                    case "registration" -> registerClient(tokens);
                    case "allclients" -> showAllClients();
                    case "login" -> login(tokens);
                    case "logout" -> logout();
                    case "open" -> openAccount(tokens);
                    case "replenish" -> replenish(tokens);
                    case "stop" -> {
                        out.println("stop");
                        return;
                    }
                }
            } catch (IllegalArgumentException e) {
                out.println(e.getMessage());
            }
        }
    }

    /**
     * регистрация клиента
     */
    public void registerClient(String... tokens) {
        if (tokens.length != 3)
            throw new IllegalArgumentException("usage: registration <name> <password>");

        String name = tokens[1];
        String password = tokens[2];

        try {
            clientsService.register(name, password);
            out.println("Register new client.");
        } catch (IllegalArgumentException e) {
            out.println(e.getMessage());
        }
    }

    /**
     * Показать всех зарегистрированных пользователей
     */
    public void showAllClients() {
        List<Client> clients = clientsService.getAll();
        if (clients.isEmpty()) {
            out.println("No clients");
        } else {
            out.printf("%36s\t%20s\t%20s\t%s%n", "uuid", "name", "password", "created");
            clients.forEach(c -> out.printf("%36s\t%20s\t%20s\t%s%n", c.getUuid(), c.getName(), c.getPassword(), c.getWhenCreated()));
        }
    }

    public void login(String... tokens) {
        if (tokens.length != 3)
            throw new IllegalArgumentException("usage: login <name> <password>");

        String name = tokens[1];
        String password = tokens[2];

        try {
            Client client = clientsService.getByName(name);
            if (client.getPassword().equals(password)) {
                loggedInClient = client;
            } else {
                out.println("Can't login: invalid name or password");
            }
        } catch (ClientNotFoundException e) {
            out.println("Can't login: invalid name or password");
        }
    }

    public void logout() {
        loggedInClient = null;
    }

    /**
     * добавление некоторой суммы на счет
     */
    public void replenish(String... tokens) {
        if (loggedInClient == null)
            throw new IllegalStateException("NEED AUTHORIZATION");

        if (tokens.length != 4)
            throw new IllegalArgumentException("usage: replenish <recipient name> <money value> <currency>");

        UUID recipientUUID = UUID.fromString(tokens[1]);
        Account recipient = accountsService.getBy(recipientUUID).orElseThrow();
        double value = Double.parseDouble(tokens[2]);
        String currency = tokens[3];
        Money money = new Money(currency, BigDecimal.valueOf(value));

        ReplenishmentInfo info = new ReplenishmentInfo(
                loggedInClient,
                recipient,
                money
        );

        operationsService.executeOperation(new Replenishment(operationsService, info));
    }

    /**
     * снятие некоторой суммы со счета
     */
    public void withdraw(String... tokens) {}

    /**
     * передача суммы с одного счета на другой
     */
    public void transfer(String... tokens) {}

    /**
     * открытие счета
     */
    public void openAccount(String... tokens) {
        if (loggedInClient == null)
            throw new IllegalStateException("NEED AUTHORIZATION");

        if (tokens.length != 3 && tokens.length != 5)
            throw new IllegalArgumentException("usage: open <name> <currency> [<sender> <money value>]");


        String name = tokens[1];
        String currency = tokens[2];

        Account sender = null;
        Money money = null;
        if (tokens.length >= 4) {
            UUID senderUUID = UUID.fromString(tokens[3]);
            sender = accountsService.getBy(senderUUID).orElseThrow();

            double value = Double.parseDouble(tokens[4]);
            money = new Money(currency, BigDecimal.valueOf(value));
        }

        OpeningAccountInfo openingAccountInfo = new OpeningAccountInfo(
                loggedInClient,
                name,
                currency,
                sender,
                money
        );

        operationsService.executeOperation(new OpenAccount(operationsService, openingAccountInfo));
    }

    /**
     * закрытие счета
     */
    public void closeAccount(String... tokens) {}

    /**
     * получение истории операций
     */
    public void getOperationsHistory(String... tokens) {}

    /**
     * получение баланса по счету
     */
    public void getBalanceByAccount(String... tokens) {}

    /**
     * формирование сводной информации по всем счетам
     */
    public void getAggregatedInfoAboutAccounts(String... tokens) {}
}
