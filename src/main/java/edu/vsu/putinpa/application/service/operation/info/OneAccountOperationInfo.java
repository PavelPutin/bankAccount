package edu.vsu.putinpa.application.service.operation.info;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.Money;

abstract public class OneAccountOperationInfo extends OperationInfo {
    private Account target;
    private Money money;
    public OneAccountOperationInfo(Client invoker, Account target, Money money) {
        super(invoker);
        this.target = target;
        this.money = money;
    }

    public Account getTarget() {
        return target;
    }

    public Money getMoney() {
        return money;
    }
}
