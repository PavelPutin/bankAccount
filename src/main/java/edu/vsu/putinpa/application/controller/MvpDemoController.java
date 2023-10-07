package edu.vsu.putinpa.application.controller;

import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.service.AccountsService;
import edu.vsu.putinpa.application.service.ClientsService;
import edu.vsu.putinpa.application.service.OperationsHistoryService;
import edu.vsu.putinpa.application.service.OperationsService;
import edu.vsu.putinpa.infrastructure.di.api.AutoInjected;
import edu.vsu.putinpa.infrastructure.di.api.Component;

import java.util.Scanner;

@Component
public class MvpDemoController {
    private Client loggedInClient = null;
    private final AccountsService accountsService;
    private final ClientsService clientsService;
    private final OperationsHistoryService operationsHistoryService;
    private final OperationsService operationsService;

    @AutoInjected
    public MvpDemoController(AccountsService accountsService, ClientsService clientsService, OperationsHistoryService operationsHistoryService, OperationsService operationsService) {
        this.accountsService = accountsService;
        this.clientsService = clientsService;
        this.operationsHistoryService = operationsHistoryService;
        this.operationsService = operationsService;
    }

    /**
     * запуск контроллера
     */
    public void start() {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.printf("%s>> ", loggedInClient == null ? "!Unauth!" : loggedInClient.getName());
            String[] tokens = input.nextLine().split(" ");
            try {
                switch (tokens[0]) {
                    case "registration" -> registerClient(tokens);
                    case "stop" -> {
                        System.out.println("stop");
                        return;
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * добавление некоторой суммы на счет
     */
    public void replenish(String... tokens) {}

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
    public void openAccount(String... tokens) {}

    /**
     * закрытие счета
     */
    public void closeAccount(String... tokens) {}

    /**
     * получение истории операций
     */
    public void getOperationsHistory(String... tokens) {}

    /**
     * регистрация клиента
     */
    public void registerClient(String... tokens) {
        if (tokens.length != 3)
            throw new IllegalArgumentException("usage: registration <name> <password>");

        String name = tokens[1];
        String password = tokens[2];

        Client newClient = clientsService.register(name, password);
        System.out.println("Register new client.");
    }

    /**
     * получение баланса по счету
     */
    public void getBalanceByAccount(String... tokens) {}

    /**
     * формирование сводной информации по всем счетам
     */
    public void getAggregatedInfoAboutAccounts(String... tokens) {}
}
