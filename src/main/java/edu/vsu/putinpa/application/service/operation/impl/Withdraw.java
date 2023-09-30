package edu.vsu.putinpa.application.service.operation.impl;

import edu.vsu.putinpa.application.model.JournalOperation;
import edu.vsu.putinpa.application.service.Operation;
import edu.vsu.putinpa.application.service.OperationsService;
import edu.vsu.putinpa.application.service.operation.info.WithdrawalInfo;

import java.time.Instant;

public class Withdraw extends Operation<WithdrawalInfo> {
    public Withdraw(OperationsService service, WithdrawalInfo info) {
        super(service, info);
    }

    @Override
    public void execute() {
        getInfo().getSender().withdrawal(getInfo().getMoney());
        getService().getAccountsService().save(getInfo().getSender());
    }
}
