package edu.vsu.putinpa.application.controller;

import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.service.AccountsService;
import edu.vsu.putinpa.application.service.ClientsService;
import edu.vsu.putinpa.application.service.OperationsHistoryService;
import edu.vsu.putinpa.application.service.OperationsService;
import edu.vsu.putinpa.infrastructure.di.api.AutoInjected;
import edu.vsu.putinpa.infrastructure.di.api.Component;

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
        while (true) {

        }
    }

    /**
     * добавление некоторой суммы на счет
     */
    public void replenish() {}

    /**
     * снятие некоторой суммы со счета
     */
    public void withdraw() {}

    /**
     * передача суммы с одного счета на другой
     */
    public void transfer() {}

    /**
     * открытие счета
     */
    public void openAccount() {}

    /**
     * закрытие счета
     */
    public void closeAccount() {}

    /**
     * получение истории операций
     */
    public void getOperationsHistory() {}

    /**
     * регистрация клиента
     */
    public void registrationClient() {}

    /**
     * получение баланса по счету
     */
    public void getBalanceByAccount() {}

    /**
     * формирование сводной информации по всем счетам
     */
    public void getAggregatedInfoAboutAccounts() {}
}
