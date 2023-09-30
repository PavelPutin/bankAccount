package edu.vsu.putinpa.application.service.operation.info;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.Money;
import edu.vsu.putinpa.application.service.operation.mapping.annotation.SenderInfo;

public class WithdrawalInfo extends OneAccountOperationInfo {
    @SenderInfo
    private Account sender;
    public WithdrawalInfo(Client invoker, Account sender, Money money) {
        super(invoker, money);
        this.sender = sender;
    }

    public Account getSender() {
        return sender;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }
}
