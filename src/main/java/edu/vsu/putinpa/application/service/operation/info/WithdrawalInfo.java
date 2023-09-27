package edu.vsu.putinpa.application.service.operation.info;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.Money;

public class WithdrawalInfo extends OneAccountOperationInfo {
    public WithdrawalInfo(Client invoker, Account target, Money money) {
        super(invoker, target, money);
    }
}
