package edu.vsu.putinpa.application.service.operation.info;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.Money;

public class OpeningAccountInfo extends OperationInfo {
    private String name;
    private String currency;
    private Account sender;
    private Money money;

    public OpeningAccountInfo(Client invoker, String name, String currency, Account sender, Money money) {
        super(invoker);
        this.name = name;
        this.currency = currency;
        this.sender = sender;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public String getCurrency() {
        return currency;
    }

    public Account getSender() {
        return sender;
    }

    public Money getMoney() {
        return money;
    }
}
