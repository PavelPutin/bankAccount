package edu.vsu.putinpa.application.service.operation.info;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.Money;

public class ReplenishmentInfo extends OneAccountOperationInfo {

    public ReplenishmentInfo(Client invoker, Account target, Money money) {
        super(invoker, target, money);
    }
}
